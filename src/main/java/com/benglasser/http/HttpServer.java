package com.benglasser.http;

import com.benglasser.http.request.handlers.RequestHandler;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import lombok.RequiredArgsConstructor;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

@RequiredArgsConstructor
public class HttpServer {

  private final Module module;

  public HttpServer start() {
    Injector injector = Guice.createInjector(module);
    final ExecutorService executor = injector.getInstance(ExecutorService.class);

    final int LISTENING_PORT = 8000;
    final ServerSocket[] listener = {null};
    final Socket[] connection = new Socket[1];

    executor.execute(() ->
    {
      try {
        listener[0] = new ServerSocket(LISTENING_PORT, 8000);
        System.out.println("Listening on port " + LISTENING_PORT);
        while (true) {

          connection[0] = listener[0].accept();
          RequestHandler parser = RequestHandler.getInstance(connection[0], "src/main/resources/srv/www/");
          executor.execute(parser);

        }
      } catch (Exception e) {
        System.out.println("Error:  " + e);
        return;
      }
    });
    return this;
  }
}