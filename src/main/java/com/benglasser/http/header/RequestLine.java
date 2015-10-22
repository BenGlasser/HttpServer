package com.benglasser.http.header;

import lombok.Getter;
import lombok.Value;
import lombok.experimental.Builder;

/**
 * Created by bglasser on 10/19/15.
 */

@Builder
@Value
public class RequestLine {
  //  https://tools.ietf.org/html/rfc2616#section-5.1
  //  Method SP Request-URI SP HTTP-Version CRLF

  public enum Method {
    OPTIONS ("OPTIONS"),
    GET ("GET"),
    HEAD ("HEAD"),
    POST ("POST"),
    PUT ("PUT"),
    DELETE ("DELETE"),
    TRACE ("TRACE"),
    CONNECT ("CONNECT");
    //TODO: implement extension-method

    @Getter
    private String method;

    Method(String method) {
      this.method = method;
    }
  }

  private final Method method;
  private final String requestUri;
  private final String httpVersion;
}
