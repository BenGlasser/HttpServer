package com.benglasser.http.modules;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provides;

import javax.inject.Singleton;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by bglasser on 10/18/15.
 */
public class MainModule implements Module {
  @Override
  public void configure(Binder binder) {
    // prefer providers
  }

  @Singleton
  @Provides
  public ExecutorService getExecutorService()
  {
    return  Executors.newFixedThreadPool(100);
  }
}
