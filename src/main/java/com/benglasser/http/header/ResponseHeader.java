package com.benglasser.http.header;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.Optional;

/**
 * Created by bglasser on 10/20/15.
 */
@Value
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

  private final Optional<String> acceptRanges;
  private final Optional<String> age;
  private final Optional<String> eTag;
  private final Optional<String> location;
  private final Optional<String> proxyAuthenticate;

  public static ResponseHeaderBuilder builder() {
    return new ResponseHeaderBuilder();
  }

  @Override
  public String toString()
  {
    return ((acceptRanges.isPresent()) ? ACCEPT_RANGES + ": " + acceptRanges.get() + "\n":"")
        + ((age.isPresent()) ? AGE + ": " + age.get() + "\n":"")
        + ((eTag.isPresent()) ? ETAG + ": " + eTag.get() + "\n":"")
        + ((location.isPresent()) ? LOCATION + ": " + location.get() + "\n":"")
        + ((proxyAuthenticate.isPresent()) ? PROXY_AUTHENTICATE + ": " + proxyAuthenticate.get() + "\n":"");
  }

  public static class ResponseHeaderBuilder {
    private String acceptRanges;
    private String age;
    private String eTag;
    private String location;
    private String proxyAuthenticate;

    ResponseHeaderBuilder() {
    }

    public ResponseHeader.ResponseHeaderBuilder acceptRanges(final String acceptRanges) {
      this.acceptRanges = acceptRanges;
      return this;
    }

    public ResponseHeader.ResponseHeaderBuilder age(final String age) {
      this.age = age;
      return this;
    }

    public ResponseHeader.ResponseHeaderBuilder eTag(final String eTag) {
      this.eTag = eTag;
      return this;
    }

    public ResponseHeader.ResponseHeaderBuilder location(final String location) {
      this.location = location;
      return this;
    }

    public ResponseHeader.ResponseHeaderBuilder proxyAuthenticate(final String proxyAuthenticate) {
      this.proxyAuthenticate = proxyAuthenticate;
      return this;
    }

    public ResponseHeader build() {
      return new ResponseHeader(
          Optional.ofNullable(acceptRanges),
          Optional.ofNullable(age),
          Optional.ofNullable(eTag),
          Optional.ofNullable(location),
          Optional.ofNullable(proxyAuthenticate));
    }

    public String toString() {
      return "com.benglasser.http.header.ResponseHeader.ResponseHeaderBuilder(acceptRanges=" + this.acceptRanges + ", age=" + this.age + ", eTag=" + this.eTag + ", location=" + this.location + ", proxyAuthenticate=" + this.proxyAuthenticate + ")";
    }
  }
}
