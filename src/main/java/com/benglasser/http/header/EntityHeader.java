package com.benglasser.http.header;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.Optional;

/**
 * Created by bglasser on 10/19/15.
 */

@Value
@AllArgsConstructor
public class EntityHeader
{
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

  private final Optional<String> allow;
  private final Optional<String> contentEncoding;
  private final Optional<String> contentLanguage;
  private final Optional<String> contentLength;
  private final Optional<String> contentLocation;
  private final Optional<String> contentMd5;
  private final Optional<String> contentRange;
  private final Optional<String> contentType;
  private final Optional<String> expires;
  private final Optional<String> lastModified;

  public static EntityHeaderBuilder builder()
  {
    return new EntityHeaderBuilder();
  }

  @Override
  public String toString()
  {
    return ((allow.isPresent()) ? ALLOW + ": " + allow + "\n" : "")
        + ((contentEncoding.isPresent()) ? CONTENT_ENCODING + ": " + contentEncoding.get() + "\n" : "")
        + ((contentLanguage.isPresent()) ? CONTENT_LANGUAGE + ": " + contentLanguage.get() + "\n" : "")
        + ((contentLength.isPresent()) ? CONTENT_LENGTH + ": " + contentLength.get() + "\n" : "")
        + ((contentLocation.isPresent()) ? CONTENT_LOCATION + ": " + contentLocation.get() + "\n" : "")
        + ((contentMd5.isPresent()) ? CONTENT_MD5 + ": " + contentMd5.get() + "\n" : "")
        + ((contentRange.isPresent()) ? CONTENT_RANGE + ": " + contentRange.get() + "\n" : "")
        + ((contentType.isPresent()) ? CONTENT_TYPE + ": " + contentType.get() + "\n" : "")
        + ((expires.isPresent()) ? EXPIRES + ": " + expires.get() + "\n" : "")
        + ((lastModified.isPresent()) ? LAST_MODIFIED + ": " + lastModified.get() + "\n" : "");
  }

  public static class EntityHeaderBuilder
  {
    private String allow;
    private String contentEncoding;
    private String contentLanguage;
    private String contentLength;
    private String contentLocation;
    private String contentMd5;
    private String contentRange;
    private String contentType;
    private String expires;
    private String lastModified;

    EntityHeaderBuilder()
    {
    }

    public EntityHeader.EntityHeaderBuilder allow(final String allow)
    {
      this.allow = allow;
      return this;
    }

    public EntityHeader.EntityHeaderBuilder contentEncoding(final String contentEncoding)
    {
      this.contentEncoding = contentEncoding;
      return this;
    }

    public EntityHeader.EntityHeaderBuilder contentLanguage(final String contentLanguage)
    {
      this.contentLanguage = contentLanguage;
      return this;
    }

    public EntityHeader.EntityHeaderBuilder contentLength(final String contentLength)
    {
      this.contentLength = contentLength;
      return this;
    }

    public EntityHeader.EntityHeaderBuilder contentLocation(final String contentLocation)
    {
      this.contentLocation = contentLocation;
      return this;
    }

    public EntityHeader.EntityHeaderBuilder contentMd5(final String contentMd5)
    {
      this.contentMd5 = contentMd5;
      return this;
    }

    public EntityHeader.EntityHeaderBuilder contentRange(final String contentRange)
    {
      this.contentRange = contentRange;
      return this;
    }

    public EntityHeader.EntityHeaderBuilder contentType(final String contentType)
    {
      this.contentType = contentType;
      return this;
    }

    public EntityHeader.EntityHeaderBuilder expires(final String expires)
    {
      this.expires = expires;
      return this;
    }

    public EntityHeader.EntityHeaderBuilder lastModified(final String lastModified)
    {
      this.lastModified = lastModified;
      return this;
    }

    public EntityHeader build()
    {
      return new EntityHeader(
          Optional.ofNullable(allow),
          Optional.ofNullable(contentEncoding),
          Optional.ofNullable(contentLanguage),
          Optional.ofNullable(contentLength),
          Optional.ofNullable(contentLocation),
          Optional.ofNullable(contentMd5),
          Optional.ofNullable(contentRange),
          Optional.ofNullable(contentType),
          Optional.ofNullable(expires),
          Optional.ofNullable(lastModified));
    }

    public String toString()
    {
      return "com.benglasser.http.header.EntityHeader.EntityHeaderBuilder(allow=" + this.allow + ", contentEncoding="
          + this.contentEncoding + ", contentLanguage=" + this.contentLanguage + ", contentLength=" + this
          .contentLength + ", contentLocation=" + this.contentLocation + ", contentMd5=" + this.contentMd5 + ", " +
          "contentRange=" + this.contentRange + ", contentType=" + this.contentType + ", expires=" + this.expires + ", lastModified=" + this.lastModified + ")";
    }
  }
}
