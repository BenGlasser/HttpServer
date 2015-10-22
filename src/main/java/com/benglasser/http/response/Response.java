package com.benglasser.http.response;

import com.benglasser.http.header.*;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.experimental.Builder;

import java.util.Optional;

/**
 * Created by bglasser on 10/20/15.
 */

@Value
@Builder
@RequiredArgsConstructor
public class Response {
//  Response      = Status-Line   ; Section 6.1
//      *(( general-header        ; Section 4.5
//      | response-header         ; Section 6.2
//      | entity-header ) CRLF)   ; Section 7.1
//  CRLF
//  [ message-body ]              ; Section 7.2


  private final StatusLine statusLine;
  private final GeneralHeader generalHeader;
  private final ResponseHeader responseHeader;
  private final EntityHeader entitylHeader;
  private final String messageBody;

  @Override
  public String toString() {
    return statusLine.toString() + '\n'
        + Optional.ofNullable(generalHeader)
          .orElse(GeneralHeader.emptyGeneralheader())
          .toString() + '\n'
        + Optional.ofNullable(responseHeader)
          .orElse(ResponseHeader.emptyResponseHeader())
          .toString() + '\n'
        + Optional.ofNullable(entitylHeader)
          .orElse(EntityHeader.emptyEntityHeader())
          .toString()
        + "\r\n"
        + Optional.ofNullable(messageBody).orElse("")
        + "\r\n";
  }
}

