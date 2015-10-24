package com.benglasser.http.modules;

import com.benglasser.http.HttpServer;
import com.google.inject.Binder;
import com.google.inject.Inject;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import lombok.SneakyThrows;

import javax.inject.Singleton;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by bglasser on 10/18/15.
 */
public class MainModule implements Module {

  public static final int SERVER_PORT = 8000;
  public static final int POOL_SIZE = 100;

  @Override
  public void configure(Binder binder) {
    // prefer providers
  }

  @Singleton
  @Provides
  public ExecutorService getExecutorService()
  {
    return  Executors.newFixedThreadPool(POOL_SIZE);
  }

  @Provides
  @SneakyThrows
  public ServerSocket getServerSocket(){ return new ServerSocket(SERVER_PORT); }

  @Singleton
  @Provides
  public HttpServer getHttpServer(ExecutorService executorService, ServerSocket serverSocket)
  {
    return new HttpServer(executorService, serverSocket);
  }
}
