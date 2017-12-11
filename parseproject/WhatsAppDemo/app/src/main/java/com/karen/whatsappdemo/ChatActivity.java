package com.karen.whatsappdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
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

public class ChatActivity extends AppCompatActivity {
    private static final String TAG = ChatActivity.class.getSimpleName();
    String activeUser = "";
    EditText chatEditText;
    ListView listView;
    List<String> messages;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initView();

        ParseQuery<ParseObject> query = new ParseQuery<>("Message");

        query.whereContainedIn("sender", Arrays.asList( ParseUser.getCurrentUser().getUsername(), activeUser))
                .whereContainedIn("recipient", Arrays.asList( ParseUser.getCurrentUser().getUsername(), activeUser))
                .orderByAscending("createdAt")
                .findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if (e == null && objects.size() > 0) {
                            for (ParseObject o : objects) {
                                messages.add(o.getString("sender").equals(activeUser) ? "> " + o.getString("message") : o.getString("message"));
                            }

                            arrayAdapter.notifyDataSetChanged();
                        }
                    }
                });



    }

    private void initView() {
        Intent intent = getIntent();
        activeUser = intent.getStringExtra("username");
        setTitle("Chat with " + activeUser);
        listView = findViewById(R.id.chatListView);
        chatEditText = findViewById(R.id.chatEditText);
        messages = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, messages);
        listView.setAdapter(arrayAdapter);
    }

    public void sendChat(View view) {
        ParseObject message = new ParseObject("Message");
        message.put("sender", ParseUser.getCurrentUser().getUsername());
        message.put("recipient", activeUser);
        message.put("message", chatEditText.getText().toString());

        message.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    messages.add(chatEditText.getText().toString());
                    chatEditText.setText("");
                    arrayAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}
