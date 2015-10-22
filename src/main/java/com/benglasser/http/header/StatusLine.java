package com.benglasser.http.header;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.experimental.Builder;

/**
 * Created by bglasser on 10/20/15.
 */
@Value
@Builder
@RequiredArgsConstructor
public class StatusLine {
  // Status-Line = HTTP-Version SP Status-Code SP Reason-Phrase CRLF


  private final String httpVersion;
  private final String statusCode;
  private final String reasonPhrase;

  @Override
  public String toString() {
    return this.httpVersion + " "
        + this.statusCode + " "
        + this.reasonPhrase;
  }

  public static StatusLine get404()
  {
    return StatusLine.builder()
        .httpVersion("HTTP/1.1 ")
        .statusCode("404")
        .reasonPhrase("Not Found")
        .build();
  }

  public static StatusLine get500()
  {
    return StatusLine.builder()
        .httpVersion("HTTP/1.1 ")
        .statusCode("500")
        .reasonPhrase("Internal Server Error")
        .build();
  }

  public static StatusLine get200()
  {
    return StatusLine.builder()
        .httpVersion("HTTP/1.1 ")
        .statusCode("200")
        .reasonPhrase("OK")
        .build();
  }

  public static StatusLine get204()
  {
    return StatusLine.builder()
        .httpVersion("HTTP/1.1 ")
        .statusCode("204")
        .reasonPhrase("No Content")
        .build();
  }

  public static StatusLine get420()
  {
    return StatusLine.builder()
        .httpVersion("HTTP/1.1 ")
        .statusCode("420")
        .reasonPhrase("Enhance Your Calm")
        .build();
  }

  public static StatusLine get418()
  {
    return StatusLine.builder()
        .httpVersion("HTTP/1.1 ")
        .statusCode("418")
        .reasonPhrase("I'm a teapot")
        .build();
  }
}
