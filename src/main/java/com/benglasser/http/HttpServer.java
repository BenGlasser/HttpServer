package com.benglasser.http;

<<<<<<< Updated upstream
import com.benglasser.http.handlers.RequestHandler;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

=======
import java.io.IOException;
>>>>>>> Stashed changes
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

<<<<<<< Updated upstream
@Getter
@Setter
@RequiredArgsConstructor
=======
import com.benglasser.http.modules.MainModule;
import com.benglasser.http.requests.handlers.RequestHandler;
import com.google.inject.Guice;
import com.google.inject.Injector;


>>>>>>> Stashed changes
public class HttpServer {

  private final ExecutorService executor;
  private final ServerSocket listener;

<<<<<<< Updated upstream
  public HttpServer start() {
    try {
      System.out.println("Listening on port " + listener.getLocalPort());
      for (; ; ) {
        Socket connection = listener.accept();
        RequestHandler handler = RequestHandler.getInstance(connection, "src/main/resources/srv/www");
        executor.execute(handler);
=======
  HttpServer(Injector injector)
  {
    this.executor = injector.getInstance(ExecutorService.class);

    try {
      final ServerSocket listener = injector.getInstance(ServerSocket.class);
      System.out.println("Listening on port " + MainModule.PORT);
      while (true) {

        final Socket connection = listener.accept();
        final RequestHandler parser = injector.getInstance(connection, "src/main/resources/srv/www/");
        executor.execute(parser);
>>>>>>> Stashed changes
      }
    } catch (Exception e) {
      System.out.println("Error:  " + e);
    }
    return this;
  }
}
