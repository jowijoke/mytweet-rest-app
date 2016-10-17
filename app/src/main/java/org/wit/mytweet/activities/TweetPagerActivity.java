package org.wit.mytweet.activities;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import org.wit.mytweet.R;
import org.wit.mytweet.main.MyTweetApp;
import org.wit.mytweet.models.Portfolio;
import org.wit.mytweet.models.Tweet;

import java.util.ArrayList;

import static org.wit.android.helpers.LogHelpers.info;

public class TweetPagerActivity extends AppCompatActivity implements TextWatcher, ViewPager.OnPageChangeListener {
    private ViewPager viewPager;
    private ArrayList<Tweet> tweets;
    private PagerAdapter pagerAdapter;
    private Portfolio portfolio;
    EditText editTweet;
    TextView countdown;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.app_name);
        setContentView(R.layout.fragment_tweet);
        viewPager = new ViewPager(this);
        viewPager.setId(R.id.viewPager);
        setContentView(viewPager);
        setTweetList();
        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), tweets);
        viewPager.setAdapter(pagerAdapter);
        setCurrentItem();
        viewPager.addOnPageChangeListener(this);


        editTweet = (EditText) findViewById(R.id.editTweet);
        countdown = (TextView) findViewById(R.id.countdown);

        editTweet.addTextChangedListener(this);

    }

    private void setTweetList() {
        MyTweetApp app = (MyTweetApp) getApplication();
        portfolio = app.portfolio;
        tweets = portfolio.tweets;
    }

    /*
* Ensure selected Tweet is shown in details view
*/
    private void setCurrentItem() {
        Long tweetId = (Long) getIntent().getSerializableExtra(TweetFragment.EXTRA_TWEET_ID);
        for (int i = 0; i < tweets.size(); i++) {
            if (tweets.get(i).id == (tweetId)) {
                viewPager.setCurrentItem(i);
                break;
            }
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

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class PagerAdapter extends FragmentStatePagerAdapter {
        private ArrayList<Tweet> tweets;

        public PagerAdapter(FragmentManager fm, ArrayList<Tweet> tweets) {
            super(fm);
            this.tweets = tweets;
        }

        @Override
        public int getCount() {
            return tweets.size();
        }

        @Override
        public Fragment getItem(int pos) {
            Tweet tweet = tweets.get(pos);
            Bundle args = new Bundle();
            args.putSerializable(TweetFragment.EXTRA_TWEET_ID, tweet.id);
            TweetFragment fragment = new TweetFragment();
            fragment.setArguments(args);
            return fragment;
        }


    }
}


