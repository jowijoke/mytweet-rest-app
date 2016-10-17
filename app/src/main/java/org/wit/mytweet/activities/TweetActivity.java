package org.wit.mytweet.activities;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import org.wit.mytweet.R;

public class TweetActivity extends AppCompatActivity implements TextWatcher {
    int count = 140;
    EditText editTweet;
    TextView countdown;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.app_name);
        setContentView(R.layout.activity_tweet);

        editTweet = (EditText) findViewById(R.id.editTweet);
        countdown = (TextView) findViewById(R.id.countdown);

        editTweet.addTextChangedListener(this);

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {


    }

    @Override
    public void afterTextChanged(Editable s) {
        countdown.setText(String.valueOf(140 - s.length()));


    }

}


