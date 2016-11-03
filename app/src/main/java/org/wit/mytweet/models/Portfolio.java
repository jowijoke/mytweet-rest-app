package org.wit.mytweet.models;

import android.util.Log;

import java.util.ArrayList;

import static org.wit.android.helpers.LogHelpers.info;

/**
 * Created by User on 17/10/2016.
 */

public class Portfolio {
    public ArrayList<Tweet> tweets;
    private PortfolioSerializer serializer;

    public Portfolio(PortfolioSerializer serializer) {
        this.serializer = serializer;
        try {
            tweets = serializer.loadTweets();
        } catch (Exception e) {
            info(this, "Error loading Tweets: " + e.getMessage());
            tweets = new ArrayList<Tweet>();
        }
    }

    public boolean saveTweets() {
        try {
            serializer.saveTweets(tweets);
            info(this, "Tweets saved to file");
            return true;
        } catch (Exception e) {
            info(this, "Error saving Tweets: " + e.getMessage());
            return false;
        }
    }

    public void addTweet(Tweet tweet) {
        tweets.add(tweet);
    }

    public Tweet getTweet(Long id) {
        Log.i(this.getClass().getSimpleName(), "id: " + id);

        for (Tweet msg : tweets) {
            if (id.equals(msg.id)) {
                return msg;
            }
        }
        info(this, "failed to find tweet. returning first element array to avoid crash");
        return null;
    }

    public void deleteTweet(Tweet tweet) {
        tweets.remove(tweet);
        saveTweets();
    }

    public void deleteAllTweet() {
        tweets.clear();
        saveTweets();
    }


}
