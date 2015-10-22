package com.benglasser.http.header;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.experimental.Builder;

import java.net.URI;

/**
 * Created by bglasser on 10/19/15.
 */
@Value
@Builder
@AllArgsConstructor
public class RequestHeader{
//  https://tools.ietf.org/html/rfc2616#section-5.3
//  request-header = Accept                   ; Section 14.1
//      | Accept-Charset           ; Section 14.2
//      | Accept-Encoding          ; Section 14.3
//      | Accept-Language          ; Section 14.4
//      | Authorization            ; Section 14.8
//      | Expect                   ; Section 14.20
//      | From                     ; Section 14.22
//      | Host                     ; Section 14.23
//      | If-Match                 ; Section 14.24

  public final static String ACCEPT = "Accept";
  public final static String ACCEPT_CHARSET = "Accept-Charset";
  public final static String ACCEPT_ENCODING = "Accept-Encoding";
  public final static String ACCEPT_LANGUAGE = "Accept-Language";
  public final static String AUTHORIZATION = "Authorization";
  public final static String EXPECT = "Expect";
  public final static String FROM = "From";
  public final static String HOST = "Host";
  public final static String IF_MATCH = "If-Match";

  private final String accept;
  private final String acceptCharset;
  private final String acceptEncoding;
  private final String acceptLanguage;
  private final String authorization;
  private final String expect;
  private final String from;
  private final String host;
  private final String ifMatch;

  public static RequestHeader emptyRequestHeader()
  {
    return new RequestHeader("", "", "", "", "", "", "", "", "");
  }
}
