package com.shoppingcart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Terms extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);

        if(getActionBar() != null) {
            getSupportActionBar().setTitle("Terms & Conditions");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}