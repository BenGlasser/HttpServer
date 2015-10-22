package com.benglasser.http.request;

import com.benglasser.http.header.EntityHeader;
import com.benglasser.http.header.GeneralHeader;
import com.benglasser.http.header.RequestHeader;
import com.benglasser.http.header.RequestLine;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.experimental.Builder;

/**
 * Created by bglasser on 10/19/15.
 */

@Value
@Builder
@RequiredArgsConstructor
public class Request {
  //  https://tools.ietf.org/html/rfc2616#section-5
  //  Request = Request-Line        ; Section 5.1
  //      *(( general-header        ; Section 4.5
  //      | request-header          ; Section 5.3
  //      | entity-header ) CRLF)   ; Section 7.1
  //     CRLF
  //     [ message-body ]           ; Section 4.3

  private final RequestLine requestLine;
  private final GeneralHeader generalHeader;
  private final RequestHeader requestHeader;
  private final EntityHeader entitylHeader;
}
