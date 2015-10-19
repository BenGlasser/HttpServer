package com.benglasser.http;

import com.benglasser.http.modules.MainModule;
import com.benglasser.http.requests.handlers.RequestHandler;
import com.google.inject.Guice;
import com.google.inject.Injector;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;


public class HttpServer {

  private final ExecutorService executor;

  HttpServer(Injector injector)
  {
    this.executor = injector.getInstance(ExecutorService.class);

    final int LISTENING_PORT = 8000;
    ServerSocket listener = null;
    Socket connection;

    try {
      listener = new ServerSocket(LISTENING_PORT, 8000);
      System.out.println("Listening on port " + LISTENING_PORT);
      while (true) {

        connection = listener.accept();
        RequestHandler parser = RequestHandler.getInstance(connection, "src/main/resources/srv/www/");
        executor.execute(parser);

      }
    } catch (Exception e) {
      System.out.println("Error:  " + e);
      return;
    }
  }




  public static void main(String[] args) throws IOException {
    new HttpServer(Guice.createInjector(new MainModule()));
  }
}