package com.benglasser.http.header;

import lombok.Value;
import lombok.experimental.Builder;

import java.net.URI;

/**
 * Created by bglasser on 10/18/15.
 */

@Value
@Builder
public abstract class Header {
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
  public final static String CACHE_CONTROL = "cache-control";
  public final static String CONNECTION = "connection";
  public final static String DATE = "Date";
  public final static String PRAGMA = "Pragma";
  public final static String TRAILER = "Trailer";
  public final static String TRANSFER_ENCODING = "Transfer-Encoding";
  public final static String UPGRADE = "Upgrade";
  public final static String VIA = "Via";
  public final static String WARNING = "Warning";

  // HEADER FIELDS ////////////////////////////////////////////////////////////////////////////////////////////////////
  private final String cache_control;
  private final String connection;
  private final String date;
  private final String pragma;
  private final String trailer;
  private final String transfer_encoding;
  private final String upgrade;
  private final String via;
  private final String warning;
}
