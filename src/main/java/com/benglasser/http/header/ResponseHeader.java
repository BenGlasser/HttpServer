package com.benglasser.http.header;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.experimental.Builder;

/**
 * Created by bglasser on 10/20/15.
 */
@Value
@Builder
@AllArgsConstructor
public class ResponseHeader {
//  response-header = Accept-Ranges           ; Section 14.5
//      | Age                     ; Section 14.6
//      | ETag                    ; Section 14.19
//      | Location                ; Section 14.30
//      | Proxy-Authenticate      ; Section 14.33

  public final static String ACCEPT_RANGES = "Accept-Ranges";
  public final static String AGE = "Age";
  public final static String ETAG = "ETag";
  public final static String LOCATION = "Location";
  public final static String PROXY_AUTHENTICATE = "Proxy-Authenticate";

  private final String acceptRanges;
  private final String age;
  private final String eTag;
  private final String location;
  private final String getProxyAuthenticate;

  public static ResponseHeader emptyResponseHeader()
  {
    return new ResponseHeader("", "", "", "", "");
  }
}
