package com.shoppingcart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.provider.CalendarContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class Writecomment extends AppCompatActivity {


   // private StorageReference PostMassageReference;
    private DatabaseReference PostRef;
    private FirebaseAuthException mAuth;

    private String Post_key,current_user_id;

    private Button postCommentButton;
    private EditText commentInputText;


    //private String saveCurrentDate,saveCurrentTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writecomment);


       // PostMassageReference = FirebaseStorage.getInstance().getReference();

        Post_key = getIntent().getExtras().get("PostKey").toString();
        PostRef = FirebaseDatabase.getInstance().getReference().child("Post").child(Post_key);

        //mAuth=FirebaseAuthException.getInstence();

        commentInputText=(EditText)findViewById(R.id.comment_Input);
        postCommentButton=(Button) findViewById(R.id.post_comment_btn);

        postCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                     commentInputText.setText("");
            }
        });
        
       // @Override
              //  public void onCancelled(DatabaseError databaseError){
            
       //  }




       /* public static class  CommentsViewHolder extends RecyclerView.ViewHolder{
            view mView;
            public CommentsViewHolder(@NonNull View itemView) {
                super(itemView);
                mView = itemView;
            }
        }*/


        String commentText = commentInputText.getText().toString();
        if(TextUtils.isEmpty(commentText)){
            Toast.makeText(this,"please write text to comment...", Toast.LENGTH_SHORT).show();

        }
        else{
           /* Calendar calForDate= Calendar.getInstance();
            SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-YYYY");
           final string saveCurrentDate = currentDate.format(calForDate.getTime());

            Calendar calForTime = Calendar.getInstance();
            SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
            final string saveCurrentTime = currentTime.format(calForTime.getTime());

            final string RandomKey = saveCurrentDate + saveCurrentTime;*/

            HashMap commentMap = new HashMap();
            commentMap.put("Comment",commentText);
            //commentMap.put("date",saveCurrentDate);
           // commentMap.put("time",saveCurrentTime);





        }
        
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


}
