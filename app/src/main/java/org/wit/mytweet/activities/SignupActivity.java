package org.wit.mytweet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.wit.android.helpers.IntentHelper;
import org.wit.mytweet.R;
import org.wit.mytweet.main.MyTweetApp;
import org.wit.mytweet.models.User;
import org.wit.mytweet.sqlite.DbHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.wit.android.helpers.LogHelpers.info;
import static org.wit.mytweet.R.id.Email;
import static org.wit.mytweet.R.id.firstName;
import static org.wit.mytweet.R.id.lastName;


/**
 * Created by User on 02/10/2016.
 */

public class SignupActivity extends AppCompatActivity implements Callback<User>, View.OnClickListener {
    private Button bsignup;
    private EditText et_firstname, et_lastname, et_email, et_password, et_cpassword;
    private String firstname, lastname, email, password, cpassword;

    MyTweetApp app;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        info(this, "SignupActivity page opened");


        app = MyTweetApp.getApp();


        et_firstname = (EditText) findViewById(firstName);
        et_lastname = (EditText) findViewById(lastName);
        et_email = (EditText) findViewById(Email);
        et_password = (EditText) findViewById(R.id.Password);
        et_cpassword = (EditText) findViewById(R.id.ConfirmPassword);

        bsignup = (Button) findViewById(R.id.signup);
        bsignup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        register();
    }

    public void register() {
        initialise();
        if (!validate()) {
            Toast.makeText(this, "Signup has failed", Toast.LENGTH_SHORT).show();
        } else {
            onSignupSuccess();
        }
    }

    public void onSignupSuccess() {
        User user = new User(firstname, lastname, email, password);
        Toast.makeText(this, "User registered", Toast.LENGTH_SHORT).show();


        MyTweetApp app = (MyTweetApp) getApplication();
        Call<User> call = app.tweetService.createUser(user);
        call.enqueue(this);
    }

    public boolean validate() {
        boolean valid = true;
        if (firstname.isEmpty()) {
            et_firstname.setError("Please enter your first name");
            valid = false;
        }
        if (lastname.isEmpty()) {
            et_lastname.setError("Please enter your surname");
            valid = false;
        }
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            et_email.setError("Please Enter Valid Email Address");
            valid = false;
        }
        if (password.isEmpty()) {
            et_password.setError("Please enter a Password");
            valid = false;
        }
        if (cpassword.isEmpty()) {
            et_cpassword.setError("Please enter a Password ");
            valid = false;
        }
        if (!password.equals(cpassword)) {
            Toast.makeText(this, "your password did not match", Toast.LENGTH_SHORT).show();
            valid = false;
        }

        return valid;
    }

    public void initialise() {
        firstname = et_firstname.getText().toString().trim();
        lastname = et_lastname.getText().toString().trim();
        email = et_email.getText().toString().trim();
        password = et_password.getText().toString().trim();
        cpassword = et_cpassword.getText().toString().trim(); // trim used to remove any whitespace at the end if present
    }


    @Override
    public void onResponse(Call<User> call, Response<User> response) {
        app.users.add(response.body());
        IntentHelper.startActivity(this, LoginActivity.class);
    }

    @Override
    public void onFailure(Call<User> call, Throwable t) {
        app.tweetServiceAvailable = false;
        Toast toast = Toast.makeText(this, "MyTweet Service Unavailable. Try again later", Toast.LENGTH_LONG);
        toast.show();
        startActivity (new Intent(this, WelcomeActivity.class));
    }
}
