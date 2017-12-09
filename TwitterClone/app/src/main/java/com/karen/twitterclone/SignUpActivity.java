package com.karen.twitterclone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener{

    private static final String TAG = SignUpActivity.class.getSimpleName();
    private ImageView logo;
    private RelativeLayout relativeLayout;
    private TextView signInView;
    private EditText username;
    private EditText password;
    private EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ParseAnalytics.trackAppOpenedInBackground(getIntent());
        initView();
    }

    private void initView() {
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        password.setOnKeyListener(SignUpActivity.this);
        email = findViewById(R.id.email);
        logo = findViewById(R.id.imageView);
        relativeLayout = findViewById(R.id.relLayout);
        signInView = findViewById(R.id.signIn);
        signInView.setOnClickListener(this);
        relativeLayout.setOnClickListener(this);
        logo.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.signIn:
                signIn();
                break;
            case R.id.imageView:
            case R.id.relLayout:
                hideKeyBoard();
                break;
            default:
                break;
        }

    }

    private void hideKeyBoard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    private void signIn() {
        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void signUp(View view) {

        if (username.getText().toString().isEmpty() || email.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
            Toast.makeText(this, "A username, email and password are required.", Toast.LENGTH_SHORT).show();
        } else {

            ParseUser user = new ParseUser();
            user.setUsername(username.getText().toString());
            user.setPassword(password.getText().toString());
            user.setEmail(email.getText().toString());

            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        navigateToUserListActivity();
                    } else {
                        Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void navigateToUserListActivity() {

    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {

        }
        return false;
    }
}
