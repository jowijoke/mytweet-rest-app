package org.wit.mytweet.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import org.wit.mytweet.R;
import org.wit.mytweet.activities.settings.SettingsActivity;
import org.wit.mytweet.main.MyTweetApp;
import org.wit.mytweet.models.Tweet;
import org.wit.mytweet.models.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.wit.mytweet.R.id.signup;

/**
 * Created by john on 08/01/2017.
 */

public class OptionsActivity extends AppCompatActivity implements Callback<List<User>>, View.OnClickListener{

    private Spinner sfollowers;
    private Spinner sRequests;
    public List <User> following   = new ArrayList<>();
    public List <User> requests   = new ArrayList<>();
    MyTweetApp app;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.app_name);
        setContentView(R.layout.activity_welcome);

        final Button bviewButton = (Button) findViewById(R.id.viewButton);
        final Button bunfollow = (Button) findViewById(R.id.unfollow);
        final Button baddButton = (Button) findViewById(R.id.addButton);

        sfollowers = (Spinner)findViewById(R.id.followers);
        sRequests = (Spinner)findViewById(R.id.requests);


        bviewButton.setOnClickListener(this);
        bunfollow.setOnClickListener(this);
        baddButton.setOnClickListener(this);

        Call<List<User>> call1 = (Call<List<User>>) app.tweetService.following(MyTweetApp.currentUser._id);
        call1.enqueue(this);

        Call<List<User>> call2 = (Call<List<User>>) app.tweetService.requesting(MyTweetApp.currentUser._id);
        call2.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                requests = response.body();
                RequestAdapter radapter = new RequestAdapter(requests);
                sRequests.setAdapter(radapter);
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });






        app = MyTweetApp.getApp();
    }



    @Override
    public void onClick(View v) {

    }

    @Override
    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
        following = response.body();
        FollowerAdapter adapter = new FollowerAdapter(following);
        sfollowers.setAdapter(adapter);

    }

    @Override
    public void onFailure(Call<List<User>> call, Throwable t) {

    }


private class FollowerAdapter extends BaseAdapter implements SpinnerAdapter {
        private final List<User> data;

        public FollowerAdapter(List<User> data) {
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View recycle, ViewGroup parent) {
            TextView text;
            if (recycle != null) {
                text = (TextView) recycle;
            } else {
                text = (TextView) getLayoutInflater().inflate(
                        android.R.layout.simple_dropdown_item_1line, parent, false
                );
            }
            text.setTextColor(Color.BLACK);
            text.setText(data.get(position).firstName);
            return text;
        }
    }

    private class RequestAdapter extends BaseAdapter implements SpinnerAdapter {
        private final List<User> data;

        public RequestAdapter(List<User> data) {
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View recycle, ViewGroup parent) {
            TextView text;
            if (recycle != null) {
                text = (TextView) recycle;
            } else {
                text = (TextView) getLayoutInflater().inflate(
                        android.R.layout.simple_dropdown_item_1line, parent, false
                );
            }
            text.setTextColor(Color.BLACK);
            text.setText(data.get(position).firstName);
            return text;
        }
    }




}
