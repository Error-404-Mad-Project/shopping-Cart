package com.shoppingcart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Register2 extends AppCompatActivity {
    private Button registerButton;
    private EditText InputName,InputEmail;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);


        registerButton = (Button) findViewById(R.id.register);
        InputName =(EditText)findViewById(R.id.uName);
        InputEmail =(EditText)findViewById(R.id.uEmail);

        loadingBar =new ProgressDialog(this);

        registerButton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });
    }

    private void createAccount() {
        String name = InputName.getText().toString();
        String email = InputEmail.getText().toString();

        if(TextUtils.isEmpty(name)){
            Toast.makeText(this,"Please write your name...",Toast.LENGTH_SHORT).show();

        }
        else if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please write your email...",Toast.LENGTH_SHORT).show();

        }

        else {
            loadingBar.setTitle("Create Account");
            loadingBar.setMessage("Please wait,while we are checking the credentials.. ");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();


            ValidateEnterDataToDataBase(name,email);

        }
}

    private void ValidateEnterDataToDataBase(final String name, final String email) {
        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(!(dataSnapshot.child("Admin").child(name).exists())) {
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    {
                        userdataMap.put("name", name);
                        userdataMap.put("email", email);

                        RootRef.child("Admin").child(name).updateChildren(userdataMap)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(Register2.this, "Your account hasbeen created..", Toast.LENGTH_SHORT).show();
                                            loadingBar.dismiss();

                                            Intent intent = new Intent(Register2.this, UserLogin.class);
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(Register2.this, "Network Error,Plase try again..", Toast.LENGTH_SHORT).show();
                                            loadingBar.dismiss();
                                        }
                                    }
                                });
                    }
                }

                                     else{
                                        Toast.makeText(Register2.this,"This" + name + "already use.",Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();
                                        Toast.makeText(Register2.this, "Please try again using another name..",Toast.LENGTH_SHORT).show();


                                        Intent intent = new Intent(Register2.this,MainPage2.class);
                                        startActivity(intent);

                                    }
                                }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {


                                    }
                                });

                    }
                }