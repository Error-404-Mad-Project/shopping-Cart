package com.e.androidproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.e.androidproject.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseListOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    public void nextFunction(View view)
    {
            Intent intent = new Intent(MainActivity.this,Home2Activity.class);
            startActivity(intent);
    }
   // Write a message to the database
   public void addproductFunction(View view)
   {
            Intent intent = new Intent(MainActivity.this, AdminCategoryActivity.class);
            startActivity(intent);
   }
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


}
