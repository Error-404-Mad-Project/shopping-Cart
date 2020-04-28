package com.shoppingcart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AdminCategoryActivity extends AppCompatActivity {


    private ImageView Vegetables, Fruits ;
    private ImageView MilkProduct, DrinkProduct;
    private ImageView FoodProduct, HouseholdProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_admin_category);

        Vegetables = (ImageView) findViewById(R.id.vegetables);
        Fruits = (ImageView) findViewById(R.id.fruits);

        MilkProduct = (ImageView) findViewById(R.id.milk_products);
        DrinkProduct = (ImageView) findViewById(R.id.drink_items);

        FoodProduct = (ImageView) findViewById(R.id.food_item);
        HouseholdProduct = (ImageView) findViewById(R.id.household);

        Vegetables.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category","Vegetables");
                startActivity(intent);
            }
        });

        Fruits.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "Fruits");
                startActivity(intent);

            }
        });

        MilkProduct.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "Milk Products");
                startActivity(intent);

            }
        });

        DrinkProduct.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "Drink Products");
                startActivity(intent);

            }
        });

        FoodProduct.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "Food Products");
                startActivity(intent);

            }
        });

        HouseholdProduct.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "Household Products");
                startActivity(intent);

            }
        });


    }
}
