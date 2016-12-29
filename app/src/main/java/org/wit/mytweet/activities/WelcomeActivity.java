package org.wit.mytweet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.wit.mytweet.R;
import org.wit.mytweet.main.MyTweetApp;
import org.wit.mytweet.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.wit.android.helpers.LogHelpers.info;


public class WelcomeActivity extends AppCompatActivity implements Callback<List<User>>, View.OnClickListener {

    private MyTweetApp app;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.app_name);
        setContentView(R.layout.activity_welcome);

        final Button login = (Button) findViewById(R.id.welcomeLogin);
        final Button signup = (Button) findViewById(R.id.welcomeSignup);

        login.setOnClickListener(this);
        signup.setOnClickListener(this);

        app = (MyTweetApp) getApplication();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.welcomeLogin:
                info(this, "LoginActivity Pressed");
                startActivity(new Intent(this, LoginActivity.class));
                break;


            case R.id.welcomeSignup:
                info(this, "SignupActivity Pressed");
                startActivity(new Intent(this, SignupActivity.class));
                break;

        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        app.currentUser = null;
        Call<List<User>> call1 = (Call<List<User>>) app.tweetService.getAllUsers();
        call1.enqueue(this);

    }


    @Override
    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
        serviceAvailableMessage();
        app.users = response.body();
        app.tweetServiceAvailable = true;
    }

    @Override
    public void onFailure(Call<List<User>> call, Throwable t) {
        app.tweetServiceAvailable = false;
        serviceUnavailableMessage();
    }

    void serviceUnavailableMessage()
    {
        Toast toast = Toast.makeText(this, "Tweet Service Unavailable. Try again later", Toast.LENGTH_LONG);
        toast.show();
    }

    void serviceAvailableMessage()
    {
        Toast toast = Toast.makeText(this, "Tweet Contacted Successfully", Toast.LENGTH_LONG);
        toast.show();
    }
}
