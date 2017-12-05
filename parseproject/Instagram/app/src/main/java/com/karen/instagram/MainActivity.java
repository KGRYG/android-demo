package com.karen.instagram;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
  EditText username;
  EditText password;
  TextView signInTextView;
  Button signUpButton;
  boolean signUpMode = true;


  public void signUp(View view) {
    if (username.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
      Toast.makeText(this, "A username and password are required.", Toast.LENGTH_SHORT).show();
    } else {
      ParseUser user = new ParseUser();
      user.setUsername(username.getText().toString());
      user.setPassword(password.getText().toString());

      if (signUpMode) {
        user.signUpInBackground(new SignUpCallback() {
          @Override
          public void done(ParseException e) {
            if (e == null) {
              Log.i("Sign up", "Sucess");
            } else {
              Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
          }
        });
      } else {

        ParseUser.logInInBackground(username.getText().toString(), password.getText().toString(), new LogInCallback() {
          @Override
          public void done(ParseUser user, ParseException e) {
            if (e == null || user != null) {

            } else {
              Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
          }
        });
      }
    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ParseAnalytics.trackAppOpenedInBackground(getIntent());
    initView();

  }

  private void initView() {
    username = findViewById(R.id.username);
    password = findViewById(R.id.password);
    signInTextView = findViewById(R.id.changeSignUpToSignIn);
    signInTextView.setOnClickListener(this);
    signUpButton = findViewById(R.id.signUpButton);
  }

  @Override
  public void onClick(View v) {
    if (v.getId() == R.id.changeSignUpToSignIn) {

      if (signUpMode) {
        signUpMode = false;
        signUpButton.setText("SIGN IN");
        signInTextView.setText("SIGN UP");
      } else {
        signUpMode = true;
        signUpButton.setText("SIGN UP");
        signInTextView.setText("SIGN IN");

      }
    }
  }
}
