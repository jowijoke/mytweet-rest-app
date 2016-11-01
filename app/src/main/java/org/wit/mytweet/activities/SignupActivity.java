package org.wit.mytweet.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.wit.mytweet.R;
import org.wit.mytweet.main.MyTweetApp;
import org.wit.mytweet.models.User;

import static org.wit.android.helpers.LogHelpers.info;
import static org.wit.mytweet.R.id.Email;
import static org.wit.mytweet.R.id.firstName;
import static org.wit.mytweet.R.id.lastName;


/**
 * Created by User on 02/10/2016.
 */

public class SignupActivity extends AppCompatActivity {

    MyTweetApp app;
    User user;

    private Button bsignup;
    private EditText et_firstname, et_lastname, et_email, et_password, et_cpassword;
    private String firstname, lastname, email, password, cpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        info(this, "SignupActivity page opened");

        et_firstname = (EditText) findViewById(firstName);
        et_lastname = (EditText) findViewById(lastName);
        et_email = (EditText) findViewById(Email);
        et_password = (EditText) findViewById(R.id.Password);
        et_cpassword = (EditText) findViewById(R.id.ConfirmPassword);

        bsignup = (Button) findViewById(R.id.signup);
        bsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

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
        user = new User();

        app.dbHelper.addUser(user);
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
            et_firstname.setError("Please enter a Password");
            valid = false;
        }
        if (cpassword.isEmpty()) {
            et_lastname.setError("Please enter a Password ");
            valid = false;
        }
        if (!password.equals(cpassword)) {
            Toast.makeText(this, "your password did not match", Toast.LENGTH_SHORT).show();
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
}
