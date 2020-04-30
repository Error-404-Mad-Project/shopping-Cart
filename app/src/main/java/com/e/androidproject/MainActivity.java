package com.e.androidproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.e.androidproject.Model.Users;
import com.e.androidproject.Prevalent.Prevalent;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 4000;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Paper.init (this);
        loadingBar = new ProgressDialog (this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(MainActivity.this, Login.class);
                startActivity(homeIntent);
                finish();
            }
        },SPLASH_TIME_OUT);

//        String UserNameKey = Paper.book ().read (Prevalent.UserNameKey);
//        String UserPasswordKey = Paper.book ().read (Prevalent.UserPasswordKey);
//
//        if (UserNameKey != "" && UserPasswordKey != "")
//        {
//            if (!TextUtils.isEmpty (UserNameKey)  &&  !TextUtils.isEmpty (UserPasswordKey))
//            {
//                AllowAccess (UserNameKey, UserPasswordKey);
//
//                /**Create Loading Bar**/
//                loadingBar.setTitle ("Already Logged in");
//                loadingBar.setMessage ("Please wait...");
//                loadingBar.setCanceledOnTouchOutside (false);
//                loadingBar.show ();
//            }
//        }
//    }
//
//    private void AllowAccess(final String name, final String password)
//    {
//        final DatabaseReference RootRef;
//        RootRef = FirebaseDatabase.getInstance ().getReference ();
//
//        RootRef.addListenerForSingleValueEvent (new ValueEventListener () {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.child ("Users").child (name).exists ())
//                {
//                    Users usersData = dataSnapshot.child ("Users").child (name).getValue (Users.class);
//                    if (usersData.getName ().equals (name))
//                    {
//                        if (usersData.getPassword ().equals (password))
//                        {
//                            Toast.makeText (MainActivity.this,"Please wait, you are already logged in.",Toast.LENGTH_SHORT).show ();
//                            loadingBar.dismiss ();
//
//                            Intent intent = new Intent (MainActivity.this,CustomerView.class);
//                            startActivity (intent);
//                        }
//                        else
//                        {
//                            loadingBar.dismiss ();
//                            Toast.makeText (MainActivity.this,"Password is incorrect.",Toast.LENGTH_SHORT).show ();
//                        }
//                    }
//                }
//                else
//                {
//                    Toast.makeText (MainActivity.this,"Account with this " + name + " user name do not exists. ",Toast.LENGTH_SHORT).show ();
//                    loadingBar.dismiss ();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
    }
}
