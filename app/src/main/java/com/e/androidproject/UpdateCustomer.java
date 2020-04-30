package com.e.androidproject;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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

import com.e.androidproject.Prevalent.Prevalent;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

public class UpdateCustomer extends AppCompatActivity {

    private ImageView imageView;
    private EditText profileName, profileEmail, profileAddress, profilePhone;
    private  TextView profileChangeTextBtn;
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

        imageView = (ImageView) findViewById (R.id.user_profile_image);
        profileName = (EditText) findViewById (R.id.editText12);
        profileEmail = (EditText) findViewById (R.id.editText14);
        profileAddress = (EditText) findViewById (R.id.editText17);
        profilePhone = (EditText) findViewById (R.id.editText18);
        profileChangeTextBtn = (TextView) findViewById (R.id.textView28);
        mButtonSave = (Button) findViewById (R.id.button7);

        if (getActionBar () != null )
        {
            getSupportActionBar ().setTitle ("Update Details");
            getSupportActionBar ().setDisplayHomeAsUpEnabled (true);
        }

        userInfoDisplay (imageView, profileName, profileEmail, profileAddress, profilePhone);

        mButtonSave.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if (checker.equals ("clicked"))
                {
                    userInfoSaved ();

                }
                else
                {
                    updateOnlyUserInfo ();
                }
            }

        });

        profileChangeTextBtn.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                checker = "clicked";

                // start cropping activity for pre-acquired image saved on the device
                CropImage.activity(imageUri)
                        .setAspectRatio (1,1)
                        .start(UpdateCustomer.this);
            }
        });
    }

    private void updateOnlyUserInfo()
    {
        DatabaseReference reference = FirebaseDatabase.getInstance ().getReference ().child ("Users");

        HashMap<String, Object> userMap = new HashMap<> ();
        userMap.put ("name",profileName.getText ().toString ());
        userMap.put ("email",profileEmail.getText ().toString ());
        userMap.put ("address",profileAddress.getText ().toString ());
        userMap.put ("phone",profilePhone.getText ().toString ());
        reference.child (Prevalent.currentOnlineUser.getName ()).updateChildren (userMap);

        startActivity (new Intent (UpdateCustomer.this,Login.class));
        Toast.makeText (UpdateCustomer.this,"Profile Update Successfully.",Toast.LENGTH_SHORT).show ();
        finish ();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult (requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult (data);
            imageUri = result.getUri ();

            imageView.setImageURI (imageUri);
        }
        else
        {
            Toast.makeText (UpdateCustomer.this,"Sorry, Please try again.",Toast.LENGTH_SHORT).show ();
            startActivity (new Intent (UpdateCustomer.this,UpdateCustomer.class));
            finish ();
        }
    }

    private void userInfoSaved()
    {
        if (TextUtils.isEmpty (profileName.getText ().toString ()))
        {
            Toast.makeText (UpdateCustomer.this,"Name is mandatory.",Toast.LENGTH_SHORT).show ();
        }
        else if (TextUtils.isEmpty (profileEmail.getText ().toString ()))
        {
            Toast.makeText (UpdateCustomer.this,"Email is mandatory.",Toast.LENGTH_SHORT).show ();
        }
        else if (TextUtils.isEmpty (profileAddress.getText ().toString ()))
        {
            Toast.makeText (UpdateCustomer.this,"Address is mandatory.",Toast.LENGTH_SHORT).show ();
        }
        else if (TextUtils.isEmpty (profilePhone.getText ().toString ()))
        {
            Toast.makeText (UpdateCustomer.this,"Phone is mandatory.",Toast.LENGTH_SHORT).show ();
        }
        else if (checker.equals ("clicked"))
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
                    .child (Prevalent.currentOnlineUser.getName () + ".jpg");

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

                                HashMap<String, Object> userMap = new HashMap<> ();
                                userMap.put ("name",profileName.getText ().toString ());
                                userMap.put ("email",profileEmail.getText ().toString ());
                                userMap.put ("address",profileAddress.getText ().toString ());
                                userMap.put ("phone",profilePhone.getText ().toString ());
                                userMap.put ("image",myUrl);
                                reference.child (Prevalent.currentOnlineUser.getName ()).updateChildren (userMap);

                                progressDialog.dismiss ();

                                startActivity (new Intent (UpdateCustomer.this,Login.class));
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
            Toast.makeText (UpdateCustomer.this,"Image is not selected.",Toast.LENGTH_SHORT).show ();
        }
    }

    private void userInfoDisplay(final ImageView imageView, final EditText profileName, final EditText profileEmail, final EditText profileAddress, final EditText profilePhone)
    {
        DatabaseReference UsersRef = FirebaseDatabase.getInstance ().getReference ().child ("Users").child (Prevalent.currentOnlineUser.getName ());

        UsersRef.addValueEventListener (new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists ())
                {
                    if (dataSnapshot.child ("image").exists ())
                    {
                        String image = dataSnapshot.child ("image").getValue ().toString ();
                        String name = dataSnapshot.child ("name").getValue ().toString ();
                        String email = dataSnapshot.child ("email").getValue ().toString ();
                        String address = dataSnapshot.child ("address").getValue ().toString ();
                        String phone = dataSnapshot.child ("phone").getValue ().toString ();

                        Picasso.get().load(image).into(imageView);
                        profileName.setText (name);
                        profileEmail.setText (email);
                        profileAddress.setText (address);
                        profilePhone.setText (phone);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
