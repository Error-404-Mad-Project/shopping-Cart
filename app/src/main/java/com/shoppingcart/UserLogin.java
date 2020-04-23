package com.shoppingcart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.sax.RootElement;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shoppingcart.models.Users;

public class UserLogin extends AppCompatActivity {

    private Button loginButton;
    private EditText InputName;
    private ProgressDialog loadingBar;

    private TextView AdminLink,NotAdminLink;

    private String parentDbName = "Users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        loginButton = (Button) findViewById(R.id.login);
        InputName =(EditText)findViewById(R.id.uName);
        AdminLink = (TextView) findViewById(R.id.admin_panal_link);
        NotAdminLink =(TextView)findViewById(R.id.not_admin_panal_link);

        loadingBar =new ProgressDialog(this);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               LoginUser();
            }
        });

        AdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.setText("Login Admin");
                AdminLink.setVisibility(View.INVISIBLE);
                NotAdminLink.setVisibility(View.VISIBLE);
                parentDbName ="Admin";
            }
        });

        NotAdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.setText("Login");
                AdminLink.setVisibility(View.VISIBLE);
                NotAdminLink.setVisibility(View.INVISIBLE);
                parentDbName ="Users";
            }
        });

    }

    private void LoginUser() {
        String name = InputName.getText().toString();

        if(TextUtils.isEmpty(name)){
            Toast.makeText(this,"Please write your name...",Toast.LENGTH_SHORT).show();

        }

        else {
            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please wait,while we are checking the credentials.. ");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            AllowAccessToAccount(name);
        }

    }

    private void AllowAccessToAccount(final String name) {
        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();


        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(parentDbName).child(name).exists()){

                    Users usersData = dataSnapshot.child(parentDbName).child(name).getValue(Users.class);

                    if(usersData.getName().equals(name))
                    {
                       /*
                        // normaly login.not separated  admin or user
                        Toast.makeText(UserLogin.this,"Login successs full..",Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();

                        Intent intent = new Intent(UserLogin.this,HomeActivity.class);
                        startActivity(intent);*/

                       //then do separeate admin or user
                        if(parentDbName.equals("Admins ")){
                            Toast.makeText(UserLogin.this,"Login successs full..",Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();

                            Intent intent = new Intent(UserLogin.this,HomeActivity.class);
                            startActivity(intent);
                        }
                        else if(parentDbName.equals("Users")) {
                            Toast.makeText(UserLogin.this,"Login successs full..",Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();

                            Intent intent = new Intent(UserLogin.this,HomeActivity.class);
                            startActivity(intent);
                        }

                    }


                }
                else{
                    Toast.makeText(UserLogin.this,"Account with this"+ name + "name do not exixts",Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(UserLogin.this,"You need creat new account.... ",Toast.LENGTH_SHORT).show();



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
