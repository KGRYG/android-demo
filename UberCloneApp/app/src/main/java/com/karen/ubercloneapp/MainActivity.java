package com.karen.ubercloneapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    Switch aSwitch;
    String userType;


    public void getStarted(View view) {
        userType = aSwitch.isChecked() ? "driver" : "rider";
        ParseUser.getCurrentUser().put("riderOrDriver", userType);
        ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                redirectActivity();
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        if (ParseUser.getCurrentUser() == null) {
            ParseAnonymousUtils.logIn(new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if (e == null) {
                        Log.i(TAG, "Login Success");
                    } else {
                        Log.i(TAG, "Login Failed");
                    }
                }
            });
        } else {
            if (ParseUser.getCurrentUser().get("riderOrDriver") != null) {
                redirectActivity();
            }
        }
    }

    private void initView() {
        ParseAnalytics.trackAppOpenedInBackground(getIntent());
        getSupportActionBar().hide();
        aSwitch = findViewById(R.id.userTypeSwitch);
    }

    private void redirectActivity() {
        Intent intent = new Intent(MainActivity.this, "rider".equals(ParseUser.getCurrentUser().get("riderOrDriver")) ? RiderActivity.class : ViewRequestsActivity.class);
        startActivity(intent);
    }

}
