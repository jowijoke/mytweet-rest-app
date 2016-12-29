package org.wit.mytweet.main;

import org.wit.mytweet.models.Token;
import org.wit.mytweet.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by john on 29/12/2016.
 */

public interface TweetServiceOpen {

    @GET("/api/users")
    Call<List<User>> getAllUsers();

    @POST("/api/users")
    Call<User> createUser(@Body User User);

    @POST("/api/users/authenticate")
    Call<Token> authUser(@Body User user);

}
