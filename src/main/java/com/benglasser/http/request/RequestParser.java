package com.benglasser.http.request;

import com.benglasser.http.header.EntityHeader;
import com.benglasser.http.header.GeneralHeader;
import com.benglasser.http.header.RequestHeader;
import com.benglasser.http.header.RequestLine;
import com.google.common.collect.Lists;
import com.sun.deploy.util.ArrayUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Created by bglasser on 10/19/15.
 */

@Slf4j
@RequiredArgsConstructor
public class RequestParser {

  /**
   * parse request for connection and returns a Request object
   *
   * @return
   * @see <a href="https://tools.ietf.org/html/rfc2616#section-4.5">RFC 2616 section 4.5</a>
   */
  public static synchronized Request parse(final BufferedReader in) throws IOException
  //  https://tools.ietf.org/html/rfc2616#section-5
  //  Request = Request-Line        ; Section 5.1
  //      *(( general-header        ; Section 4.5
  //      | request-header          ; Section 5.3
  //      | entity-header ) CRLF)   ; Section 7.1
  //     CRLF
  //     [ message-body ]           ; Section 4.3
  {
    final Request.RequestBuilder requestBuilder = Request.builder();
    final RequestLine.RequestLineBuilder requestLineBuilder = RequestLine.builder();
    final GeneralHeader.GeneralHeaderBuilder generalHeaderBuilder = GeneralHeader.builder();
    final RequestHeader.RequestHeaderBuilder requestHeaderBuilder = RequestHeader.builder();
    final EntityHeader.EntityHeaderBuilder entityHeaderBuilder = EntityHeader.builder();

    String requestLine = "";
    while(requestLine != null && !(requestLine = in.readLine()).equals(""))
    {
      if (requestLine.equals("")) break;
      log.info("parsing request: {}", requestLine);

      try {
        if (requestLine.contains("GET"))
        {
          final String[] inputLineParts = requestLine.split(" ");
          log.debug("line parts: {} {} {}", inputLineParts);

          requestLineBuilder
              .method(RequestLine.Method.valueOf(inputLineParts[0]))
              .requestUri(inputLineParts[1])
              .httpVersion(inputLineParts[2])
              .build();
        }
        else if (requestLine.contains(":"))
        {
          log.debug("inputLine: {}", requestLine);

          // A cheap way to parse out header fields and values.
          String field = requestLine.split(":")[0].trim();
          String value = requestLine.split(":")[1].trim();

          switch (field)
          {

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
        }
      } catch (Exception e) {
        log.error("problem parsing line: {}", requestLine);
        e.printStackTrace();
      }
    }
    return Request.builder()
        .entitylHeader(entityHeaderBuilder.build())
        .generalHeader(generalHeaderBuilder.build())
        .requestHeader(requestHeaderBuilder.build())
        .requestLine(requestLineBuilder.build())
        .build();
  }
}
