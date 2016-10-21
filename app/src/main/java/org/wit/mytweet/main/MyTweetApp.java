package org.wit.mytweet.main;

import android.app.Application;

import org.wit.mytweet.models.Portfolio;
import org.wit.mytweet.models.PortfolioSerializer;
import org.wit.mytweet.models.User;

import java.util.ArrayList;
import java.util.List;

import static org.wit.android.helpers.LogHelpers.info;

/**
 * Created by User on 02/10/2016.
 */

public class MyTweetApp extends Application {
    public List<User> users = new ArrayList<User>();
    public Portfolio portfolio;
    private static final String FILENAME = "portfolio.json";
    protected static MyTweetApp app;

    public void newUser(User user) {
        users.add(user);
    }

    public boolean validUser(String email, String password) {
        for (User user : users) {
            if (user.email.equals(email) && user.password.equals(password)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        PortfolioSerializer serializer = new PortfolioSerializer(this, FILENAME);
        portfolio = new Portfolio(serializer);

        info(this, "TweetListActivity app launched");


    }

    public static MyTweetApp getApp() {
        return app;
    }

}
