package com.benglasser.http.response;

import com.benglasser.http.header.EntityHeader;
import com.benglasser.http.header.GeneralHeader;
import com.benglasser.http.header.ResponseHeader;
import com.benglasser.http.header.StatusLine;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.Optional;

/**
 * Created by bglasser on 10/20/15.
 */

@Value
@RequiredArgsConstructor
public class Response {
//  Response      = Status-Line   ; Section 6.1
//      *(( general-header        ; Section 4.5
//      | response-header         ; Section 6.2
//      | entity-header ) CRLF)   ; Section 7.1
//  CRLF
//  [ message-body ]              ; Section 7.2


  @NonNull
  private final StatusLine statusLine;
  private final Optional<GeneralHeader> generalHeader;
  private final Optional<ResponseHeader> responseHeader;
  private final Optional<EntityHeader> entitylHeader;
  private final Optional<String> messageBody;

  public static ResponseBuilder builder() {
    return new ResponseBuilder();
  }

  @Override
  public String toString() {
    String headers = generalHeader
        .orElse(GeneralHeader.builder().build())
        .toString()
        + responseHeader
        .orElse(ResponseHeader.builder().build())
        .toString()
        + entitylHeader
        .orElse(EntityHeader.builder().build())
        .toString();

    return statusLine.toString()
        // if there are any headers we need to follow with CLRF
        // otherwise there is no need
        + ((Optional.ofNullable(headers).isPresent()) ? headers :"")
        + "\r\n"
        + messageBody.orElse("");
  }

  public static class ResponseBuilder {
    private StatusLine statusLine;
    private GeneralHeader generalHeader;
    private ResponseHeader responseHeader;
    private EntityHeader entitylHeader;
    private String messageBody;

    ResponseBuilder() {
    }

    public Response.ResponseBuilder statusLine(final StatusLine statusLine) {
      this.statusLine = statusLine;
      return this;
    }

    public Response.ResponseBuilder generalHeader(final GeneralHeader generalHeader) {
      this.generalHeader = generalHeader;
      return this;
    }

    public Response.ResponseBuilder responseHeader(final ResponseHeader responseHeader) {
      this.responseHeader = responseHeader;
      return this;
    }

    public Response.ResponseBuilder entitylHeader(final EntityHeader entitylHeader) {
      this.entitylHeader = entitylHeader;
      return this;
    }

    public Response.ResponseBuilder messageBody(final String messageBody) {
      this.messageBody = messageBody;
      return this;
    }

    public Response build() {
      return new Response(
          statusLine,
          Optional.ofNullable(generalHeader),
          Optional.ofNullable(responseHeader),
          Optional.ofNullable(entitylHeader),
          Optional.ofNullable(messageBody));
    }

    public String toString() {
      return "com.benglasser.http.response.Response.ResponseBuilder(statusLine=" + this.statusLine + ", generalHeader=" + this.generalHeader + ", responseHeader=" + this.responseHeader + ", entitylHeader=" + this.entitylHeader + ", messageBody=" + this.messageBody + ")";
    }
  }
}

