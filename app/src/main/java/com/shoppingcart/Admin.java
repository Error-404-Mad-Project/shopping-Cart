package com.shoppingcart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class Admin extends AppCompatActivity {

    private String categoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);


    categoryName=getIntent().getExtras().get("category").toString();

     Toast.makeText(this,"category",Toast.LENGTH_SHORT).show();


    }
}
