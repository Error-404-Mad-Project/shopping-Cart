package com.shoppingcart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//import com.shoppingcart.Prevalent.Prevalent;

public class Login extends AppCompatActivity {
    EditText mTextUsername;
    EditText mTextPassword;
    Button mButtonRegister;
    Button mButtonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mTextUsername = (EditText) findViewById (R.id.editText);
        mTextPassword = (EditText) findViewById (R.id.editText3);
        mButtonRegister = (Button) findViewById(R.id.button3);
        mButtonLogin = (Button) findViewById (R.id.button4);

        mButtonRegister.setOnClickListener (new View.OnClickListener () { /** Called when the user taps the Register button */
        @Override
        public void onClick(View v) {
            // Do something in response to button
            Intent intent = new Intent (Login.this, RegistrationForm.class);
            startActivity (intent);
        }
        });

        mButtonLogin.setOnClickListener (new View.OnClickListener () { /** Called when the user taps the Login button */
        @Override
        public void onClick(View v) {
            // Do something in response to button
            Intent intent = new Intent (Login.this,CustomerView.class);
//          Prevalent.currentOnlineUser = usersData;
            startActivity (intent);

            Toast.makeText (Login.this,"Logged in Successfully",Toast.LENGTH_SHORT).show ();

                }
            });








    }
}
