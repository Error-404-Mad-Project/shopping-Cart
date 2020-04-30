package com.shoppingcart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Terms extends AppCompatActivity {
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);

        if(getActionBar() != null) {
            getSupportActionBar().setTitle("Terms & Conditions");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        backButton = (Button) findViewById(R.id.button12);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Terms.this, Instructions.class);
                startActivity(intent);
            }
        });
    }
}