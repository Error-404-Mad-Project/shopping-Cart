package com.shoppingcart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ThankYou extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_thankyou);

        if(getActionBar() != null) {
            getSupportActionBar().setTitle("Thank you");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


    }
}
