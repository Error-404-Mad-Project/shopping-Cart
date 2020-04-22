package com.shoppingcart.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shoppingcart.R;
import com.shoppingcart.models.ModelsPost;

import java.util.List;

public class AdapterPost extends RecyclerView.Adapter<AdapterPost.MyHolder> {

    Context context;
    List<ModelsPost> postList;

    String myUid;

    private DatabaseReference postRef;

    public AdapterPost(Context context, List<ModelsPost> postList) {
        this.context = context;
        this.postList = postList;
        myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        postRef=FirebaseDatabase.getInstance().getReference().child("Posts");
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_posts,viewGroup,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myholder, int i) {

        String uid = postList.get(i).getuid();
        String name = postList.get(i).getuName();
        String pId = postList.get(i).getuPicture();
        String pTitle = postList.get(i).getpTitle();
        String pDescription = postList.get(i).getpDescription();
        String moreBtn = postList.get(i).getMoreBtn();
        String pTime = postList.get(i).getpTime();






    }

    @Override
    public int getItemCount() {
        return postList.size();
    }


     class MyHolder extends RecyclerView.ViewHolder {
         public MyHolder(@NonNull View itemView) {
             super(itemView);
         }
     }


}
