package org.wit.mytweet.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.wit.android.helpers.IntentHelper;
import org.wit.mytweet.R;
import org.wit.mytweet.sqlite.DbHelper;

/**
 * Created by User on 02/10/2016.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button login;
    private EditText etEmail, etPass;
    private String email, pass;
    private DbHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DbHelper(this);
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
        if (pass.isEmpty()) {
            etPass.setError("Please enter a Password");
            valid = false;
        }
        return valid;
    }


    public void initialise() {
        email = etEmail.getText().toString();
        pass = etPass.getText().toString();

    }

    public void onLoginSuccess() {
        if (db.getUser(email, pass)) {

            IntentHelper.startActivity(this, TweetListActivity.class);

        } else {
            Toast.makeText(getApplicationContext(), "Wrong email/password", Toast.LENGTH_SHORT).show();
        }

    }



}
