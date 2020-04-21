package com.shoppingcart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import static android.widget.Toast.LENGTH_SHORT;

public class Writecomment extends AppCompatActivity {


   // private StorageReference PostMassageReference;
    private FirebaseAuth mAuth;
    private DatabaseReference Usersref,PostRef;

    private String Post_key,current_user_id;

    private Button postCommentButton;
    private EditText commentInputText;
    private String commentText;
    private Uri texturi;

    private String saveCurrentDate,saveCurrentTime,RandomKey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writecomment);

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        //DatabaseReference myRef = database.getReference("message");

       // PostMassageReference = FirebaseStorage.getInstance().getReference();

       Post_key = getIntent().getExtras().get("PostKey").toString();

       mAuth= FirebaseAuth.getInstance();
       current_user_id = mAuth.getCurrentUser().getUid();
       Usersref = FirebaseDatabase.getInstance().getReference().child("Users");
       PostRef = FirebaseDatabase.getInstance().getReference().child("Post").child(Post_key).child("Comments");


        commentInputText=(EditText)findViewById(R.id.comment_Input);
        postCommentButton=(Button) findViewById(R.id.post_comment_btn);

        postCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // validatePostInfo();

                Usersref.child(current_user_id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            String username = dataSnapshot.child("username").getValue().toString();

                            validatecomment(username);
                            commentInputText.setText("");

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

          /*  private void validatePostInfo() {
                commentText = commentInputText.getText().toString();

                 commentText = commentInputText.getText().toString();
                if (TextUtils.isEmpty(commentText)) {
                   //Toast.makeText(this, "please write text to comment...", LENGTH_SHORT).show();

                } else {

                }
            }*/


            });
/*
            private void validatePostInfo() {
                String commentTexte = commentInputText.getText().toString();

                String commentText = commentInputText.getText().toString();
                if(TextUtils.isEmpty(commentText)){
                    Toast.makeText(this,"please write text to comment...", LENGTH_SHORT).show();

                }

       /* public static class  CommentsViewHolder extends RecyclerView.ViewHolder{
            view mView;
            public CommentsViewHolder(@NonNull View itemView) {
                super(itemView);
                mView = itemView;
            }
        }*/

      /*  String commentText = commentInputText.getText().toString();
        if(TextUtils.isEmpty(commentText)){
            Toast.makeText(this,"please write text to comment...", LENGTH_SHORT).show();

        }*/
      /*  else {
           /* Calendar calForDate= Calendar.getInstance();
            SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-YYYY");
           final string saveCurrentDate = currentDate.format(calForDate.getTime());

            Calendar calForTime = Calendar.getInstance();
            SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
            final string saveCurrentTime = currentTime.format(calForTime.getTime());

            final string RandomKey = saveCurrentDate + saveCurrentTime;*/

               /*     HashMap commentMap = new HashMap();
                    commentMap.put("Comment", commentText);
                    //commentMap.put("date",saveCurrentDate);
                    // commentMap.put("time",saveCurrentTime);


                }
                }
            }/*

        }



    private void sendViewActivity()
    {
        Intent mainIntent = new Intent(Writecomment.this,Viewcomment.class);
        startActivity(mainIntent);
    }

    private void StoringMessageFirebaseStorage(){
       /* Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-YYYY");
        final string saveCurrentDate = currentDate.format(calForDate.getTime());

        Calendar calForTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
        final string saveCurrentTime = currentTime.format(calForTime.getTime());

        postRandomName = saveCurrentDate + saveCurrentTime;*/


        //  StorageReference filePath = PostMassageReference.child("Post Messages").child(url.getLastPathSegment());
    }



    private void validatecomment(String username) {
        String commentText = commentInputText.getText().toString();
        if(TextUtils.isEmpty(commentText))
        {
            Toast.makeText(this,"Please write text to comment...", LENGTH_SHORT).show();
        }
        else
        {
            Calendar calForDate = Calendar.getInstance();
            SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-YYYY");
            saveCurrentDate = currentDate.format(calForDate.getTime());

            Calendar calForTime = Calendar.getInstance();
            SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
            saveCurrentTime = currentTime.format(calForTime.getTime());

            RandomKey = current_user_id + saveCurrentDate + saveCurrentTime;

            HashMap commentMap = new HashMap();
                commentMap.put("uid",current_user_id);
                commentMap.put("Comment", commentText);
                commentMap.put("date", saveCurrentDate);
                commentMap.put("time",saveCurrentTime);
                commentMap.put("username",username);
            PostRef.child(RandomKey).updateChildren(commentMap)
                    .addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if(task.isSuccessful())
                            {
                               // SendToViewComment();
                                Toast.makeText(Writecomment.this,"You have commented successfully...",Toast.LENGTH_LONG).show();

                            }

                            else
                            {
                                Toast.makeText(Writecomment.this,"Error occured, try again... ", LENGTH_SHORT).show();
                            }
                        }
                    });

        }
    }


}
