package com.shoppingcart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.InetAddresses;
import android.net.Uri;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.shoppingcart.Prevalent.Prevalent;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class UpdateCustomer extends AppCompatActivity {

    private CircleImageView profileImageView;
    private EditText fullNameEditText, userEmailEditText, addressEditText, phoneEditText;
    private TextView profileChangeTextBtn,  closeTextBtn, saveTextButton;
    private Button mButtonSave;


    private Uri imageUri;
    private String myUrl = "";
    private StorageTask uploadTask;
    private StorageReference storageProfilePictureRef;
    private String checker = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_update_customer);

        storageProfilePictureRef = FirebaseStorage.getInstance ().getReference ().child ("Profile pictures");

        profileImageView = (CircleImageView) findViewById(R.id.settings_profile_image);
        fullNameEditText = (EditText) findViewById(R.id.settings_full_name);
        userEmailEditText = (EditText) findViewById(R.id.settings_email);
        addressEditText = (EditText) findViewById(R.id.settings_address);
        phoneEditText = (EditText) findViewById(R.id.phone);

        profileChangeTextBtn = (TextView) findViewById(R.id.profile_image_change_btn);
        closeTextBtn = (TextView) findViewById(R.id.close_settings_btn);
        saveTextButton = (TextView) findViewById(R.id.update_account_settings_btn);


//
//        if (getActionBar () != null )
//        {
//            getSupportActionBar ().setTitle ("Update Details");
//            getSupportActionBar ().setDisplayHomeAsUpEnabled (true);
//        }

        userInfoDisplay(profileImageView, fullNameEditText, userEmailEditText, addressEditText, phoneEditText);

        closeTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                deleteProfile();

            }
        });

        saveTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (checker.equals("clicked"))
                {
                    userInfoSaved();
                }
                else
                {
                    updateOnlyUserInfo();
                }
            }
        });

        profileChangeTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                checker = "clicked";

                CropImage.activity(imageUri)
                        .setAspectRatio(1, 1)
                        .start(UpdateCustomer.this);
            }
        });
    }

    private void updateOnlyUserInfo()
    {
        DatabaseReference reference = FirebaseDatabase.getInstance ().getReference ().child ("Users");

        HashMap<String, Object> userMap = new HashMap<>();
        userMap. put("name", fullNameEditText.getText().toString());
        userMap. put("email", userEmailEditText.getText().toString());
        userMap. put("address", addressEditText.getText().toString());
        userMap. put("phone", phoneEditText.getText().toString());

        reference.child(Prevalent.currentOnlineUser.getName ()).updateChildren(userMap);

        startActivity (new Intent (UpdateCustomer.this,HomeActivity.class));
        Toast.makeText (UpdateCustomer.this,"Profile Update Successfully.",Toast.LENGTH_SHORT).show ();
        finish ();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult (requestCode, resultCode, data);

        if (requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE  &&  resultCode==RESULT_OK  &&  data!=null)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();

            profileImageView.setImageURI(imageUri);
        }
        else
        {
            Toast.makeText(this, "Error, Try Again.", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(UpdateCustomer.this, UpdateCustomer.class));
            finish();
        }
    }

    private void deleteProfile(){

        AlertDialog.Builder builder = new AlertDialog.Builder (this);
        builder.setMessage ("Are you sure want to delete your data?")
                .setCancelable (false)
                .setPositiveButton ("Yes", new DialogInterface.OnClickListener () {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseReference UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");

                        UsersRef.child (Prevalent.currentOnlineUser.getName ()).removeValue ().addOnCompleteListener (new OnCompleteListener<Void> () {

                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                Intent intent = new Intent (UpdateCustomer.this, Login.class);
                                startActivity (intent);
                                finish();
                            }
                        });
                    }
                })
                .setNegativeButton ("No", new DialogInterface.OnClickListener () {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel ();
                    }
                });
        AlertDialog alertDialog = builder.create ();
        alertDialog.show ();

    }


    private void userInfoSaved()
    {
        if (TextUtils.isEmpty(fullNameEditText.getText().toString()))
        {
            Toast.makeText(this, "Name is mandatory.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(userEmailEditText.getText().toString()))
        {
            Toast.makeText(this, "Email is mandatory.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(addressEditText.getText().toString()))
        {
            Toast.makeText(this, "Address is address.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(phoneEditText.getText().toString()))
        {
            Toast.makeText(this, "Phone is mandatory.", Toast.LENGTH_SHORT).show();
        }

        else if(checker.equals("clicked"))
        {
            uploadImage();
        }
    }

    private void uploadImage()
    {
        final ProgressDialog progressDialog = new ProgressDialog (this);
        progressDialog.setTitle ("Update Profile");
        progressDialog.setMessage ("Please wait, while we are updating your account information");
        progressDialog.setCanceledOnTouchOutside (false);
        progressDialog.show ();

        if (imageUri != null)
        {
            final StorageReference fileRef = storageProfilePictureRef
                    .child (Prevalent.currentOnlineUser.getName() + ".jpg");

            uploadTask = fileRef.putFile (imageUri);

            uploadTask.continueWithTask (new Continuation () {
                @Override
                public Object then(@NonNull Task task) throws Exception
                {
                    if (!task.isSuccessful ())
                    {
                        throw task.getException ();
                    }

                    return fileRef.getDownloadUrl ();
                }
            })
            .addOnCompleteListener (new OnCompleteListener<Uri> () {
                @Override
                public void onComplete(@NonNull Task <Uri> task)
                {
                    if (task.isSuccessful ())
                    {
                        Uri downloadUrl = task.getResult ();
                        myUrl = downloadUrl.toString ();

                        DatabaseReference reference = FirebaseDatabase.getInstance ().getReference ().child ("Users");


                        HashMap<String, Object> userMap = new HashMap<>();
                        userMap. put("name", fullNameEditText.getText().toString());
                        userMap. put("email", userEmailEditText.getText().toString());
                        userMap. put("address", addressEditText.getText().toString());
                        userMap. put("phone", phoneEditText.getText().toString());

                        userMap. put("image", myUrl);
                        reference.child(Prevalent.currentOnlineUser.getName()).updateChildren(userMap);

                        progressDialog.dismiss ();

                        startActivity (new Intent (UpdateCustomer.this,HomeActivity.class));
                        Toast.makeText (UpdateCustomer.this,"Profile Update Successfully.",Toast.LENGTH_SHORT).show ();
                        finish ();
                    }
                    else
                    {
                        progressDialog.dismiss ();
                        Toast.makeText (UpdateCustomer.this,"Error.",Toast.LENGTH_SHORT).show ();
                    }
                }
            });
        }
        else
        {
            Toast.makeText (this,"Image is not selected.",Toast.LENGTH_SHORT).show ();
        }
    }


    private void userInfoDisplay(final CircleImageView profileImageView, final EditText fullNameEditText, final EditText userEmailEditText, final EditText addressEditText, final EditText phoneEditText)
    {
        DatabaseReference UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");

        UsersRef.addChildEventListener (new ChildEventListener () {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.exists())
                {

                    String Name = dataSnapshot.child("name").getValue (String.class);

                    if (Prevalent.currentOnlineUser.getName ().equals (Name))
                    {
//                        String image = dataSnapshot.child("image").getValue().toString();
                        String name = dataSnapshot.child("name").getValue().toString();
                        String email = dataSnapshot.child("email").getValue().toString();
                        String address = dataSnapshot.child("address").getValue().toString();
                        String phone = dataSnapshot.child("phone").getValue().toString();

//                        Picasso.get().load(image).into(profileImageView);
                        fullNameEditText.setText(name);
                        userEmailEditText.setText(email);
                        addressEditText.setText(address);
                        phoneEditText.setText(phone);
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        UsersRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot)
//            {
//                if (dataSnapshot.exists())
//                {
//
//                    String Name = dataSnapshot.child("name").getValue (String.class);
//
//                    if (Prevalent.currentOnlineUser.getName ().equals (Name))
//                    {
//                        String image = dataSnapshot.child("image").getValue().toString();
//                        String name = dataSnapshot.child("name").getValue().toString();
//                        String email = dataSnapshot.child("email").getValue().toString();
//                        String address = dataSnapshot.child("address").getValue().toString();
//                        String phone = dataSnapshot.child("phone").getValue().toString();
//
//                        Picasso.get().load(image).into(profileImageView);
//                        fullNameEditText.setText(name);
//                        userEmailEditText.setText(email);
//                        addressEditText.setText(address);
//                        phoneEditText.setText(phone);
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }
}
