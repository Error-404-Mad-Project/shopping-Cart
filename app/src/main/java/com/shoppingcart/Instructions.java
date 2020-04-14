package com.shoppingcart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Instructions extends AppCompatActivity {
    private Button button;
    private Button cancelButton;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);

//        getSupportActionBar().setTitle("Instructions");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        button = (Button) findViewById(R.id.button3);
        cancelButton = (Button) findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Instructions.this, ThankYou.class);
                startActivity(intent);
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Instructions.this, DeliveryActivityMain.class);
                startActivity(intent);
            }
        });

        textView=(TextView)findViewById(R.id.textView14);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Instructions.this,Terms.class);
                startActivity(intent);
                Toast.makeText(Instructions.this,"Terms & Conditions",Toast.LENGTH_LONG).show();

            }
        });
    }
}
