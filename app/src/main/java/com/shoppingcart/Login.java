package com.shoppingcart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Login extends AppCompatActivity {
    Button nextpage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        nextpage = (Button) findViewById(R.id.button3);
        nextpage.setOnClickListener (new View.OnClickListener () { /** Called when the user taps the Register button */
        @Override
        public void onClick(View v) {
            // Do something in response to button
            Intent intent = new Intent (Login.this,RegistrationForm.class);
            startActivity (intent);
        }
        });



    }
}
