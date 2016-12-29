package org.wit.mytweet.main;

import android.app.Application;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.wit.mytweet.models.Portfolio;
import org.wit.mytweet.models.Tweet;
import org.wit.mytweet.models.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by User on 02/10/2016.
 */

public class MyTweetApp extends Application {

    public TweetService tweetService;
    public boolean tweetServiceAvailable = false;
    public Portfolio portfolio;
    public String service_url = "http://10.0.2.2:4000";   // Standard Emulator IP Address

    public User currentUser;
    public List<Tweet> tweets = new ArrayList<Tweet>();
    public List<User> users = new ArrayList<User>();
    //private static final String FILENAME = "portfolio.json";
    static final String TAG = "MyTweetApp";
    //public DbHelper dbHelper = null;
    protected static MyTweetApp app;


    @Override
    public void onCreate() {
        super.onCreate();
        Gson gson = new GsonBuilder().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(service_url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        tweetService = retrofit.create(TweetService.class);

        Log.v("Tweet", "MyTweet App Started");

        app = this;
        Log.d(TAG, "MyTweet app launched");
        //PortfolioSerializer serializer = new PortfolioSerializer(this, FILENAME);
        portfolio = new Portfolio(getApplicationContext());
    }

    public static MyTweetApp getApp() {
        return app;
    }

    public void newUser(User user)
    {
        users.add(user);
    }

    public boolean validUser (String email, String password)
    {
        for (User user : users)
        {
            if (user.email.equals(email) && user.password.equals(password))
            {
                currentUser = user;
                return true;
            }
        }
        return false;
    }

}
