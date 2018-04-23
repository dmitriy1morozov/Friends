package com.gbksoft.android.test.app;

import android.app.Application;
import com.facebook.appevents.AppEventsLogger;

public class MyApplication extends Application {
  @Override public void onCreate() {
    super.onCreate();
    AppEventsLogger.activateApp(this);
  }
}