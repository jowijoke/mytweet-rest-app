package org.wit.mytweet.models;

import java.util.UUID;

/**
 * Created by User on 02/10/2016.
 */

public class User {
    public UUID id;
    public String firstName;
    public String lastName;
    public String email;
    public String password;

    public User() {
        this.id = UUID.randomUUID();
    }


}
