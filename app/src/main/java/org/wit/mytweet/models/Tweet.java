package org.wit.mytweet.models;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.Random;

import static android.R.attr.id;

/**
 * Created by User on 03/10/2016.
 */

public class Tweet {
    public String _id;
    public String message;
    public Long date;
    public String contact;

    public Tweet() {
       // _id = String.valueOf(unsignedLong());
       // id = new Random().nextLong();
        date = new Date().getTime();

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

    public String getTweetReport(Context context) {


        String msg = message;
        String report = msg;
        return report;

    }

    public String getDateString() {
        return dateString();
    }

    private String dateString() {
        String dateFormat = "EEE d MMM yyyy H:mm";
        return android.text.format.DateFormat.format(dateFormat, date).toString();
    }


}
