package com.shoppingcart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.rey.material.widget.LinearLayout;
import com.rey.material.widget.RelativeLayout;

public class Viewcomment extends AppCompatActivity {

    private RecyclerView commentList;
    private Button button_write;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewcomment);

        commentList = (RecyclerView)findViewById(R.id.comment_List);
        commentList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        commentList.setLayoutManager(linearLayoutManager);
    }

    public void onButtonClickListener(){
        button_write = (Button)findViewById(R.id.btn_write);
        button_write.setOnClickListener(
                new android.view.View.OnClickListener() {
                    @Override
                    public void onClick(android.view.View v) {
                        Intent intent = new Intent("android.intent.action.Comment");
                        startActivity(intent);
                    }
                });
    }


}
