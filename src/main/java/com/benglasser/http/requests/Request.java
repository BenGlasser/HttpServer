package com.benglasser.http.requests;

import lombok.Value;
import lombok.experimental.Builder;

import java.net.URI;

/**
 * Created by bglasser on 10/18/15.
 */

@Value
@Builder
public class Request {
  // https://tools.ietf.org/html/rfc2616#section-4.5

  // CONSTANTS ////////////////////////////////////////////////////////////////////////////////////////////////////////
  public final static String METHOD = "get";
  //TODO:  fill in other methods here: POST PUT PATCH etc...
  public final static String HOST = "host";
  public final static String CONNECTION = "connection";
  public final static String PRAGMA = "pragma";
  public final static String CACHE = "cache-control";
  public final static String ACCEPT = "accept";
  public final static String UPGRADE = "upgrade-insecure-requests";
  public final static String AGENT = "user-agent";
  public final static String ENCODING = "accept-encoding";
  public final static String LANGUAGE = "accept-language";


  // HEADER FIELDS ////////////////////////////////////////////////////////////////////////////////////////////////////
  //FIXME: there may be other header fields, but this is what chrome sends for get requests...
  private String method;
  private String requestUri;
  private String httpVersion;
  private URI host;
  private String connection;
  private String pragma;
  private String cache;
  private String accept;
  private int upgrade;
  private String agent;
  private String encoding;
  private String language;
  private String body;


}
