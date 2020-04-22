package com.shoppingcart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shoppingcart.adapters.AdapterComment;
import com.shoppingcart.models.ModelsComment;

import java.util.ArrayList;
import java.util.List;

public class PostViewComment extends AppCompatActivity {

    RecyclerView recyclerView;
    List<ModelsComment> commentList;
    AdapterComment adapterComment;

    Button editBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_view_comment);


        //Actionbar and its properties
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Edit Comment");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


        recyclerView=findViewById(R.id.recyclerView);
        editBt=findViewById(R.id.editBt);

        loadcomment();
    }




    private void loadcomment() {
        //layout(Linier) for recycleview
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        //set layout to recycle view
        recyclerView.setLayoutManager(layoutManager);

        //init comment list
        commentList = new ArrayList<>();
        //path of the post,to get it's comments
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Comments");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                commentList.clear();
                //for (DataSnapshot ds:dataSnapshot.getChildren()){
                    ModelsComment modelsComment =dataSnapshot.getValue(ModelsComment.class);

                    commentList.add(modelsComment);

                    //set adapter
                    adapterComment = new AdapterComment(getApplicationContext(),commentList);
                    //set adapter
                    recyclerView.setAdapter(adapterComment);
               // }//
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}
