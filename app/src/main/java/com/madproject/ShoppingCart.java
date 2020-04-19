package com.madproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class ShoppingCart extends AppCompatActivity {

    public void addFunction(View view){
        Log.i("Info","add to cart");
        Intent intent = new Intent(ShoppingCart.this ,MyCart.class);
        startActivity(intent);
    }

    public void commentFunction(View view){
        Log.i("Info","Add comment");

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
    }
}
