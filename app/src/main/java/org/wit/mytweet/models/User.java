package org.wit.mytweet.models;

/**
 * Created by john on 28/12/2016.
 */

public class User {
    public String _id;
    public String firstName;
    public String lastName;
    public String email;
    public String password;
    public String followers;

    public User(String firstName, String lastName, String email, String password)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.followers = followers;
    }
}