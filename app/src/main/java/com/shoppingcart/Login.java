package com.shoppingcart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    Button nextPage;
    Button nextActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        nextPage = (Button) findViewById(R.id.button3);
        nextActivity = (Button) findViewById (R.id.button4);

        nextPage.setOnClickListener (new View.OnClickListener () { /** Called when the user taps the Register button */
        @Override
        public void onClick(View v) {
            // Do something in response to button
            Intent intent = new Intent (Login.this,RegistrationForm.class);
            startActivity (intent);

            nextActivity.setOnClickListener (new View.OnClickListener () { /** Called when the user taps the Login button */
                @Override
                public void onClick(View v) {
                    // Do something in response to button
                    Intent intent = new Intent (Login.this,CustomerView.class);
                    startActivity (intent);

                    Toast.makeText (Login.this,"Logged in Successfully",Toast.LENGTH_SHORT).show ();

                }
            });
        }


        });




    }
}
