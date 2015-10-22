package com.benglasser.http;

import com.benglasser.http.modules.MainModule;
import com.google.inject.Guice;

import java.io.IOException;

/**
 * Created by bglasser on 10/19/15.
 */
public class Main {
  public static void main(String[] args) throws IOException {
    HttpServer myServer = new HttpServer(new MainModule());
    myServer.start();

    Runtime.getRuntime().addShutdownHook(new Thread()
    {
      @Override
      public void run()
      {
        //TODO: once there is a shutdown hook in HttpServer this would be an ideal place to call it.
        System.out.println("Shutting down...");
      }
    });

    // run forever
    for(;;);

  }
}
