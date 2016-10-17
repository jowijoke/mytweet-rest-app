package org.wit.mytweet.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.Random;

/**
 * Created by User on 03/10/2016.
 */

public class Tweet {
    public long id;
    public String message;
    public Long date;
    public String user;

    private static final String JSON_USER = "user";
    private static final String JSON_ID = "id";
    private static final String JSON_MESSAGE = "message";
    private static final String JSON_DATE = "date";


    public Tweet(String message, String user) {
        id = unsignedLong();
        id = new Random().nextLong();
        this.message = message;
        date = new Date().getTime();
        this.user = user;
    }

    /**
     * Generate a long greater than zero
     *
     * @return Unsigned Long value greater than zero
     */
    private Long unsignedLong() {
        long rndVal = 0;
        do {
            rndVal = new Random().nextLong();
        } while (rndVal <= 0);
        return rndVal;
    }

    public Tweet(JSONObject json) throws JSONException {
        id = json.getLong(JSON_ID);
        date = json.getLong(JSON_DATE);
        message = json.getString(JSON_MESSAGE);
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_ID, Long.toString(id));
        json.put(JSON_USER, user);
        json.put(JSON_DATE, date);
        json.put(JSON_MESSAGE, message);
        return json;
    }

    public String getDateString() {
        return "Registered:" + dateString();
    }

    private String dateString() {
        String dateFormat = "EEE d MMM yyyy H:mm";
        return android.text.format.DateFormat.format(dateFormat, date).toString();
    }


}
