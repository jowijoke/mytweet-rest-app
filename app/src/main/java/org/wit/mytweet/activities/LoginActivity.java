package org.wit.mytweet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.wit.android.helpers.IntentHelper;
import org.wit.android.helpers.LogHelpers;
import org.wit.mytweet.R;
import org.wit.mytweet.main.MyTweetApp;
import org.wit.mytweet.main.RetrofitServiceFactory;
import org.wit.mytweet.main.TweetService;
import org.wit.mytweet.main.TweetServiceOpen;
import org.wit.mytweet.models.Token;
import org.wit.mytweet.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.wit.android.helpers.LogHelpers.info;

/**
 * Created by User on 02/10/2016.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button login;
    private EditText etEmail, etPass;
    private String email, password;

    MyTweetApp app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        app = MyTweetApp.getApp();

        login = (Button) findViewById(R.id.loginButton);
        etEmail = (EditText) findViewById(R.id.loginEmail);
        etPass = (EditText) findViewById(R.id.loginPassword);

        login.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginButton:
                login();
                break;
        }
    }


    private void login() {
        initialise();
        if (!validate()) {
            Toast.makeText(this, "Signup has failed", Toast.LENGTH_SHORT).show();
        } else {
            onLoginSuccess();
        }
    }

    public boolean validate() {
        boolean valid = true;
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Please Enter Valid Email Address");
            valid = false;
        }
        if (password.isEmpty()) {
            etPass.setError("Please enter a Password");
            valid = false;
        }
        return valid;
    }


    public void initialise() {
        email = etEmail.getText().toString().trim();
        password = etPass.getText().toString().trim();

    }

    public void onLoginSuccess() {
        MyTweetApp app = (MyTweetApp) getApplication();
        if(!app.validUser(email, password)){
            Toast.makeText(getApplicationContext(), "Wrong email/password", Toast.LENGTH_SHORT).show();

        }else {
            IntentHelper.startActivity(this, TweetListActivity.class);

        }
    }
}
