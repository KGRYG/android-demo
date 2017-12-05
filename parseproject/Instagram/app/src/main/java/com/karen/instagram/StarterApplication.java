package com.karen.instagram;

import android.app.Application;
import com.parse.Parse;
import com.parse.ParseACL;


public class StarterApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();

    Parse.enableLocalDatastore(this);

    Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
            .applicationId("4fbde0ba72311e2f57ca5d483f82bae09f8bda0e")
            .clientKey("550dd6987d6a5b50ac758b4ff7cb2b97a1a829ce")
            .server("http://54.92.215.143:80/parse/")
            .build()
    );


    ParseACL defaultACL = new ParseACL();
    defaultACL.setPublicReadAccess(true);
    defaultACL.setPublicWriteAccess(true);
    ParseACL.setDefaultACL(defaultACL, true);

  }
}
