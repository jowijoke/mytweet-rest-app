package org.wit.mytweet.models;

/**
 * Created by john on 29/12/2016.
 */

public class Token {
    public boolean success;
    public String token;
    public User user;

    public Token(boolean success, String token)
    {
        this.success = success;
        this.token = token;
    }
}

