package org.wit.mytweet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.wit.mytweet.R;

import static org.wit.android.helpers.LogHelpers.info;


public class Welcome extends AppCompatActivity implements View.OnClickListener {
    public Button login;
    public Button signup;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.app_name);
        setContentView(R.layout.welcome_activity);

        login = (Button) findViewById(R.id.welcomeLogin);
        signup = (Button) findViewById(R.id.welcomeSignup);

        login.setOnClickListener(this);
        signup.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.welcomeLogin:
                Toast.makeText(this, "Login pressed", Toast.LENGTH_SHORT).show();
                info(this, "Login Pressed");
                startActivity(new Intent(this, Login.class));
                break;


            case R.id.welcomeSignup:
                startActivity(new Intent(this, Signup.class));
                Toast.makeText(this, "Signup pressed", Toast.LENGTH_SHORT).show();
                info(this, "Signup Pressed");
                break;

        }
    }
}
