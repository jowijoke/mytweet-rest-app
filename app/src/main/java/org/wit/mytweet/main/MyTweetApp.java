package org.wit.mytweet.main;

import android.app.Application;

import org.wit.mytweet.models.Portfolio;
import org.wit.mytweet.models.PortfolioSerializer;

import static org.wit.android.helpers.LogHelpers.info;

/**
 * Created by User on 02/10/2016.
 */

public class MyTweetApp extends Application {
    public Portfolio portfolio;
    private static final String FILENAME = "portfolio.json";
    protected static MyTweetApp app;


    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        PortfolioSerializer serializer = new PortfolioSerializer(this, FILENAME);
        info(this, "TweetListActivity app launched");


    }

    public static MyTweetApp getApp() {
        return app;
    }

}
