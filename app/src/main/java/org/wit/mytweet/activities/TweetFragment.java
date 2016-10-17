package org.wit.mytweet.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.support.v4.app.Fragment;
import org.wit.android.helpers.ContactHelper;
import org.wit.mytweet.R;
import org.wit.mytweet.main.MyTweetApp;
import org.wit.mytweet.models.Portfolio;
import org.wit.mytweet.models.Tweet;

import static org.wit.android.helpers.ContactHelper.sendEmail;
import static org.wit.android.helpers.IntentHelper.navigateUp;

/**
 * Created by User on 17/10/2016.
 */

public class TweetFragment extends Fragment implements View.OnClickListener {

    public static final String EXTRA_TWEET_ID = "mytweet.TWEET_ID";

    private static final int REQUEST_CONTACT = 1;

    private Button ContactButton;
    private Button EmailButton;
    private Button TweetButton;

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

        return v;
    }


    private void addListeners(View v) {

        ContactButton = (Button) v.findViewById(R.id.contact);
        EmailButton = (Button) v.findViewById(R.id.tweet_EmailButton);
        TweetButton = (Button) v.findViewById(R.id.tweetButton);

        ContactButton.setOnClickListener(this);
        EmailButton.setOnClickListener(this);
        TweetButton.setOnClickListener(this);


    }

    public void updateControls() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                navigateUp(getActivity());
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        portfolio.saveTweets();
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
            case R.id.contact:
                Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(i, REQUEST_CONTACT);
                if (tweet.contact != null) {
                    ContactButton.setText("Tenant: " + tweet.contact);
                }
                break;
            case R.id.tweet_EmailButton:
                sendEmail(getActivity(), "emailAddress", getString(R.string.tweet_email_subject), tweet.getTweetReport(getActivity()));
                break;


        }
    }
}
