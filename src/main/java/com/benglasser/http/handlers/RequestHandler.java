package com.benglasser.http.handlers;

import com.benglasser.http.header.EntityHeader;
import com.benglasser.http.header.RequestLine;
import com.benglasser.http.header.StatusLine;
import com.benglasser.http.request.Request;
import com.benglasser.http.request.RequestParser;
import com.benglasser.http.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

/**
 * Created by bglasser on 10/18/15.
 * <p>
 * The RequestHandler parses incoming requests and formulates the proper response to send to the client
 * Currently it only handles GET requests, later this could be pulled out into a specific GetHandler
 * additional handlers could be called from here rather than putting all this logic in one place.  An Error
 * Handler would be especially useful.
 */
@Slf4j
@RequiredArgsConstructor
public class RequestHandler implements Runnable
{

  private final Socket connection;
  private final String rootDirectory;

  /**
   * parse request for connection and returns a Request object.
   *
   * @return
   * @see <a href="https://tools.ietf.org/html/rfc2616#section-4.5">RFC 2616 section 4.5</a>
   */
  @SneakyThrows
  private Request parseRequest()
  {
    String request = "";
    BufferedReader in =
        new BufferedReader(new InputStreamReader(connection.getInputStream()));

    String line;
    do
    {
      line = in.readLine();
      request += line + '\n';
    } while (line != null && !line.equals(""));

    return new RequestParser(request).parse();
  }

  @Override
  public void run()
  {
    final Request request = this.parseRequest();
    // Handle Get Request
    try
    {
      if (request != null && request.getRequestLine().getMethod().compareTo(RequestLine.Method.GET) == 0)
      {
        log.info("parsed request: {}", request);

        final PrintWriter out =
            new PrintWriter(connection.getOutputStream(), true);

        try
        {
          final String path = rootDirectory + request.getRequestLine().getRequestUri();
          File file = new File(path);
          if (file.isDirectory())
          {
            // we could return a list of files here at some point
            out.println(Response.builder()
                .statusLine(StatusLine.get404())
                .build()
                .toString());
            return;
          }
          else
          {
            byte[] bytes = Files.readAllBytes(Paths.get(path));
            final String fileContents = new String(bytes, StandardCharsets.UTF_8);
            out.println(
                Response.builder()
                    .statusLine(StatusLine.get200())
                    .entitylHeader(EntityHeader.builder()
                        .contentLength(String.valueOf(bytes.length + 1))
                        .build())
                    .messageBody(fileContents)
                    .build());
          }
        } catch (NoSuchFileException e)
        {
          //  return a 404 if the file can't be found
          log.error("Requested file not found: {}", request.getRequestLine().getRequestUri());
          out.println(Response.builder()
              .statusLine(StatusLine.get404())
              .build()
              .toString());

        } catch (IOException e)
        {
          //TODO: figure out what to return here...  bad header (400)? server error (500)?
          log.error("Problem responding to the client", e);

        } finally
        {
          // FIXME:  make sure to close connections in code paths that don't reach this point.
          out.close();
        }
      }
    } catch (Exception e)
    {
      log.error("problem handling request: {}", request, e);
    }
  }

  /**
   * Returns a new RequestHandler for this connection
   *
   * @param connection
   * @return
   */
  public static RequestHandler getInstance(Socket connection, String rootDirectory)
  {
    return new RequestHandler(connection, rootDirectory);
  }
}
