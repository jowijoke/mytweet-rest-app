package org.wit.mytweet.main;

import android.app.Application;

import static org.wit.android.helpers.LogHelpers.info;

/**
 * Created by User on 02/10/2016.
 */

public class MyTweetApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        info(this, "MyTweet app launched");


    }


}
