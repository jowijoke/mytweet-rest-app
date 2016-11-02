package org.wit.mytweet.main;

import android.app.Application;
import android.util.Log;

import org.wit.mytweet.models.Portfolio;
import org.wit.mytweet.models.PortfolioSerializer;
import org.wit.mytweet.sqlite.DbHelper;

/**
 * Created by User on 02/10/2016.
 */

public class MyTweetApp extends Application {
    public Portfolio portfolio;
    private static final String FILENAME = "portfolio.json";
    static final String TAG = "MyTweetApp";
    public DbHelper dbHelper = null;
    protected static MyTweetApp app;


    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        Log.d(TAG, "MyTweet app launched");
        PortfolioSerializer serializer = new PortfolioSerializer(this, FILENAME);
        portfolio = new Portfolio(serializer);



    }

    public static MyTweetApp getApp() {
        return app;
    }

}
