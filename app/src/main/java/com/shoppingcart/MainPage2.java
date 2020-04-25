package com.shoppingcart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainPage2 extends AppCompatActivity {
    private Button joinButton,logButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page2);

        logButton = (Button) findViewById(R.id.log);
        joinButton = (Button) findViewById(R.id.join);

        logButton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Intent intent = new Intent(MainPage2.this,UserLogin2.class);
                startActivity(intent);
            }
        });

        joinButton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPage2.this,Register2.class);
                startActivity(intent);
            }
        });




    }
}
