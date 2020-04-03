package com.shoppingcart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class CustomerView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_customer_view);
    }
    /** mDrawerlayout = (DrawerLayout) findViewById (R.id.navigation);
     mToggle = new ActionBarDrawerToggle (this, mDrawerlayout, R.string.open, R.string.close);
     mDrawerlayout.addDrawerListener (mToggle);
     mToggle.syncState ();
     getSupportActionBar ().setDisplayHomeAsUpEnabled (true);



     @Override
     public boolean onOptionItemSelected(MenuItem item) {
     return super.onOptionsItemSelected (item);
     } **/
}
