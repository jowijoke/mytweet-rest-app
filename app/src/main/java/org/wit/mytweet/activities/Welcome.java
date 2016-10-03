package org.wit.mytweet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import org.wit.mytweet.R;


public class Welcome extends AppCompatActivity implements View.OnClickListener {
    public Button Login;
    public Button Signup;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.app_name);
        setContentView(R.layout.welcome_activity);

        Login = (Button) findViewById(R.id.welcomeLogin);
        Signup = (Button) findViewById(R.id.welcomeSignup);

        Login.setOnClickListener(this);
        Signup.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.welcomeLogin:
                Intent Login = (new Intent(this, Login.class));
                startActivity(Login);
                break;


            case R.id.welcomeSignup:
                Intent Signup = (new Intent(this, Signup.class));
                startActivity(Signup);
                break;

        }
    }
}
