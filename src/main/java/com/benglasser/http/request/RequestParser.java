package com.benglasser.http.request;

import com.benglasser.http.header.EntityHeader;
import com.benglasser.http.header.GeneralHeader;
import com.benglasser.http.header.RequestHeader;
import com.benglasser.http.header.RequestLine;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Created by bglasser on 10/19/15.
 */

@Slf4j
@RequiredArgsConstructor
public class RequestParser {

  private final String request;

  /**
   * parse request for connection and returns a Request object
   *
   * @return
   * @see <a href="https://tools.ietf.org/html/rfc2616#section-4.5">RFC 2616 section 4.5</a>
   */
  public Request parse()
  //  https://tools.ietf.org/html/rfc2616#section-5
  //  Request = Request-Line        ; Section 5.1
  //      *(( general-header        ; Section 4.5
  //      | request-header          ; Section 5.3
  //      | entity-header ) CRLF)   ; Section 7.1
  //     CRLF
  //     [ message-body ]           ; Section 4.3
  {
    if (request != null) {
      log.info("parsing request: {}", request);
      final RequestLine.RequestLineBuilder requestLineBuilder = RequestLine.builder();
      final GeneralHeader.GeneralHeaderBuilder generalHeaderBuilder = GeneralHeader.builder();
      final RequestHeader.RequestHeaderBuilder requestHeaderBuilder = RequestHeader.builder();
      final EntityHeader.EntityHeaderBuilder entityHeaderBuilder = EntityHeader.builder();

      // we don't use "try with resources" here because we need to leave the connection
      // open until we respond
      try {
        final List<String> lines = Arrays.asList(request.split("\n"));
        //  first we strip off the request line
        final String line = lines.get(0);
        final String[] inputLineParts = line.split(" ");
        log.debug("line parts: {} {} {}", inputLineParts);
        Optional.ofNullable(inputLineParts[0]).map((part1) ->
        {
          log.debug("Request Method: ", part1);
          requestLineBuilder.method(RequestLine.Method.valueOf(part1));
          Optional.ofNullable(inputLineParts[1]).map((part2) ->
              requestLineBuilder.requestUri(part2));
          Optional.ofNullable(inputLineParts[2]).map((part3) ->
              requestLineBuilder.httpVersion(part3));
          return requestHeaderBuilder;
        });

        lines.subList(1, lines.size()).forEach((inputLine) ->
        {
          log.debug("inputLine: {}", inputLine);

          // A cheap way to parse out header fields and values.
          String field = inputLine.split(":")[0].trim();
          String value = inputLine.split(":")[1].trim();

          switch (field) {

            // GeneralHeader fields
            case (GeneralHeader.CACHE_CONTROL):
              generalHeaderBuilder.cache_control(value);
              break;
            case (GeneralHeader.CONNECTION):
              generalHeaderBuilder.connection(value);
              break;
            case (GeneralHeader.DATE):
              generalHeaderBuilder.date(value);
              break;
            case (GeneralHeader.PRAGMA):
              generalHeaderBuilder.pragma(value);
              break;
            case (GeneralHeader.TRAILER):
              generalHeaderBuilder.trailer(value);
              break;
            case (GeneralHeader.TRANSFER_ENCODING):
              generalHeaderBuilder.transferEncoding(value);
              break;
            case (GeneralHeader.UPGRADE):
              generalHeaderBuilder.upgrade(value);
              break;
            case (GeneralHeader.VIA):
              generalHeaderBuilder.via(value);
              break;
            case (GeneralHeader.WARNING):
              generalHeaderBuilder.warning(value);
              break;

            // RequestHeader fields
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
        });
        return Request.builder()
            .entitylHeader(entityHeaderBuilder.build())
            .generalHeader(generalHeaderBuilder.build())
            .requestHeader(requestHeaderBuilder.build())
            .requestLine(requestLineBuilder.build())
            .build();
      } catch (Exception e) {
        log.error("problem parsing line: ");
        e.printStackTrace();
      }
    }
    return null;
  }
}
