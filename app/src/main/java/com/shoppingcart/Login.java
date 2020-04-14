package com.shoppingcart;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.shoppingcart.Database.DatabaseHelper;

//import com.shoppingcart.Prevalent.Prevalent;

public class Login extends AppCompatActivity {

    EditText mTextUsername;
    EditText mTextPassword;
    Button mButtonRegister;
    Button mButtonLogin;
    DatabaseHelper database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        database = new DatabaseHelper (this);
        mTextUsername = (EditText) findViewById (R.id.editText);
        mTextPassword = (EditText) findViewById (R.id.editText3);
        mButtonRegister = (Button) findViewById(R.id.button3);
        mButtonLogin = (Button) findViewById (R.id.button4);

        mButtonRegister.setOnClickListener (new View.OnClickListener () { /** Called when the user taps the Register button */
        @Override
        public void onClick(View v) {
            // Do something in response to button
            Intent registerIntent = new Intent (Login.this, RegistrationForm.class);
            startActivity (registerIntent);
        }
        });

        mButtonLogin.setOnClickListener (new View.OnClickListener () { /** Called when the user taps the Login button */
        @Override
        public void onClick(View v) {

            String user = mTextUsername.getText ().toString ().trim ();
            String pwd = mTextPassword.getText ().toString ().trim ();
            Boolean res = database.checkUser (user,pwd);
            if(res == true)
            {
                // Do something in response to button
                Intent CustView = new Intent (Login.this,CustomerView.class);
//          Prevalent.currentOnlineUser = usersData;
                startActivity (CustView);

                Toast.makeText (Login.this,"Logged in Successfully",Toast.LENGTH_SHORT).show ();
            }
            else
            {
                Toast.makeText (Login.this,"Login Error",Toast.LENGTH_SHORT).show();
            }

                }
            });








    }
}
