package com.benglasser.http.request;

import com.benglasser.http.header.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URI;
import java.util.regex.Pattern;

/**
 * Created by bglasser on 10/19/15.
 */

@Slf4j
@RequiredArgsConstructor
public class RequestParser {

  private final Socket connection;

  public Request parse()

  //  https://tools.ietf.org/html/rfc2616#section-5
  //  Request = Request-Line        ; Section 5.1
  //      *(( general-header        ; Section 4.5
  //      | request-header          ; Section 5.3
  //      | entity-header ) CRLF)   ; Section 7.1
  //     CRLF
  //     [ message-body ]           ; Section 4.3

  {
    log.info("parsing request: {}", connection);

    RequestLine.RequestLineBuilder requestLineBuilder = RequestLine.builder();
    GeneralHeader.GeneralHeaderBuilder generalHeaderBuilder = GeneralHeader.builder();
    RequestHeader.RequestHeaderBuilder requestHeaderBuilder = RequestHeader.builder();
    EntityHeader.EntityHeaderBuilder entityHeaderBuilder = EntityHeader.builder();

    String inputLine = ".:.";

    // we don't use "try with resources" here because we need to leave the connection
    // open until we respond
    try {
      BufferedReader in = new BufferedReader(
          new InputStreamReader(connection.getInputStream()));

      //  first we strip off the request line
      inputLine = in.readLine();
      String [] inputLineParts = inputLine.split(" ");
      requestLineBuilder
          .method(RequestLine.Method.valueOf(inputLineParts[0]))
          .requestUri(inputLineParts[1])
          .httpVersion(inputLineParts[2]);



      while (inputLine != null && !inputLine.equals("")) {
        log.debug("inputLine: {}", inputLine);

        // A cheap way to parse out header fields and values.
        String field = inputLine.split(":")[0].trim();
        String value = inputLine.split(":")[1].trim();

        switch (field) {

          // General Header fields
          case(GeneralHeader.CACHE_CONTROL):
            generalHeaderBuilder.cache_control(value);
            break;
          case(GeneralHeader.CONNECTION):
            generalHeaderBuilder.connection(value);
            break;
          case(GeneralHeader.DATE):
            generalHeaderBuilder.date(value);
            break;
          case(GeneralHeader.PRAGMA):
            generalHeaderBuilder.pragma(value);
            break;
          case(GeneralHeader.TRAILER):
            generalHeaderBuilder.trailer(value);
            break;
          case(GeneralHeader.TRANSFER_ENCODING):
            generalHeaderBuilder.transferEncoding(value);
            break;
          case(GeneralHeader.UPGRADE):
            generalHeaderBuilder.upgrade(value);
            break;
          case(GeneralHeader.VIA):
            generalHeaderBuilder.via(value);
            break;
          case(GeneralHeader.WARNING):
            generalHeaderBuilder.warning(value);
            break;

          // Request Header fields
          case (RequestHeader.ACCEPT):
            requestHeaderBuilder.accept(value);
            break;
          case (RequestHeader.ACCEPT_CHARSET):
            requestHeaderBuilder.acceptCharset(value);
            break;
          case (RequestHeader.ACCEPT_ENCODING):
            requestHeaderBuilder.acceptEncoding(value);
            break;
          case (RequestHeader.ACCEPT_LANGUAGE):
            requestHeaderBuilder.acceptLanguage(value);
            break;
          case (RequestHeader.AUTHORIZATION):
            requestHeaderBuilder.authorization(value);
            break;
          case (RequestHeader.EXPECT):
            requestHeaderBuilder.expect(value);
            break;
          case (RequestHeader.FROM):
            requestHeaderBuilder.from(value);
            break;
          case (RequestHeader.HOST):
            requestHeaderBuilder.host(value);
            break;
          case (RequestHeader.IF_MATCH):
            requestHeaderBuilder.ifMatch(value);
            break;

          // Entity header fields
          case (EntityHeader.ALLOW):
            entityHeaderBuilder.allow(value);
            break;
          case (EntityHeader.CONTENT_ENCODING):
            entityHeaderBuilder.contentEncoding(value);
            break;
          case (EntityHeader.CONTENT_LANGUAGE):
            entityHeaderBuilder.contentLanguage(value);
            break;
          case (EntityHeader.CONTENT_LENGTH):
            entityHeaderBuilder.contentLength(value);
            break;
          case (EntityHeader.CONTENT_LOCATION):
            entityHeaderBuilder.contentLocation(value);
            break;
          case (EntityHeader.CONTENT_MD5):
            entityHeaderBuilder.contentMd5(value);
            break;
          case (EntityHeader.CONTENT_RANGE):
            entityHeaderBuilder.contentRange(value);
            break;
          case (EntityHeader.CONTENT_TYPE):
            entityHeaderBuilder.contentType(value);
            break;
          case (EntityHeader.EXPIRES):
            entityHeaderBuilder.expires(value);
            break;
          case (EntityHeader.LAST_MODIFIED):
            entityHeaderBuilder.lastModified(value);
            break;

        }
        inputLine = in.readLine();
      }
      return Request.builder()
          .entitylHeader(entityHeaderBuilder.build())
          .generalHeader(generalHeaderBuilder.build())
          .requestHeader(requestHeaderBuilder.build())
          .requestLine(requestLineBuilder.build())
          .build();
    } catch (Exception e) {
      log.error("problem parsing line: {}", inputLine);
      e.printStackTrace();
    }
    return null;
  }
}
