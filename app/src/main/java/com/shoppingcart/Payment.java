package com.shoppingcart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.*;

public class Payment extends AppCompatActivity {
    private Button button;
    private Button backButton;
    private TextView textView;


    private RadioGroup RadioGroupPhoto15;
    private ImageView imageViewphoto1;
    private Integer []photos = { R.drawable.master, R.drawable.visa, R.drawable.cod};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_payment);

        this.imageViewphoto1 = (ImageView) findViewById(R.id.imageViewphoto1);
        this.RadioGroupPhoto15 = (RadioGroup) findViewById(R.id.RadioGroupPhoto15);
        this.RadioGroupPhoto15.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton =(RadioButton) RadioGroupPhoto15.findViewById(checkedId);
                int index = RadioGroupPhoto15.indexOfChild(radioButton);
                imageViewphoto1.setImageResource(photos[index]);
            }
        });

        if(getActionBar() != null) {
            getSupportActionBar().setTitle("Payment");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        button = (Button) findViewById(R.id.button33);
        backButton = (Button) findViewById(R.id.button11);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Payment.this, ThankYou.class);
                startActivity(intent);
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Payment.this,  Instructions.class);
                startActivity(intent);
            }
        });
        textView=(TextView)findViewById(R.id.textView5);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Payment.this,FAQ.class);
                startActivity(intent);
                Toast.makeText(Payment.this,"Terms & Conditions",Toast.LENGTH_LONG).show();

            }
        });


    }
}
