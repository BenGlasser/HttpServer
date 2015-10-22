package com.benglasser.http;

import com.benglasser.http.handlers.RequestHandler;
import lombok.RequiredArgsConstructor;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

@RequiredArgsConstructor
public class HttpServer {

  final ExecutorService executor;

  public HttpServer start() {
    final int LISTENING_PORT = 8000;
    final ServerSocket listener;
    try {
      listener = new ServerSocket(LISTENING_PORT, 8000);
      System.out.println("Listening on port " + LISTENING_PORT);
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