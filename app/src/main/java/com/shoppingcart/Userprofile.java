package com.shoppingcart;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class Userprofile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);

       // mAuth = FirebaseAuth.getInstance();

        //Userref = FirebaseDatabase.getInstance().getRefrence().child('User"');
    }
}
