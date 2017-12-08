package com.karen.ubercloneapp;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;


public class StarterApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();

    Parse.enableLocalDatastore(this);

    Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
            .applicationId("38fc8c94db1a5e38aef7902ed05826c1292f1895")
            .clientKey("3f0ae9db32c3e9593d478b9f02791a7417d3e199")
            .server("https://parse.bitratesoft.com:443/parse/")
            .build()
    );

    ParseACL defaultACL = new ParseACL();
    defaultACL.setPublicReadAccess(true);
    defaultACL.setPublicWriteAccess(true);
    ParseACL.setDefaultACL(defaultACL, true);

  }
}
