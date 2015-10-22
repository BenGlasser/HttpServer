package com.benglasser.http.header;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.Optional;

/**
 * Created by bglasser on 10/19/15.
 */
@Value
@AllArgsConstructor
public class RequestHeader
{
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

  private final Optional<String> accept;
  private final Optional<String> acceptCharset;
  private final Optional<String> acceptEncoding;
  private final Optional<String> acceptLanguage;
  private final Optional<String> authorization;
  private final Optional<String> expect;
  private final Optional<String> from;
  private final Optional<String> host;
  private final Optional<String> ifMatch;

  public static RequestHeaderBuilder builder()
  {
    return new RequestHeaderBuilder();
  }

  @Override
  public String toString()
  {
    return ((accept.isPresent()) ? ACCEPT + ": " + accept.get() + "\n" : "")
        + ((acceptCharset.isPresent()) ? ACCEPT_CHARSET + ": " + acceptCharset.get() + "\n" : "")
        + ((acceptEncoding.isPresent()) ? ACCEPT_ENCODING + ": " + acceptEncoding.get() + "\n" : "")
        + ((acceptLanguage.isPresent()) ? ACCEPT_LANGUAGE + ": " + acceptLanguage.get() + "\n" : "")
        + ((authorization.isPresent()) ? AUTHORIZATION + ": " + authorization.get() + "\n" : "")
        + ((expect.isPresent()) ? EXPECT + ": " + expect.get() + "\n" : "")
        + ((from.isPresent()) ? FROM + ": " + from.get() + "\n" : "")
        + ((host.isPresent()) ? HOST + ": " + host.get() + "\n" : "")
        + ((ifMatch.isPresent()) ? IF_MATCH + ": " + ifMatch.get() + "\n" : "");
  }

  public static class RequestHeaderBuilder
  {
    private String accept;
    private String acceptCharset;
    private String acceptEncoding;
    private String acceptLanguage;
    private String authorization;
    private String expect;
    private String from;
    private String host;
    private String ifMatch;

    RequestHeaderBuilder()
    {
    }

    public RequestHeader.RequestHeaderBuilder accept(final String accept)
    {
      this.accept = accept;
      return this;
    }

    public RequestHeader.RequestHeaderBuilder acceptCharset(final String acceptCharset)
    {
      this.acceptCharset = acceptCharset;
      return this;
    }

    public RequestHeader.RequestHeaderBuilder acceptEncoding(final String acceptEncoding)
    {
      this.acceptEncoding = acceptEncoding;
      return this;
    }

    public RequestHeader.RequestHeaderBuilder acceptLanguage(final String acceptLanguage)
    {
      this.acceptLanguage = acceptLanguage;
      return this;
    }

    public RequestHeader.RequestHeaderBuilder authorization(final String authorization)
    {
      this.authorization = authorization;
      return this;
    }

    public RequestHeader.RequestHeaderBuilder expect(final String expect)
    {
      this.expect = expect;
      return this;
    }

    public RequestHeader.RequestHeaderBuilder from(final String from)
    {
      this.from = from;
      return this;
    }

    public RequestHeader.RequestHeaderBuilder host(final String host)
    {
      this.host = host;
      return this;
    }

    public RequestHeader.RequestHeaderBuilder ifMatch(final String ifMatch)
    {
      this.ifMatch = ifMatch;
      return this;
    }

    public RequestHeader build()
    {
      return new RequestHeader(
          Optional.ofNullable(accept),
          Optional.ofNullable(acceptCharset),
          Optional.ofNullable(acceptEncoding),
          Optional.ofNullable(acceptLanguage),
          Optional.ofNullable(authorization),
          Optional.ofNullable(expect),
          Optional.ofNullable(from),
          Optional.ofNullable(host),
          Optional.ofNullable(ifMatch));
    }

    public String toString()
    {
      return "com.benglasser.http.header.RequestHeader.RequestHeaderBuilder(accept=" + this.accept + ", " +
          "acceptCharset=" + this.acceptCharset + ", acceptEncoding=" + this.acceptEncoding + ", acceptLanguage=" +
          this.acceptLanguage + ", authorization=" + this.authorization + ", expect=" + this.expect + ", from=" + this.from + ", host=" + this.host + ", ifMatch=" + this.ifMatch + ")";
    }
  }
}
