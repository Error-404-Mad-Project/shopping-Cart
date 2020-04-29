package com.shoppingcart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shoppingcart.Model.Products;
import com.shoppingcart.Prevalent.Prevalent;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class WriteComment extends AppCompatActivity {

    private EditText commentEt;
    private ImageButton sendBtn;
    private String commentWrite;

    private TextView UserName, productName;


    private String productID = "", state = "Normal";
    private String CommentRandomKey;

    private DatabaseReference CommentListRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_comment);

        productID = getIntent().getStringExtra("pid");

        UserName = (TextView) findViewById(R.id.view_uName);
        productName = (TextView) findViewById(R.id.product_name);
        sendBtn = (ImageButton) findViewById(R.id.sendBtn);
        commentEt = (EditText) findViewById(R.id.commentEt);

        getProductDetails(productID);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commentWrite = commentEt.getText().toString();

                if (TextUtils.isEmpty(commentWrite)) {
                    // AllowAccessToAccount(name);
                    Toast.makeText(WriteComment.this, "Plz write Comment.", Toast.LENGTH_SHORT).show();
                } else {
                    // StoreProductImageInformation();
                    addingToCommentList();
                }

            }
        });
    }


    private void addingToCommentList() {


        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        final String saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        final String saveCurrentTime = currentTime.format(calForDate.getTime());

        final String CommentRandomKey = saveCurrentDate + saveCurrentTime;

        final DatabaseReference CommentListRef = FirebaseDatabase.getInstance().getReference().child("CommentList");

        final HashMap<String, Object> commentMap = new HashMap<>();
        commentMap.put("cId", CommentRandomKey);
        commentMap.put("pname", productName.getText().toString());
        commentMap.put("name", UserName.getText().toString());
        commentMap.put("comment", commentWrite);
        commentMap.put("date", saveCurrentDate);
        commentMap.put("time", saveCurrentTime);


       CommentListRef.child("Users View").child(Prevalent.currentOnlineUser.getName())
                .child("Products").child("productID")
                .child("Comments").child("cId")
                .updateChildren(commentMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            CommentListRef.child("Admin View").child(Prevalent.currentOnlineUser.getName())
                                    .child("Products").child("productID")
                                    .child("Comments").child("cId")
                                    .updateChildren(commentMap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(WriteComment.this, "Added to Cart List.", Toast.LENGTH_SHORT).show();

                                                Intent intent = new Intent(WriteComment.this,Viewcomments.class);
                                                startActivity(intent);
                                            }
                                        }
                                    });
                        }
                    }
                });
    }

    private void getProductDetails(String productID) {
        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference().child("Products");

        productsRef.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Products products = dataSnapshot.getValue(Products.class);

                    productName.setText(products.getPname());

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}



