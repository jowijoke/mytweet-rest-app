package org.wit.mytweet.main;

import org.wit.mytweet.models.Tweet;
import org.wit.mytweet.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by john on 28/12/2016.
 */

public interface  TweetService {

    @GET("/api/tweets")
    Call<List<Tweet>> getAllTweets();

    @GET("/api/users/{id}/tweets")
    Call<List<Tweet>> userTweets(@Path("id") String id);

    @POST("/api/users/{id}/tweets")
    Call<Tweet> makeTweet(@Path("id") String id, @Body Tweet tweet);

    @POST("/api/tweets/change")
    Call<Tweet> changeTweet(@Body Tweet tweet);

    @POST("/api/tweets/delete")
    Call<Tweet> deleteTweet(@Body Tweet tweet);

    @DELETE("/api/users/{id}/tweets")
    Call<User> deleteUserTweet(@Path("id") String id);
}