package org.wit.mytweet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.wit.mytweet.R;

public class Welcome extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity);
    }

    public void loginPressed(View view) {
        startActivity(new Intent(this, Login.class));
    }

    public void signUpPressed(View view) {
        startActivity(new Intent(this, Signup.class));
    }
}
