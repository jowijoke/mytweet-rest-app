package org.wit.mytweet.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.wit.android.helpers.ContactHelper;
import org.wit.android.helpers.IntentHelper;
import org.wit.mytweet.R;
import org.wit.mytweet.main.MyTweetApp;
import org.wit.mytweet.models.Portfolio;
import org.wit.mytweet.models.Tweet;

import static org.wit.android.helpers.ContactHelper.sendEmail;
import static org.wit.android.helpers.IntentHelper.navigateUp;

/**
 * Created by User on 17/10/2016.
 */

public class TweetFragment extends Fragment implements TextWatcher, View.OnClickListener {

    public static final String EXTRA_TWEET_ID = "TWEET_ID";

    private static final int REQUEST_CONTACT = 1;

    private Button ContactButton;
    private Button EmailButton;
    private Button TweetButton;
    private TextView tDate;
    EditText editTweet;
    TextView countdown;

    private Tweet tweet;
    private Portfolio portfolio;

    private String emailAddress;

    MyTweetApp app;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        Long msgId = (Long) getArguments().getSerializable(EXTRA_TWEET_ID);

        app = MyTweetApp.getApp();
        portfolio = app.portfolio;
        tweet = portfolio.getTweet(msgId);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        super.onCreateView(inflater, parent, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_tweet, parent, false);

        addListeners(v);


        if (tweet.message != null) {
            updateControls(tweet);
        } else {
            tDate.setText(tweet.getDateString());// Display current date and time if this is a new tweet being created.
        }
        return v;

    }


    private void addListeners(View v) {

        ContactButton = (Button) v.findViewById(R.id.contact);
        EmailButton = (Button) v.findViewById(R.id.tweet_EmailButton);
        TweetButton = (Button) v.findViewById(R.id.tweetButton);
        editTweet = (EditText) v.findViewById(R.id.editTweet);
        countdown = (TextView) v.findViewById(R.id.countdown);
        tDate = (TextView) v.findViewById(R.id.tweet_date);

        editTweet.addTextChangedListener(this);
        ContactButton.setOnClickListener(this);
        EmailButton.setOnClickListener(this);
        TweetButton.setOnClickListener(this);
        tDate.setOnClickListener(this);


    }

    /*
    Method to display tweet details if the tweet is already created & selected from the list of tweets.
    */
    public void updateControls(Tweet tweet) {
        editTweet.setText(tweet.message);
        editTweet.setEnabled(false);
        TweetButton.setClickable(false);
        int totalCountdown = 140;
        countdown.setText(Integer.toString(totalCountdown - tweet.message.length()));
        tDate.setText(tweet.getDateString());

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (tweet.message == null) {
                    portfolio.deleteTweet(tweet);

                }
                navigateUp(getActivity());
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        portfolio.updateTweet(tweet);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        switch (requestCode) {
            case REQUEST_CONTACT:
                String name = ContactHelper.getContact(getActivity(), data);
                emailAddress = ContactHelper.getEmail(getActivity(), data);
                ContactButton.setText(name + " : " + emailAddress);
                tweet.contact = name;
                break;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tweetButton:
                if (editTweet.getText().length() > 0) {
                    tweet.message = editTweet.getText().toString();
                    IntentHelper.startActivity(getActivity(), TweetListActivity.class);
                    portfolio.addTweet(tweet);
                    break;
                } else {

                    Toast toast = Toast.makeText(getActivity(), "Please enter your message", Toast.LENGTH_SHORT);
                    toast.show();
                    break;
                }

            case R.id.contact:
                Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(i, REQUEST_CONTACT);
                if (tweet.contact != null) {
                    ContactButton.setText("Contact: " + tweet.contact);
                }
                break;

            case R.id.tweet_EmailButton:
                sendEmail(getActivity(), emailAddress, getString(R.string.tweet_email_subject), tweet.getTweetReport(getActivity()));
                break;


        }
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
