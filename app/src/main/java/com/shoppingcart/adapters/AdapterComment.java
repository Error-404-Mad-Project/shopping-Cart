package com.shoppingcart.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shoppingcart.R;
import com.shoppingcart.models.ModelsComment;

import java.util.List;

public class AdapterComment extends RecyclerView.Adapter<AdapterComment.MyHolder>{
   Context context;
   List<ModelsComment> commentList;
   String myUid,postId;

    public AdapterComment(Context context, List<ModelsComment> commentList) {
        this.context = context;
        this.commentList = commentList;
        this.myUid = myUid;
        this.postId = postId;
    }



    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
       //bind the row-comments.xml layout
        View view = LayoutInflater.from(context).inflate(R.layout.row_comment,viewGroup,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        //get the data
        final String uid = commentList.get(i).getUid();
        String name = commentList.get(i).getuName();
        String email = commentList.get(i).getuEmail();
        String image = commentList.get(i).getuDp();
        final String cid = commentList.get(i).getcId();
        String comment = commentList.get(i).getComment();
        String timestamp = commentList.get(i).getTimestamp();

        //set the data

        myHolder.nameTv.setText(name);
        myHolder.commentTv.setText(comment);


        //myHolder.timeTv.setText(timestamp);

        //comment clixk liastner
        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if this comment is by currently signed in user or not

                if(myUid.equals(uid)){
                    //my comment
                    //show delete dialog
                    final AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
                    builder.setTitle("Delete");
                    builder.setMessage("Are you sure to delete this message...?");
                    builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //delete comment
                            deleteComment(cid);

                        }
                    });
                            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                   //dismiss dialog
                                    dialog.dismiss();
                                }
                            });

                    //show dialog
                    builder.create().show();
                }
                else{
                    //no my comment count
                    Toast.makeText(context,"con't delete other's comment...",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void deleteComment(String cid) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Posts").child(postId);
        ref.child("Comments").child(cid).removeValue();//it will deleted the comment

        //now update the comment
    }



    @Override
    public int getItemCount() {
        return commentList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        //declare views from row_comment.xml
        ImageView avatarIv;
        ImageButton Delete_bin;
        Button Comment_replyButton,editBt;
        TextView nameTv,commentTv,timeTv;


        public MyHolder(@NonNull View itemView) {
            super(itemView);
            avatarIv=itemView.findViewById(R.id.avatarIv);
            nameTv=itemView.findViewById(R.id.nameTv);
            commentTv=itemView.findViewById(R.id.commentTv);
            timeTv=itemView.findViewById(R.id.timeTv);
            Delete_bin=itemView.findViewById(R.id.Delete_bin);
            Comment_replyButton=itemView.findViewById(R.id.Comment_replyButton);
            editBt = itemView.findViewById(R.id.editBt);

        }
    }
}
