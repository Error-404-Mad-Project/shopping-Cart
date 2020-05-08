package com.shoppingcart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shoppingcart.Interface.ItemClickListner;
import com.shoppingcart.Model.Comments;
import com.shoppingcart.Prevalent.Prevalent;
import com.squareup.picasso.Picasso;

public class Viewcomments extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DatabaseReference CommentListRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    private Button editBt;
    private TextView txtcommentTv, txttimeTv, txtcomment_date;

    private String type = "";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewcomments);

        CommentListRef = FirebaseDatabase.getInstance().getReference().child("Products").child("Comments");

        Toolbar toolbar = findViewById (R.id.toolbar);
        toolbar.setTitle ("View Comments");
        setSupportActionBar (toolbar);


        recyclerView = findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        txtcommentTv =(TextView)findViewById(R.id.commentTv);
        txttimeTv =(TextView)findViewById(R.id.timeTv);
        txtcomment_date =(TextView)findViewById(R.id.comment_date);
        editBt=findViewById(R.id.editBt);



    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView txtcommentTv, txttimeTv, txtcomment_date,txtnameTv;
        private ItemClickListner listener;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void getName(String name) {
            TextView txtnameTv = (TextView) itemView.findViewById(R.id.nameTv);
            txtcommentTv.setText(name);
        }


        public void getComment(String comment) {
            TextView txtcommentTv = (TextView) itemView.findViewById(R.id.commentTv);
            txtcommentTv.setText(comment);
        }

        public void setDate(String date) {
            TextView txtcomment_date = (TextView) itemView.findViewById(R.id.comment_date);
            txtcomment_date.setText(" Date " +date);
        }

        public void setTime(String time) {
            TextView txttimeTv = (TextView) itemView.findViewById(R.id.timeTv);
            txttimeTv.setText(time);
        }
        public void setName(String time) {
            TextView txttimeTv = (TextView) itemView.findViewById(R.id.timeTv);
            txttimeTv.setText(time);
        }

        public void setItemClickListener(ItemClickListner listener)
        {
            this.listener = listener;
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getAdapterPosition(), false);
        }
    }



    @Override
    public void onStart()
    {
        super.onStart ();

        DatabaseReference commentListRef = FirebaseDatabase.getInstance().getReference().child("Comment List");


        FirebaseRecyclerOptions<Comments> options =
                new FirebaseRecyclerOptions.Builder<Comments> ()
                        .setQuery(commentListRef.child("User View")
                        .child(Prevalent.currentOnlineUser.getPhone())
                        .child("Products").child("Comments"),
                                Comments.class)
                                .build();
                       // .setQuery (CommentListRef, Comments.class)
                       // .build ();

        FirebaseRecyclerAdapter<Comments, CommentViewHolder> adapter =
                new FirebaseRecyclerAdapter<Comments, CommentViewHolder> (options) {
                    @Override
                    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position, @NonNull final Comments model) {
                        holder.txtcommentTv.setText (model.getComment ());
                        holder.txttimeTv.setText (model.getTime());
                        holder.txtcomment_date.setText (model.getDate ());
                        holder.txtnameTv.setText (model.getName());

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CharSequence option[] = new CharSequence[]
                                        {
                                                "Edit",
                                                "Delete"

                                        };

                                AlertDialog.Builder builder = new AlertDialog.Builder(Viewcomments.this);
                                builder.setTitle("Comments option :");

                                builder.setItems(option, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i) {
                                        if(i == 0)
                                        {
                                            Intent intent = new Intent(Viewcomments.this,EditComment.class);
                                            intent.putExtra("cId",model.getcId());
                                            startActivity(intent);

                                        }
                                       else if(i == 1)
                                        {
                                            CommentListRef.child("User View").child(Prevalent.currentOnlineUser.getName())
                                                    .child("Products").child("productID")
                                                    .child("Comments").child(model.getcId())
                                                    .removeValue()
                                                    .addOnCompleteListener((new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                             if(task.isSuccessful())
                                                             {
                                                                 Toast.makeText(Viewcomments.this,"Delete comment",Toast.LENGTH_SHORT).show();
                                                                 Intent intent = new Intent(Viewcomments.this,Viewcomments.class);

                                                                 startActivity(intent);


                                                             }
                                                        }
                                                    }));

                                        }

                                    }
                                });
                                builder.show();



                            }
                        });



                    }

                    @NonNull
                    @Override
                    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from (parent.getContext ()).inflate (R.layout.comments_view_layout, parent, false);
                        CommentViewHolder holder = new CommentViewHolder(view);
                        return holder;
                    }


                };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }
}
