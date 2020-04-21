package com.shoppingcart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rey.material.widget.LinearLayout;
import com.rey.material.widget.RelativeLayout;

public class Viewcomment extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference Usersref,PostRef;

    private String Post_key,current_user_id;

    private RecyclerView commentList;
    private Button button_write;
    private String commentText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewcomment);

        PostRef = FirebaseDatabase.getInstance().getReference().child("Post").child(Post_key).child("Comments");
        Usersref = FirebaseDatabase.getInstance().getReference().child("Users");

        button_write=(Button)findViewById(R.id.btn_write);

        commentList = (RecyclerView)findViewById(R.id.comment_List);
        commentList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        commentList.setLayoutManager(linearLayoutManager);


        button_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendViewToCommentActivity();
            }

            private void sendViewToCommentActivity() {
                Intent addnewMessage = new Intent(Viewcomment.this,Writecomment.class);
                startActivity(addnewMessage);
            }
        });
    }

    /*public void onButtonClickListener(){
        button_write = (Button)findViewById(R.id.btn_write);
        button_write.setOnClickListener(
                new android.view.View.OnClickListener() {
                    @Override
                    public void onClick(android.view.View v) {
                        Intent intent = new Intent("android.intent.action.Comment");
                        startActivity(intent);
                    }
                });
    }*/

    /*Comments.class,
                R.layout.all_comments_layout,
                commentViewHolder.class,
                PostRef*/

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Comments>options =
                new FirebaseRecyclerOptions.Builder<Comments>()
                .setQuery(PostRef,Comments.class)
                .build();

        FirebaseRecyclerAdapter<Comments,commentViewHolder> firebaseRecyclerAdapter
                =new FirebaseRecyclerAdapter<Comments,commentViewHolder>(options){
            @NonNull
            @Override
            public commentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_comments_layout,parent,false);
                commentViewHolder viewHolder = new commentViewHolder(view);
                return  viewHolder;

            }

            @Override
            protected void onBindViewHolder(@NonNull commentViewHolder viewHolder, int position, @NonNull Comments model) {
                viewHolder.setUsername(model.getUsername());
                viewHolder.setComment(model.getComment());
                viewHolder.setDate(model.getDate());
                viewHolder.setTime(model.getTime());

            }

        };
        commentList.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
        }


    public static class commentViewHolder extends RecyclerView.ViewHolder
    {
        View mView;

        public commentViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;
        }

        public void setUsername(String username)
        {
            TextView myuserName = (TextView)mView.findViewById(R.id.comment_Username);
            myuserName.setText("@"+username + " ");
        }

        public void setComment(String comment)
        {
            TextView myComment = (TextView)mView.findViewById(R.id.comment_Text);
            myComment.setText(comment);
        }

        public void setDate(String date)
        {
            TextView myData = (TextView)mView.findViewById(R.id.comment_date );
            myData.setText(" Date: "+date);
        }

        public void setTime(String time)
        {
            TextView myTime = (TextView)mView.findViewById(R.id.comment_time);
            myTime.setText(" Time: "+time);
        }
    }



}
