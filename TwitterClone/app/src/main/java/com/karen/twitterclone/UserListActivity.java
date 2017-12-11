package com.karen.twitterclone;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserListActivity extends AppCompatActivity {
    private static final String TAG = UserListActivity.class.getSimpleName();
    ListView listView;
    ArrayAdapter arrayAdapter;
    List<String> users;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        initView();
    }

    private void initView() {
        users = new ArrayList<>();
        listView = findViewById(R.id.listView);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_checked, users);
        listView.setAdapter(arrayAdapter);
        isFollowingListEmpty();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckedTextView checkedTextView = (CheckedTextView) view;
                if (checkedTextView.isChecked()) {
                    ParseUser.getCurrentUser().add("isFollowing", users.get(position));
                    ParseUser.getCurrentUser().saveInBackground();
                } else {
                    ParseUser.getCurrentUser().getList("isFollowing").remove(users.get(position));
                    List newlist = ParseUser.getCurrentUser().getList("isFollowing");
                    ParseUser.getCurrentUser().remove("isFollowing");
                    ParseUser.getCurrentUser().put("isFollowing", newlist);

                    ParseUser.getCurrentUser().saveInBackground();
                }
            }
        });

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null && objects.size() > 0) {
                    for (ParseUser u : objects) {
                        users.add(u.getUsername());
                    }
                    arrayAdapter.notifyDataSetChanged();

                    for (String u : users) {
                        if (ParseUser.getCurrentUser().getList("isFollowing").contains(u)) {
                            listView.setItemChecked(users.indexOf(u), true);
                        }
                    }
                }
            }
        });
    }

    private void isFollowingListEmpty() {
        if (ParseUser.getCurrentUser().get("isFollowing") == null) {
            ParseUser.getCurrentUser().put("isFollowing", new ArrayList<String>());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.tweet_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.tweet:
                tweet();
                break;
            case R.id.logout:
                logout();
                break;
            case R.id.feedList:
                navigateToFeed();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void navigateToFeed() {
        Intent intent = new Intent(this, FeedActivity.class);
        startActivity(intent);
    }

    private void tweet() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Send a tweet");
        final EditText tweetContent = new EditText(this);
        builder.setView(tweetContent)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ParseObject tweet = new ParseObject("Tweet");
                        tweet.put("username", ParseUser.getCurrentUser().getUsername());
                        tweet.put("tweet", tweetContent.getText().toString());

                        tweet.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {

                                if (e == null) {
                                    Toast.makeText(UserListActivity.this, "Tweet sent successfully!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(UserListActivity.this, "Tweet failed - pls try again later", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                })
                .show();
    }

    private void logout() {
        ParseUser.logOut();
        Intent intent = new Intent(UserListActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
