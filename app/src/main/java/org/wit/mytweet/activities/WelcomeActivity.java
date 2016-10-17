package org.wit.mytweet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import org.wit.mytweet.R;

import static org.wit.android.helpers.LogHelpers.info;


public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.app_name);
        setContentView(R.layout.activity_welcome);

        final Button login = (Button) findViewById(R.id.welcomeLogin);
        final Button signup = (Button) findViewById(R.id.welcomeSignup);

        login.setOnClickListener(this);
        signup.setOnClickListener(this);
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
}
