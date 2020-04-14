package com.shoppingcart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class View extends AppCompatActivity {

    private static  Button button_write;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        onButtonClickListener();
    }

    public void onButtonClickListener(){
        button_write = (Button)findViewById(R.id.btn_write);
        button_write.setOnClickListener(
                new android.view.View.OnClickListener() {
                    @Override
                    public void onClick(android.view.View v) {
                        Intent intent = new Intent("android.intent.action.Comment");
                        startActivity(intent);
                    }
                });
    }
}


