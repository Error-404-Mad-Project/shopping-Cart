package com.shoppingcart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FAQ extends AppCompatActivity {
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f_a_q);

        if(getActionBar() != null) {
            getSupportActionBar().setTitle("FAQ");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        backButton = (Button) findViewById(R.id.button111);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FAQ.this, Payment.class);
                startActivity(intent);
            }
        });
    }
}
