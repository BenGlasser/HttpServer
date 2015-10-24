package com.benglasser.http;

import com.benglasser.http.handlers.RequestHandler;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

@Getter
@Setter
@RequiredArgsConstructor
public class HttpServer {

  private Thread runningThread= null;

  private final ExecutorService executor;
  private final ServerSocket listener;

  public HttpServer start() {
    try {
      System.out.println("Listening on port " + listener.getLocalPort());
      for (; ; ) {
        Socket connection = listener.accept();
        RequestHandler handler = RequestHandler.getInstance(connection, "src/main/resources/srv/www");
        executor.execute(handler);
      }
    } catch (Exception e) {
      System.out.println("Error:  " + e);
    }
    return this;
  }
}