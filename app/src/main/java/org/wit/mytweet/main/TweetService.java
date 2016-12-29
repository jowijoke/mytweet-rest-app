package org.wit.mytweet.main;

import org.wit.mytweet.models.Tweet;
import org.wit.mytweet.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by john on 28/12/2016.
 */

public interface  TweetService {
    @GET("/api/users")
    Call<List<User>> getAllUsers();

    @GET("/api/users/{id}")
    Call<User> getUser(@Path("id") String id);

    @POST("/api/users")
    Call<User> createUser(@Body User User);

    @GET("/api/tweets")
    Call<List<Tweet>> getAllTweets();

    @POST("/api/users/{id}/tweets")
    Call<Tweet> makeTweet(@Path("id") String id, @Body Tweet tweet);
}