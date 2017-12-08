package com.karen.instagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener {
  EditText username;
  EditText password;
  TextView signInTextView;
  Button signUpButton;
  boolean signUpMode = true;
  ImageView imageView;
  RelativeLayout relativeLayout;

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
              navigateToUserListActivity();
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
              navigateToUserListActivity();
            } else {
              Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
          }
        });
      }
    }
  }

  private void navigateToUserListActivity() {
    Intent intent = new Intent(MainActivity.this, UserListActivity.class);
    startActivity(intent);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ParseAnalytics.trackAppOpenedInBackground(getIntent());
    initView();

    if (ParseUser.getCurrentUser() != null) {
      navigateToUserListActivity();
    }

  }

  private void initView() {
    username = findViewById(R.id.username);
    password = findViewById(R.id.password);
    password.setOnKeyListener(MainActivity.this);
    signInTextView = findViewById(R.id.changeSignUpToSignIn);
    signInTextView.setOnClickListener(this);
    signUpButton = findViewById(R.id.signUpButton);
    imageView = findViewById(R.id.logoImageView);
    imageView.setOnClickListener(this);
    relativeLayout = findViewById(R.id.relativeLayout);
    relativeLayout.setOnClickListener(this);
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
    } else if (v.getId() == R.id.logoImageView || v.getId() == R.id.relativeLayout) { //close keyboard if user clicks somewhere on screen
      InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
      inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }
  }

  @Override
  public boolean onKey(View v, int keyCode, KeyEvent event) {
    if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
      signUp(v);
    }
    return false;
  }

}
