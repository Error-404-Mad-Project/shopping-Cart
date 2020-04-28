package com.shoppingcart;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shoppingcart.Model.Users;
import com.shoppingcart.Prevalent.Prevalent;
import com.squareup.picasso.Picasso;
//import com.shoppingcart.Prevalent.Prevalent;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import io.paperdb.Paper;

public class CustomerView extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

        private ImageView userProfilePic;
        private TextView profileName, profileEmail, profileAddress, profilePhone;
        private FirebaseAuth firebaseAuth;
        private FirebaseDatabase firebaseDatabase;
        private String Name = "";
        private String Email = "";
        private String Address = "";
        private String Phone = "";
        private String checker = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_customer_view2);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null)
        {
            Name = getIntent().getExtras().get( "UserName" ).toString();
            Email = getIntent().getExtras().get( "Email" ).toString();
            Address = getIntent().getExtras().get( "Address" ).toString();
            Phone = getIntent().getExtras().get( "Phone" ).toString();
        }

        //Hooks
//        userProfilePic = findViewById (R.id.profileImage);
        profileName = findViewById (R.id.textView17);
        profileEmail = findViewById (R.id.textView18);
        profileAddress = findViewById (R.id.textView19);
        profilePhone = findViewById (R.id.textView20);
        firebaseAuth = FirebaseAuth.getInstance ();
        firebaseDatabase = FirebaseDatabase.getInstance ();

        showAllData();

        Toolbar toolbar = findViewById (R.id.toolbar);
        toolbar.setTitle ("CustomerView");
        setSupportActionBar (toolbar);

        FloatingActionButton fab = findViewById (R.id.fab);
        fab.setOnClickListener (new View.OnClickListener () {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent (CustomerView.this, UpdateCustomer.class);
                startActivity (intent);
//                Paper.book ().destroy ();
//
//                Snackbar.make (view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction ("Action", null).show ();
                // Get the Intent that started this activity and extract the string
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById (R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle (this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener (toggle);
        toggle.syncState ();

        NavigationView navigationView = (NavigationView) findViewById (R.id.nav_view);
        navigationView.setNavigationItemSelectedListener (this);

        View headerView = navigationView.getHeaderView (0);
        TextView userNameTextView = headerView.findViewById (R.id.user_profile_name);
        TextView userEmailTextView = headerView.findViewById (R.id.textView);
        ImageView ProfileImageView = headerView.findViewById (R.id.user_profile_image);

        userNameTextView.setText (Prevalent.currentOnlineUser.getName());
        userEmailTextView.setText (Prevalent.currentOnlineUser.getEmail ());
        Picasso.get ().load (Prevalent.currentOnlineUser.getImage()).placeholder (R.drawable.person).into (userProfilePic);

    }

    private void showAllData() {

        profileName.setText (Name);
        profileEmail.setText (Email);
        profileAddress.setText (Address);
        profilePhone.setText (Phone);

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
