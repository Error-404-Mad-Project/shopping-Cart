package com.shoppingcart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.shoppingcart.adapters.AdapterComment;
import com.shoppingcart.models.ModelsComment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class PostDetailsActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    //to get details of user and post
    String hisUid, myUid, myEmail, myName, myDp, postId, hisDp, hisName, pTime, menuItem;

    private String saveCurrentDate, saveCurrentTime, RandomKey;

    private String current_user_id;


    boolean mProcessComment = false;

    //progress bar
    ProgressDialog pd;


    //views
    ImageView uPictureIv, pImageTv;
    TextView uNameTv, pTimeTv, pTitleTv, pDescriptionTv, pCommentTv;
    ImageButton moreBtn;
    LinearLayout profileLayout;
    RecyclerView recyclerView;

    List<ModelsComment> commentList;
    AdapterComment adapterComment;

    //add comment views
    EditText commentEt;
    ImageView sendBtn;
    ImageView cAvatarIv;
    private Menu menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);


        /*FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Posts");

        myRef.setValue("Hello, World!");*/


        // mAuth= FirebaseAuth.getInstance();
        // current_user_id = mAuth.getCurrentUser().getUid();

        //Actionbar and its properties
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Post details");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        //get id of post using intent
       /* Intent intent = getIntent();
        postId = intent.getStringExtra("postId");*/


        //init views
        uPictureIv = findViewById(R.id.uPicture);
        pImageTv = findViewById(R.id.pImage);
        uNameTv = findViewById(R.id.uName);
        pTimeTv = findViewById(R.id.pTime);
        pTitleTv = findViewById(R.id.pTitle);
        pDescriptionTv = findViewById(R.id.pDescription);
        pCommentTv = findViewById(R.id.pCommentTv);
        profileLayout = findViewById(R.id.profileLayout);
        recyclerView = findViewById(R.id.recyclerView);

        commentEt = findViewById(R.id.commentEt);
        sendBtn = findViewById(R.id.sendBtn);
        moreBtn = findViewById(R.id.moreBtn);
        cAvatarIv = findViewById(R.id.cAvatar);

        loadPostInfo();

        //checkUserStatus();

        //loadUserInfo();

        //set subtitle of action bar
        //actionBar.setSubtitle("SignedIn as: "+myEmail);

     //   loadComment();

        //send comment button click
        sendBtn.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                postComment();

            }
        });

        //more button click handle
       /* moreBtn.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                showMoreOptions();
            }
        });*/


    }

/*
    private void loadComment() {
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
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    ModelsComment modelsComment = dataSnapshot.getValue(ModelsComment.class);

                    commentList.add(modelsComment);

                    //set adapter
                    adapterComment = new AdapterComment(getApplicationContext(), commentList);
                    //set adapter
                    recyclerView.setAdapter(adapterComment);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }*/

   /* @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void showMoreOptions() {
        //creating popup menu currently having option delete,we will add more option later
        PopupMenu popupMenu=new PopupMenu(this,moreBtn, Gravity.END);

        if(hisUid.equals(myUid)){
            //add items in menu
            popupMenu.getMenu().add(Menu.NONE,0,0,"Delete");
            popupMenu.getMenu().add(Menu.NONE,0,0,"Edit");
        }

        popupMenu.getMenu().add(Menu.NONE,0,0,"View Detail");

        //item click listner
       // popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener(menuItem){

       // });*/

    private void postComment() {
        pd = new ProgressDialog(this);
        pd.setMessage("Adding comment..");

        //get data from comment edit text
        String comment = commentEt.getText().toString().trim();

        //validate
        if (TextUtils.isEmpty(comment)) {
            //no value is entered
            Toast.makeText(this, "Comment is empty...", Toast.LENGTH_SHORT).show();
            return;
        }

        //each post will have a child "comments" the will contain comments of that post
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Comments");

        ref.setValue(" ");
        /*FirebaseDatabase.getInstance().getReference("Posts").child(postId).child("Comment ");*/
        String timeStamp = String.valueOf(System.currentTimeMillis());

        HashMap<String, Object> hashMap = new HashMap<>();
        //put info in hashmap
        hashMap.put("cId", timeStamp);
        hashMap.put("comment", comment);
        hashMap.put("timestamp", timeStamp);
        hashMap.put("uid", myUid);
        hashMap.put("uEmail", myEmail);
        hashMap.put("uDp", myDp);
        hashMap.put("uName", myName);

        //put this data in db
        ref.child(timeStamp).setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //added
                        //pd.dismiss();
                        Toast.makeText(PostDetailsActivity.this, "Comment Added...", Toast.LENGTH_SHORT).show();
                        commentEt.setText("");
                        // updateCommentCount();
                    }

                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //failed,not added
                // pd.dismiss();
                Toast.makeText(PostDetailsActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

            }

        });


    }


  /*  private void updateCommentCount() {
        //whenever user add commit increase the comment count as we did for like count
        mProcessComment = true;
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Posts").child("pId");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (mProcessComment) {
                    String comment = "" + dataSnapshot.child("pComments").getValue();
                    int newCommentVal = Integer.parseInt(comment) + 1;
                    ref.child("pComments").setValue("" + newCommentVal);
                    mProcessComment = false;


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
/*
    private void loadUserInfo() {
        //get current user info
        Query myRef = FirebaseDatabase.getInstance().getReference("Users");
        myRef.orderByChild("uid").equalTo(myUid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds:dataSnapshot.getChildren()){
                    myName = "" +ds.child("name").getValue();
                    myDp = ""+ds.child("image").getValue();

                    //set data
                   try {
                        //if image is received then set
                        Picasso.get().load(R.drawable.round_face_black_18).into(cAvatarIv);

                    }catch (Exception e){
                        Picasso.get().load(R.drawable.round_face_black_18).into(cAvatarIv);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }*/

    private void loadPostInfo() {
        //get post using the id of the post
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Posts");
        Query query = ref.orderByChild("pId").equalTo(postId);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //keep checking the post until get required post
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    //get data in database
                    String pTitle = "" + ds.child("pTitle").getValue();
                    String pDescr = "" + ds.child("pDescr").getValue();

                    String pTimeStamp = "" + ds.child("pTime").getValue();
                    String pImage = "" + ds.child("pImage").getValue();
                    hisDp = "" + ds.child("uDp").getValue();
                    String uid = "" + ds.child("uid").getValue();
                    String uEmail = "" + ds.child("uEmail").getValue();
                    hisName = "" + ds.child("uName").getValue();
                    String commentCount = "" + ds.child("pComments").getValue();

                    // convert timestap to dd/mm/yyyy hh:mm am/pm
                  /* Calendar calendar = Calendar.getInstance(Locale.getDefault());
                    calendar.setTimeInMillis(Long.parseLong(pTimeStamp));
                    String pTime = DateFormat.format("dd/MM/yyyy hh:mm aa",calendar).toString();
*/
                    Calendar calForDate = Calendar.getInstance();
                    SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-YYYY");
                    saveCurrentDate = currentDate.format(calForDate.getTime());

                    Calendar calForTime = Calendar.getInstance();
                    SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
                    saveCurrentTime = currentTime.format(calForTime.getTime());

                    RandomKey = current_user_id + saveCurrentDate + saveCurrentTime;

                    //set data
                    pTitleTv.setText(pTitle);
                    pDescriptionTv.setText(pDescr);
                    pTimeTv.setText(pTime);
                    pCommentTv.setText(commentCount);

                    uNameTv.setText(hisName);

                    //set image view
                    // if (pImage.equals("noImage")) {
                    //hide image view
                    // pImage.setVisibility(View.GONE);
                    //    setVisible(false);

                    // }
                    // else{
                    //show image view
                    // pImage.setVisibility(View.VISIBILITY);
                    //   setVisible(true);

                    //   try{
                    //       Picasso.get().load(pImage).into(pImage);

                    // }
                    //   catch (Exception e){

                    //   }
                    // }

                    //set user image in comment part
                   /* try{
                        Picasso.get().load(hisDp).placeholder(R.drawable.round_face_black_18).into(uPictureIv);

                    }catch (Exception e) {
                        Picasso.get().load(R.drawable.round_face_black_18).into(uPictureIv);


                    }*/


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private void checkUserStatus() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            //user is signed in
            myEmail = user.getEmail();
            myUid = user.getUid();


        } else {
            //user is signed in,go to main activity
            //startActivity(new Intent(this,MainActivity.class));
            // finish();

        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_view, menu);
        //hide some menu items
        menu.findItem(R.id.action_add_post).setVisible(false);
        menu.findItem(R.id.action_search).setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);

    }
}
/*
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_logout{
            FirebaseAuth.getInstance().signOut();
            checkUserStatus();
        }
        return super.onOptionsItemSelected(item);
    }
*/
//}

