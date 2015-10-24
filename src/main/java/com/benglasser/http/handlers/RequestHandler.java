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

  @Override
  public void run()
  {
    //TODO: generify this and hand off individual GET, PUT, POST, etc...  requests to their own handlers
    // Handle Get Request
    try (
        final BufferedReader in =
            new BufferedReader(new InputStreamReader(connection.getInputStream()));
        final PrintWriter out =
            new PrintWriter(connection.getOutputStream(), true);
    )
    {

      final Request request = RequestParser.parse(in);

      if (request != null && request.getRequestLine().getMethod().compareTo(RequestLine.Method.GET) == 0)
      {
        log.info("parsed request: {}", request);
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
            final Response response = Response.builder()
                .statusLine(StatusLine.get200())
                .entitylHeader(EntityHeader.builder()
                    .contentLength(String.valueOf(bytes.length + 1))
                    .build())
                .messageBody(fileContents)
                .build();
            log.info("sending response: {}", response);
            out.println(response);
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
      log.error("problem handling request", e);
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
