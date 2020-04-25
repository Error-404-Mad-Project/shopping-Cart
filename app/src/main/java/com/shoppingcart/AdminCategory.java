package com.shoppingcart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class AdminCategory extends AppCompatActivity {

    private TextView catagary;
    private Button addPost;

    private ImageView vegitable,cleaning,animalProduct;
    private ImageView plastic,stationry,beauty;
    private ImageView mechanikal,grains,grosery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        catagary =(TextView) findViewById(R.id.category);

        addPost=(Button) findViewById(R.id.addPost);

        vegitable=(ImageView) findViewById(R.id.vegitable);
        cleaning=(ImageView) findViewById(R.id.cleaningItems);
        animalProduct=(ImageView) findViewById(R.id.animalProduct);
        plastic=(ImageView) findViewById(R.id.plastic);
        stationry=(ImageView) findViewById(R.id.stationary);
        beauty=(ImageView) findViewById(R.id.beauty);
        mechanikal=(ImageView) findViewById(R.id.mechanikal);
        grains=(ImageView) findViewById(R.id.grains);
        grosery=(ImageView) findViewById(R.id.grosery);

        addPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategory.this,AddPost.class);
                startActivity(intent);
            }
        });


        vegitable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategory.this,Admin.class);
                intent.putExtra("category","vegitable");
                startActivity(intent );
            }
        });

        grains.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategory.this, Admin.class);
                intent.putExtra("category", "grains");
                startActivity(intent);
            }
        });

        grosery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategory.this, Admin.class);
                intent.putExtra("category", "grosery");
                startActivity(intent);
            }
        });

        cleaning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategory.this, Admin.class);
                intent.putExtra("category", "cleaning");
                startActivity(intent);
            }
        });

        animalProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategory.this,Admin.class);
                intent.putExtra("category","animalProduct");
                startActivity(intent );
            }
        });

        plastic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategory.this,Admin.class);
                intent.putExtra("category","plastic");
                startActivity(intent );
            }
        });

        beauty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategory.this,Admin.class);
                intent.putExtra("category","beauty");
                startActivity(intent );
            }
        });
        stationry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategory.this,Admin.class);
                intent.putExtra("category","stationry");
                startActivity(intent );
            }
        });
        mechanikal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategory.this,Admin.class);
                intent.putExtra("category","mechanikal");
                startActivity(intent );
            }
        });







    }
}
