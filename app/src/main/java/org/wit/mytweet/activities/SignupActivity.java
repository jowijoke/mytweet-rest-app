package org.wit.mytweet.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.wit.mytweet.R;

import static org.wit.android.helpers.LogHelpers.info;

/**
 * Created by User on 02/10/2016.
 */

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        info(this, "SignupActivity page opened");

        final Button register = (Button) findViewById(R.id.register);
        final EditText firstname = (EditText) findViewById(R.id.firstName);
        final EditText lastname = (EditText) findViewById(R.id.lastName);
        final EditText email = (EditText) findViewById(R.id.Email);
        final EditText password = (EditText) findViewById(R.id.Password);
    }

    @Override
    public void onClick(View v) {

    }
}
