package org.wit.mytweet.main;

import android.app.Application;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.wit.android.helpers.IntentHelper;
import org.wit.mytweet.activities.TweetListActivity;
import org.wit.mytweet.models.Portfolio;
import org.wit.mytweet.models.Token;
import org.wit.mytweet.models.Tweet;
import org.wit.mytweet.models.User;
import org.wit.mytweet.activities.LoginActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.wit.android.helpers.LogHelpers.info;

/**
 * Created by User on 02/10/2016.
 */

public class MyTweetApp extends Application implements Callback<Token> {

    public TweetServiceOpen tweetServiceOpen;
    public TweetService tweetService;

    public boolean tweetServiceAvailable = false;
    public Portfolio portfolio;
    public String service_url = "http://10.0.2.2:4000";   // Standard Emulator IP Address

    public static User currentUser;
    public List<Tweet> tweets = new ArrayList<Tweet>();
    public List<User> users = new ArrayList<User>();
    //private static final String FILENAME = "portfolio.json";
    static final String TAG = "MyTweetApp";
    //public DbHelper dbHelper = null;
    protected static MyTweetApp app;


    @Override
    public void onCreate() {
        super.onCreate();
        Log.v("Tweet", "MyTweet App Started");
        tweetServiceOpen = RetrofitServiceFactory.createService(TweetServiceOpen.class);
        app = this;
        Log.d(TAG, "MyTweet app launched");
        //PortfolioSerializer serializer = new PortfolioSerializer(this, FILENAME);
        portfolio = new Portfolio(getApplicationContext());

        Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(service_url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        tweetService = retrofit.create(TweetService.class);

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
        User user = new User ("", "", email, password);
        tweetServiceOpen.authUser(user);
        Call<Token> call = (Call<Token>) tweetServiceOpen.authUser (user);
        call.enqueue(this);
        return true;
    }

    @Override
    public void onResponse(Call<Token> call, Response<Token> response) {
        if(response.isSuccessful()){
            Token auth = response.body();
            tweetService =  RetrofitServiceFactory.createService(TweetService.class, auth.token);
            currentUser = auth.user;
            Log.v("MyTweet", "Authenticated ");
            info(this, "currentUser " + currentUser);
            LoginActivity.onLoginSuccess();
        }else {
            Toast toast = Toast.makeText(this, "Unable to authenticate with Tweet Service", Toast.LENGTH_SHORT);
            toast.show();
            Log.v("MyTweet", "Failed to Authenticated!");
        }
    }

    @Override
    public void onFailure(Call<Token> call, Throwable t) {

    }
}
