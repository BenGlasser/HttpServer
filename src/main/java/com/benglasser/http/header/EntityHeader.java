package com.benglasser.http.header;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.experimental.Builder;

/**
 * Created by bglasser on 10/19/15.
 */

@Value
@Builder
@RequiredArgsConstructor
public class EntityHeader {
//  https://tools.ietf.org/html/rfc2616#section-7.1
//  entity-header  = Allow                    ; Section 14.7
//      | Content-Encoding         ; Section 14.11
//      | Content-Language         ; Section 14.12
//      | Content-Length           ; Section 14.13
//      | Content-Location         ; Section 14.14
//      | Content-MD5              ; Section 14.15
//      | Content-Range            ; Section 14.16
//      | Content-Type             ; Section 14.17
//      | Expires                  ; Section 14.21
//      | Last-Modified            ; Section 14.29

  public static final String ALLOW = "Allow";
  public static final String CONTENT_ENCODING = "Content-Encoding";
  public static final String CONTENT_LANGUAGE = "Content-Language";
  public static final String CONTENT_LENGTH = "Content-Length";
  public static final String CONTENT_LOCATION = "Content-Location";
  public static final String CONTENT_MD5 = "Content-MD5";
  public static final String CONTENT_RANGE = "Content-Range";
  public static final String CONTENT_TYPE = "Content-Type";
  public static final String EXPIRES = "Expires";
  public static final String LAST_MODIFIED = "Last-Modified";

  //TODO: implement extension-header

  private final String allow;
  private final String contentEncoding;
  private final String contentLanguage;
  private final String contentLength;
  private final String contentLocation;
  private final String contentMd5;
  private final String contentRange;
  private final String contentType;
  private final String expires;
  private final String lastModified;

}
