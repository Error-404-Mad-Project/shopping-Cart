package com.e.androidproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.e.androidproject.Model.Users;
import com.e.androidproject.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import io.paperdb.Paper;




public class Login extends AppCompatActivity {

    private EditText mTextUsername, mTextPassword;
    private TextView mTextForgotPassword, AdminLink, NotAdminLink;
    private Button mButtonRegister, mButtonLogin;
    private ProgressDialog loadingBar;

    private String parentDbName = "Users";
    private CheckBox chkBoxRememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_login);

        mTextUsername = (EditText) findViewById (R.id.editText);
        mTextPassword = (EditText) findViewById (R.id.editText3);
        mTextForgotPassword = (TextView) findViewById (R.id.textView23);
        AdminLink = (TextView) findViewById (R.id.admin_panel_link);
        NotAdminLink = (TextView) findViewById (R.id.not_admin_panel_link);
        mButtonRegister = (Button) findViewById (R.id.button3);
        mButtonLogin = (Button) findViewById (R.id.button4);

        loadingBar = new ProgressDialog (this);

//        chkBoxRememberMe = (CheckBox) findViewById (R.id.remember_me_chkb);
//        Paper.init (this);

        mButtonRegister.setOnClickListener (new View.OnClickListener () {
            /**
             * Called when the user taps the Register button
             */
            @Override
            public void onClick(View v) {
                // Do something in response to button
                Intent registerIntent = new Intent (Login.this, RegistrationForm.class);
                startActivity (registerIntent);
            }
        });

        mTextForgotPassword.setOnClickListener (new View.OnClickListener () {
            /**
             * Called when the user taps the Forgot Password button
             */
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (Login.this, ForgotPassword.class);
                startActivity (intent);
            }
        });


        mButtonLogin.setOnClickListener (new View.OnClickListener () {
            /**
             * Called when the user taps the Login button
             */
            @Override
            public void onClick(View v) {
                LoginUser ();
            }
        });

        AdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                mButtonLogin.setText("Login Admin");
                AdminLink.setVisibility(View.INVISIBLE);
                NotAdminLink.setVisibility(View.VISIBLE);
                parentDbName = "Admins";
            }
        });

        NotAdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                mButtonLogin.setText("Login");
                AdminLink.setVisibility(View.VISIBLE);
                NotAdminLink.setVisibility(View.INVISIBLE);
                parentDbName = "Users";
            }
        });

    }

    private void LoginUser()
    {
        String name = mTextUsername.getText ().toString ();
        String password = mTextPassword.getText ().toString ();

        if (TextUtils.isEmpty (name))
        {
            Toast.makeText (Login.this,"Please enter your name here...",Toast.LENGTH_SHORT).show ();
        }
        else if (TextUtils.isEmpty (password))
        {
            Toast.makeText (Login.this,"Please enter your password here...",Toast.LENGTH_SHORT).show ();
        }
        else
        {
            /**Create Loading Bar**/
            loadingBar.setTitle ("Login Account.Please wait");
            loadingBar.setMessage ("Please wait, while we are checking the credentials");
            loadingBar.setCanceledOnTouchOutside (false);
            loadingBar.show ();

            AllowAccessToAccount (name, password);
        }

    }

    private void AllowAccessToAccount(final String name, final String password)
    {
//                if (chkBoxRememberMe.isChecked ())
//                {
//                    Paper.book ().write (Prevalent.UserNameKey,name);
//                    Paper.book ().write (Prevalent.UserPasswordKey,password);
//                }

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance ().getReference ();

        RootRef.addListenerForSingleValueEvent (new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child (parentDbName).child (name).exists ())
                {
                    Users usersData = dataSnapshot.child (parentDbName).child (name).getValue (Users.class);
                    if (usersData.getName ().equals (name))
                    {
                        if (usersData.getPassword ().equals (password))
                        {
                            if (parentDbName.equals("Admins"))
                            {
                                Toast.makeText(Login.this, "Welcome Admin, you are logged in Successfully...", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(Login.this, AdminCategoryActivity.class);
                                startActivity(intent);
                            }
                            else if (parentDbName.equals("Users"))
                            {
                                Toast.makeText(Login.this, "logged in Successfully...", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(Login.this, Home2Activity.class);
                                Prevalent.currentOnlineUser = usersData;
                                startActivity(intent);
                            }


//                                    Toast.makeText (Login.this,"Logged in Successfully.",Toast.LENGTH_SHORT).show ();
//                                    loadingBar.dismiss ();
//
//                                    Intent intent = new Intent (Login.this,HomeActivity.class);
//                                    intent.putExtra ("UserName", usersData.getName ());
//                                    intent.putExtra ("Email", usersData.getEmail ());
//                                    intent.putExtra ("Address", usersData.getAddress ());
//                                    intent.putExtra ("Phone", usersData.getPhone ());
//                                    Prevalent.currentOnlineUser = usersData;
//                                    startActivity (intent);
                        }
                        else
                        {
                            loadingBar.dismiss ();
                            Toast.makeText (Login.this,"Password is incorrect.",Toast.LENGTH_SHORT).show ();
                        }
                    }
                }
                else
                {
                    Toast.makeText (Login.this,"Account with this " + name + " user name do not exists. ",Toast.LENGTH_SHORT).show ();
                    loadingBar.dismiss ();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



}