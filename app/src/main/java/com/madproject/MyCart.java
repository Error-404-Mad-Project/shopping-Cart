package com.madproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MyCart extends AppCompatActivity {

    public void buyFunction(View view){
        Log.i("Info","buy item in cart");
        Intent intent = new Intent(MyCart.this ,PlaceOrder.class);
        startActivity(intent);
    }

    public void deleteFunction(View view){
        Log.i("Info","delete item in cart");
        Intent intent = new Intent(MyCart.this ,MyCart.class);
        startActivity(intent);
    }

    public void placeorderFunction(View view){
        Log.i("Info","view place order page");
        Intent intent = new Intent(MyCart.this ,PlaceOrder.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);
    }
}
