package com.shoppingcart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.*;

public class Instructions extends AppCompatActivity {

    private RadioGroup radioGroupPhoto;
    private ImageView imageViewphoto;
    private Integer []photos = { R.drawable.bike, R.drawable.weller, R.drawable.van};

    private Button button;
    private Button cancelButton;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);

        this.imageViewphoto = (ImageView) findViewById(R.id.imageViewphoto);
        this.radioGroupPhoto = (RadioGroup) findViewById(R.id.RadioGroupPhoto);
        this.radioGroupPhoto.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton =(RadioButton) radioGroupPhoto.findViewById(checkedId);
                int index = radioGroupPhoto.indexOfChild(radioButton);
                imageViewphoto.setImageResource(photos[index]);
            }
        });

        if(getActionBar() != null)
        {
            getActionBar().setTitle("Instructions");
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }


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
