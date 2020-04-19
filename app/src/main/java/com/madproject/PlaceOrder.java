package com.madproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class PlaceOrder extends AppCompatActivity {


    public void confirmFunction(View view){
        Log.i("Info","order confirm");
       // Intent intent = new Intent(PlaceOrder.this ,.class);
       // startActivity(intent);
    }
    public void backFunction(View view){
        Log.i("Info","go to previous page");
        Intent intent = new Intent(PlaceOrder.this ,MyCart.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);
    }
}
