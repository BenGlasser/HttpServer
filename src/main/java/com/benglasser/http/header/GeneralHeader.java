package com.benglasser.http.header;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.Optional;

/**
 * Created by bglasser on 10/19/15.
 */

@Value
@AllArgsConstructor
public class GeneralHeader
{
  // https://tools.ietf.org/html/rfc2616#section-4.5
  //  general-header = Cache-Control            ; Section 14.9
  //      | Connection               ; Section 14.10
  //      | Date                     ; Section 14.18
  //      | Pragma                   ; Section 14.32
  //      | Trailer                  ; Section 14.40
  //      | Transfer-Encoding        ; Section 14.41
  //      | Upgrade                  ; Section 14.42
  //      | Via                      ; Section 14.45
  //      | Warning                  ; Section 14.46

  // CONSTANTS ////////////////////////////////////////////////////////////////////////////////////////////////////////
  public final static String CACHE_CONTROL = "Cache-Control";
  public final static String CONNECTION = "Connection";
  public final static String DATE = "Date";
  public final static String PRAGMA = "Pragma";
  public final static String TRAILER = "Trailer";
  public final static String TRANSFER_ENCODING = "Transfer-Encoding";
  public final static String UPGRADE = "Upgrade";
  public final static String VIA = "Via";
  public final static String WARNING = "Warning";

  // HEADER FIELDS ////////////////////////////////////////////////////////////////////////////////////////////////////
  private final Optional<String> cache_control;
  private final Optional<String> connection;
  private final Optional<String> date;
  private final Optional<String> pragma;
  private final Optional<String> trailer;
  private final Optional<String> transferEncoding;
  private final Optional<String> upgrade;
  private final Optional<String> via;
  private final Optional<String> warning;

  public static GeneralHeaderBuilder builder()
  {
    return new GeneralHeaderBuilder();
  }


  @Override
  public String toString()
  {

    return ((cache_control.isPresent()) ? CACHE_CONTROL + ": " + cache_control.get() + "\n" : "")
        + ((connection.isPresent()) ? CONNECTION + ": " + connection.get() + "\n" : "")
        + ((date.isPresent()) ? DATE + ": " + date.get() + "\n" : "")
        + ((pragma.isPresent()) ? PRAGMA + ": " + pragma.get() + "\n" : "")
        + ((trailer.isPresent()) ? TRAILER + ": " + trailer.get() + "\n" : "")
        + ((transferEncoding.isPresent()) ? TRANSFER_ENCODING + ": " + transferEncoding.get() + "\n" : "")
        + ((upgrade.isPresent()) ? UPGRADE + ": " + upgrade.get() + "\n" : "")
        + ((via.isPresent()) ? VIA + ": " + via.get() + "\n" : "")
        + ((warning.isPresent()) ? WARNING + ": " + warning.get() + "\n" : "");
  }

  public static class GeneralHeaderBuilder
  {
    private String cache_control;
    private String connection;
    private String date;
    private String pragma;
    private String trailer;
    private String transferEncoding;
    private String upgrade;
    private String via;
    private String warning;

    GeneralHeaderBuilder()
    {
    }

    public GeneralHeader.GeneralHeaderBuilder cache_control(final String cache_control)
    {
      this.cache_control = cache_control;
      return this;
    }

    public GeneralHeader.GeneralHeaderBuilder connection(final String connection)
    {
      this.connection = connection;
      return this;
    }

    public GeneralHeader.GeneralHeaderBuilder date(final String date)
    {
      this.date = date;
      return this;
    }

    public GeneralHeader.GeneralHeaderBuilder pragma(final String pragma)
    {
      this.pragma = pragma;
      return this;
    }

    public GeneralHeader.GeneralHeaderBuilder trailer(final String trailer)
    {
      this.trailer = trailer;
      return this;
    }

    public GeneralHeader.GeneralHeaderBuilder transferEncoding(final String transferEncoding)
    {
      this.transferEncoding = transferEncoding;
      return this;
    }

    public GeneralHeader.GeneralHeaderBuilder upgrade(final String upgrade)
    {
      this.upgrade = upgrade;
      return this;
    }

    public GeneralHeader.GeneralHeaderBuilder via(final String via)
    {
      this.via = via;
      return this;
    }

    public GeneralHeader.GeneralHeaderBuilder warning(final String warning)
    {
      this.warning = warning;
      return this;
    }

    public GeneralHeader build()
    {
      return new GeneralHeader(
          Optional.ofNullable(cache_control),
          Optional.ofNullable(connection),
          Optional.ofNullable(date),
          Optional.ofNullable(pragma),
          Optional.ofNullable(trailer),
          Optional.ofNullable(transferEncoding),
          Optional.ofNullable(upgrade),
          Optional.ofNullable(via),
          Optional.ofNullable(warning));
    }

    public String toString()
    {
      return "com.benglasser.http.header.GeneralHeader.GeneralHeaderBuilder(cache_control=" + this.cache_control + "," +
          " connection=" + this.connection + ", date=" + this.date + ", pragma=" + this.pragma + ", trailer=" + this
          .trailer + ", transferEncoding=" + this.transferEncoding + ", upgrade=" + this.upgrade + ", via=" + this.via + ", warning=" + this.warning + ")";
    }
  }
}
