package com.shoppingcart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.jar.Attributes;


public class DeliveryActivityMain extends AppCompatActivity {
    private Button button;
    private EditText Name, Address, Telephone_Number, Email;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delevery_main);

        button = (Button) findViewById(R.id.button);
        Name = (EditText)findViewById(R.id.editText);
        Address = (EditText)findViewById(R.id.editText2);
        Telephone_Number = (EditText)findViewById(R.id.editText3);
        Email = (EditText)findViewById(R.id.editText4);
        loadingBar = new ProgressDialog(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateAccount();
            }
        });
    }

    private void CreateAccount() {
        String name = Name.getText().toString();
        String address = Address.getText().toString();
        String telephone_number = Telephone_Number.getText().toString();
        String email = Email.getText().toString();

        if (TextUtils.isEmpty(name))
            {
                    Toast.makeText(this,"please write your name", Toast.LENGTH_SHORT).show();
            }
       else if (TextUtils.isEmpty(address))
            {
                    Toast.makeText(this,"please write your address", Toast.LENGTH_SHORT).show();
            }
       else if (TextUtils.isEmpty(telephone_number))
            {
                    Toast.makeText(this,"please write your phone number", Toast.LENGTH_SHORT).show();
            }
       else if (TextUtils.isEmpty(email))
            {
                    Toast.makeText(this,"please write your email", Toast.LENGTH_SHORT).show();
            }
       else
            {
                    loadingBar.setTitle("Create Account");
                    loadingBar.setMessage("Please wait, while we are checking the credentials.");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();

                    ValidatephoneNumber(name, address, telephone_number, email);
            }

    }

    private void ValidatephoneNumber(String name, String address, String telephone_number, String email)
    {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!(dataSnapshot.child("DeliveryUser").child(telephone_number).exists()))
                {
                    HashMap<String, Object> userdataMap = new HashMap<> ();
                    userdataMap.put ("name", name);
                    userdataMap.put ("address", address);
                    userdataMap.put ("telephone_number", telephone_number);
                    userdataMap.put ("email", email);

                    RootRef.child ("DeliveryUser").child (name).updateChildren (userdataMap)
                            .addOnCompleteListener (new OnCompleteListener<Void> () {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    if (task.isSuccessful ()) {
                                        Toast.makeText (DeliveryActivityMain.this, "Congratulations, your account has been created.", Toast.LENGTH_SHORT).show ();
                                        loadingBar.dismiss();

                                        Intent intent = new Intent (DeliveryActivityMain.this, Instructions.class);
                                        startActivity (intent);
                                    }

                                    else {
                                        loadingBar.dismiss ();
                                        Toast.makeText (DeliveryActivityMain.this, "Network Error,Please try again.", Toast.LENGTH_SHORT).show ();
                                    }
                                }
                            });


                }
                else
                {
                    Toast.makeText(DeliveryActivityMain.this, "This " + telephone_number + "already exists.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(DeliveryActivityMain.this, "please try again using another phone number", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent (DeliveryActivityMain.this, Instructions.class);
                    startActivity (intent);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}



