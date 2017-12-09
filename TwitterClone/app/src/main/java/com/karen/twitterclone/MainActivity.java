package com.karen.twitterclone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.parse.ParseAnalytics;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener{
    private static final String TAG = MainActivity.class.getSimpleName();
    private ImageView logo;
    private RelativeLayout relativeLayout;
    private TextView signUpView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ParseAnalytics.trackAppOpenedInBackground(getIntent());
        initView();
    }

    private void initView() {
        setTitle("Twitter - Login");
        logo = findViewById(R.id.imageView);
        relativeLayout = findViewById(R.id.relLayout);
        signUpView = findViewById(R.id.signUp);
        signUpView.setOnClickListener(this);
        relativeLayout.setOnClickListener(this);
        logo.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.signUp:
                signUp();
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

    private void signUp() {
        Log.i(TAG, "signUp clicked");
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {

        }
        return false;
    }

}
