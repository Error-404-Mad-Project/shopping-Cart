package com.shoppingcart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shoppingcart.Prevalent.Prevalent;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class EditComment extends AppCompatActivity {
    private TextView UserName, productName;

    private TextView txtcommentTv, txttimeTv, txtcomment_date;
    private Button editCommentPost;
    private EditText commentEt;
    private String commentWrite;

    private  String CommentID = "";
    private  String productID = "";

    private DatabaseReference commentRef,productRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_comment);


        productID = getIntent().getStringExtra("pid");
        productRef = FirebaseDatabase.getInstance().getReference().child(productID);

        CommentID = getIntent().getStringExtra("cId");
        commentRef = FirebaseDatabase.getInstance().getReference().child(CommentID);

        editCommentPost=(Button)findViewById(R.id.Edit_Comment);
        txtcommentTv =(TextView)findViewById(R.id.commentTv);
        txttimeTv =(TextView)findViewById(R.id.timeTv);
        txtcomment_date =(TextView)findViewById(R.id.comment_date);
        commentEt = (EditText) findViewById(R.id.commentEt);

        displayUpdateComments();

        editCommentPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    applyChanges();
            }
        });

    }

    private void applyChanges() {
        commentWrite = commentEt.getText().toString();

        if(commentWrite.equals("")){
            Toast.makeText(EditComment.this, "Update Comment.", Toast.LENGTH_SHORT).show();
        }
        else{

            Calendar calForDate = Calendar.getInstance();
            SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
            final String saveCurrentDate = currentDate.format(calForDate.getTime());

            SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
            final String saveCurrentTime = currentTime.format(calForDate.getTime());

            final String CommentRandomKey = saveCurrentDate + saveCurrentTime;

            final HashMap<String, Object> commentMap = new HashMap<>();
            commentMap.put("pid", productID);
            commentMap.put("cId", CommentRandomKey);
            commentMap.put("pname", productName.getText().toString());
            commentMap.put("name", UserName.getText().toString());
            commentMap.put("comment", commentWrite);
            commentMap.put("date", saveCurrentDate);
            commentMap.put("time", saveCurrentTime);

            commentRef.updateChildren(commentMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        commentRef.child("Admin View").child(Prevalent.currentOnlineUser.getPhone())
                                .child("Products").child("productID")
                                .child("Comments").child("cId")
                                .updateChildren(commentMap)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(EditComment.this, "Changes appli successfully.", Toast.LENGTH_SHORT).show();

                                            Intent intent = new Intent(EditComment.this,Viewcomments.class);
                                            startActivity(intent);
                                        }
                                    }
                                });
                    }
                }
            });

        }
    }

    private void displayUpdateComments() {
        commentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    String uName = dataSnapshot.child("name").getValue().toString();
                    String comment = dataSnapshot.child("comment").getValue().toString();
                    String date = dataSnapshot.child("date").getValue().toString();
                    String time = dataSnapshot.child("time").getValue().toString();



                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
