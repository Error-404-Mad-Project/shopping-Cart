package com.shoppingcart;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;


import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import io.paperdb.Paper;

public class CustomerView extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    //Paper.init(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_customer_view2);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(RegistrationForm.EXTRA_MESSAGE);
        String message1 = intent.getStringExtra(RegistrationForm.EXTRA_MESSAGE1);
        String message2 = intent.getStringExtra(RegistrationForm.EXTRA_MESSAGE2);
        String message3 = intent.getStringExtra(RegistrationForm.EXTRA_MESSAGE3);

        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.textView17);
        TextView textView1= findViewById(R.id.textView18);
        TextView textView2= findViewById(R.id.textView19);
        TextView textView3= findViewById(R.id.textView20);

        textView.setText(message);
        textView1.setText(message1);
        textView2.setText(message2);
        textView3.setText(message3);



        Toolbar toolbar = findViewById (R.id.toolbar);
        toolbar.setTitle ("CustomerView");
        setSupportActionBar (toolbar);

        FloatingActionButton fab = findViewById (R.id.fab);
        fab.setOnClickListener (new View.OnClickListener () {

            @Override
            public void onClick(View view) {
                Snackbar.make (view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction ("Action", null).show ();
                // Get the Intent that started this activity and extract the string
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById (R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle (this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener (toggle);
        toggle.syncState ();

        NavigationView navigationView = (NavigationView) findViewById (R.id.nav_view);
        navigationView.setNavigationItemSelectedListener (this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater ().inflate (R.menu.customer_view, menu);
        return true;
    }

    @Override
    public  boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId ();

        //noinspection SimplifiableIfStatement
//        if (id ==R.id.action_HomeFragment_to_HomeSecondFragment) {
//            return true;
//        }
        return super.onOptionsItemSelected (item);
    }

    @SuppressWarnings ("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId ();

        if (id == R.id.nav_home)
        {

        }
        else if (id == R.id.nav_gallery)
        {

        }
        else if (id == R.id.nav_slideshow)
        {

        }
        else if (id == R.id.search_e)
        {

        }
        else if (id == R.id.logout)
        {
            Paper.book ().destroy ();

            Intent intent = new Intent (CustomerView.this,Login.class);
            intent.addFlags (Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity (intent);
            finish ();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById (R.id.drawer_layout);
        drawer.closeDrawer (GravityCompat.START);
        return true;
    }
}
