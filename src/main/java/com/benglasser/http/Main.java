package com.benglasser.http;

import com.benglasser.http.modules.MainModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

/**
 * Created by bglasser on 10/19/15.
 */
public class Main
{
  public static void main(String[] args) throws IOException
  {
    Injector injector = Guice.createInjector(new MainModule());
    HttpServer myServer = new HttpServer(injector.getInstance(ExecutorService.class));
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
    for (; ; ) ;
  }
}
