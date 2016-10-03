package org.wit.mytweet.main;

import android.app.Application;

import org.wit.mytweet.models.User;

import java.util.ArrayList;
import java.util.List;

import static org.wit.android.helpers.LogHelpers.info;

/**
 * Created by User on 02/10/2016.
 */

public class MyTweetApp extends Application {

    public List<User> users = new ArrayList<User>();

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
        info(this, "MyTweet app launched");


    }




}
