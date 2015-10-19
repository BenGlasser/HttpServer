package com.benglasser.http.requests.handlers;

import com.benglasser.http.requests.Request;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.regex.Pattern;

/**
 * Created by bglasser on 10/18/15.
 */

@Slf4j
@RequiredArgsConstructor
public class RequestHandler implements Runnable {

  private final Socket connection;
  private final String rootDirectory;

  /**
   * parse request for connection and returns a Request object.  When populating
   * the Request object the parser converts all header fields to lower case to
   * make them easier to work with later on.
   * @see <a href="https://tools.ietf.org/html/rfc2616#section-4.5">RFC 2616 section 4.5</a>
   *
   * @return
   */
  @SneakyThrows
  private Request parseRequest() {
    log.info("parsing request: {}", connection);
    Request.RequestBuilder requestBuilder = Request.builder();
    String inputLine = ".:.";

    // we don't use "try with resources" here because we need to leave the connection
    // open until we respond
    try {
      BufferedReader in = new BufferedReader(
          new InputStreamReader(connection.getInputStream()));

      while (inputLine != null && !inputLine.equals("")) {
        log.debug("inputLine: {}", inputLine);

        //FIXME: its probably not necessary to run this check every time...
        if (Pattern.compile("(GET|POST|PUT|PATCH|DELETE)", Pattern.CASE_INSENSITIVE).matcher(inputLine).find()) {
          //  Request-Line   = Method SP Request-URI SP HTTP-Version CRLF
          parseRequestLine(requestBuilder, inputLine.toLowerCase());
          if ((inputLine = in.readLine()).equals("")) {
            break;
          }
        }

        // A cheap way to parse out header fields and values.
        // Then we trim white space and standardize case because
        // rfc 2616 says we should not care about case
        String field = inputLine.split(":")[0].trim().toLowerCase();
        String value = inputLine.split(":")[1].trim().toLowerCase();

        switch (field) {
          case (Request.ACCEPT):
            requestBuilder.accept(value);
            break;
          case (Request.AGENT):
            requestBuilder.agent(value);
            break;
          case (Request.CACHE):
            requestBuilder.cache(value);
            break;
          case (Request.CONNECTION):
            requestBuilder.connection(value);
            break;
          case (Request.ENCODING):
            requestBuilder.encoding(value);
            break;
          case (Request.HOST):
            requestBuilder.host(URI.create(value));
            break;
          case (Request.LANGUAGE):
            requestBuilder.language(value);
            break;
          case (Request.PRAGMA):
            requestBuilder.pragma(value);
            break;
          case (Request.UPGRADE):
            requestBuilder.upgrade(Integer.parseInt(value));
            break;
        }
        inputLine = in.readLine();
      }
      return requestBuilder.build();
    } catch (Exception e) {
      log.error("problem parsing line: {}", inputLine);
      e.printStackTrace();
    }
    return requestBuilder.build();
  }

  private void parseRequestLine(Request.RequestBuilder builder, String requestLine)
  {
    //  Request-Line   = Method SP Request-URI SP HTTP-Version CRLF
    String [] parts = requestLine.trim().split(" ");
    builder
        .method(parts[0])
        .requestUri(parts[1])
        .httpVersion(parts[2]);
  }

  @Override
  public void run() {
    Request request = this.parseRequest();
    if (request.getMethod().contains("get")) {

      // try with resources is useful here to make sure we close the connection once we have responded
      try (
          PrintWriter out =
              new PrintWriter(connection.getOutputStream(), true);
      ) {
        String path = rootDirectory + request.getRequestUri();
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        String filecontents = new String(encoded, StandardCharsets.UTF_8);
          out.println(filecontents);
      }
      catch (NoSuchFileException e)
      {
        //TODO: return a 404 here
        log.error("Requested file not found: {}", request.getRequestUri());
      }
      catch (IOException e)
      {
        //TODO: figure out what to return here...  bad request (400)? server error (500)?
        log.error("Problem responding to the client");
      }
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
