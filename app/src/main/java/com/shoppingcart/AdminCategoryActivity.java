package com.shoppingcart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AdminCategoryActivity extends AppCompatActivity {


    private ImageView vegetables, fruits ;
    private ImageView milkProduct, drinkProduct;
    private ImageView foodProduct, householdProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_admin_category);

        vegetables = (ImageView) findViewById(R.id.vegetables);
        fruits = (ImageView) findViewById(R.id.fruits);

        milkProduct = (ImageView) findViewById(R.id.milk_products);
        drinkProduct = (ImageView) findViewById(R.id.drink_items);

        foodProduct = (ImageView) findViewById(R.id.food_item);
        householdProduct = (ImageView) findViewById(R.id.household);

        vegetables.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category","vegetables");
                startActivity(intent);
            }
        });

        fruits.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "fruits");
                startActivity(intent);

            }
        });

        milkProduct.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "milkProduct");
                startActivity(intent);

            }
        });

        drinkProduct.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "drinkProduct");
                startActivity(intent);

            }
        });

        foodProduct.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "foodProduct");
                startActivity(intent);

            }
        });

        householdProduct.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "householdProduct");
                startActivity(intent);

            }
        });


    }
}
