package com.benglasser.http.handlers;

import com.benglasser.http.header.GeneralHeader;
import com.benglasser.http.header.RequestLine;
import com.benglasser.http.header.StatusLine;
import com.benglasser.http.request.Request;
import com.benglasser.http.request.RequestParser;
import com.benglasser.http.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * Created by bglasser on 10/18/15.
 *
 * The RequestHandler parses incoming requests and formulates the proper response to send to the client
 */
@Slf4j
@RequiredArgsConstructor
public class RequestHandler implements Runnable {

  private final Socket connection;
  private final String rootDirectory;

  /**
   * parse request for connection and returns a Request object.
   *
   * @return
   * @see <a href="https://tools.ietf.org/html/rfc2616#section-4.5">RFC 2616 section 4.5</a>
   */
  @SneakyThrows
  private Request parseRequest() {
    String request = "";
    BufferedReader in =
        new BufferedReader(new InputStreamReader(connection.getInputStream()));

    String line;
    do {
      line = in.readLine();
      request += line + '\n';
    } while (line != null && !line.equals(""));

    return new RequestParser(request).parse();
  }

  @Override
  public void run() {
    Request request = this.parseRequest();
    // Handle Get Request
    try {
      if (request != null && request.getRequestLine().getMethod().compareTo(RequestLine.Method.GET) == 0) {
        log.info("parsed request: {}", request);

        PrintWriter out =
            new PrintWriter(connection.getOutputStream(), true);
        try
        {
          String path = rootDirectory + request.getRequestLine().getRequestUri();
          byte[] encoded = Files.readAllBytes(Paths.get(path));
          String filecontents = new String(encoded, StandardCharsets.UTF_8);
          out.println(filecontents);
        } catch (NoSuchFileException e) {
          //TODO: return a 404 here
          log.error("Requested file not found: {}", request.getRequestLine().getRequestUri());
          out.println(Response.builder()
              .statusLine(StatusLine.get404())
              .build()
              .toString());
        } catch (IOException e) {
          //TODO: figure out what to return here...  bad header (400)? server error (500)?
          log.error("Problem responding to the client", e);
        } finally {
          // FIXME:  make sure to close connections in code paths that don't reach this point.
          out.close();
        }
      }
    }catch (Exception e)
    {
      log.error("problem handling request: {}", request, e);
    }
  }

  /**
   * Return a new RequestHandler for this connection
   *
   * @param connection
   * @return
   */
  public static RequestHandler getInstance(Socket connection, String rootDirectory) {
    return new RequestHandler(connection, rootDirectory);
  }
}
