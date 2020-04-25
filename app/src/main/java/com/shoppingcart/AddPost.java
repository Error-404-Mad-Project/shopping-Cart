package com.shoppingcart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.service.media.MediaBrowserService;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AddPost extends AppCompatActivity {

    private String CategoryName,Description,Price,Pname,savecurrentDate,savecurrentTime;

    private TextView view;
    private EditText pDiscription,pName,pPrice;
  private Button addProduct;
    private static final int Gallery = 1;
   // private ImageView addimage;

    private String productRandomKey,downloadImageURI;

    private Uri ImageUri;
    //private ImageButton addimage;
    private DatabaseReference ProductRef;
    private StorageReference ProductImagesRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);


       // ProductImagesRef = FirebaseStorage.getInstance().getReference().child("Product Images");
        ProductRef =  FirebaseDatabase.getInstance().getReference().child("Products");

        view = (TextView)findViewById(R.id.viewAddProduct);
       // addimage=(ImageButton)findViewById(R.id.addimage);
        pDiscription=(EditText)findViewById(R.id.pDiscription);
        pName=(EditText)findViewById(R.id.pName);
        pPrice=(EditText)findViewById(R.id.pPrice);
        addProduct=(Button)findViewById(R.id.addProduct);
       // addimage=(ImageView)findViewById(R.id.addimage);


     /*  addimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery();

            }
        });*/

        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateProductData();
            }
        });
    }

    /*private void OpenGallery() {
        Intent galleryintent = new Intent();
        galleryintent.setAction(Intent.ACTION_GET_CONTENT);
        galleryintent.setType("image/*");
        startActivityForResult(galleryintent,Gallery);

    }*/

  /*  @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == Gallery && requestCode== RESULT_OK && data!=null){
            ImageUri = data.getData();
            addimage.setImageURI(ImageUri);

        }

    }*/

    private void validateProductData() {

        Description = pDiscription.getText().toString();
        Price = pPrice.getText().toString();
        Pname = pName.getText().toString();


       /* if(ImageUri ==null)
        {
            Toast.makeText(this,"Product image is not updataed ....",Toast.LENGTH_SHORT).show();

        }*/
       if(TextUtils.isEmpty(Description)){
            Toast.makeText(this,"Plz write product Description.",Toast.LENGTH_SHORT).show();

        }
        else if(TextUtils.isEmpty(Price)){
            Toast.makeText(this,"Plz write product price.",Toast.LENGTH_SHORT).show();

        }
        else if(TextUtils.isEmpty(Pname)){
            Toast.makeText(this,"Plz write product name.",Toast.LENGTH_SHORT).show();

        }
        else{
           // StoreProductImageInformation();
           SaveProductInforToDatabase();
        }

    }

   /* private void StoreProductImageInformation() {
        Calendar calendarDate =Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd yyy");
        savecurrentDate = currentDate.format(calendarDate.getTime());

        Calendar calendarTime =Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a ");
        savecurrentTime = currentTime.format(calendarTime.getTime());

        productRandomKey = savecurrentDate + savecurrentTime;

        final StorageReference filePath = ProductImagesRef.child("Post Image").child(ImageUri.getLastPathSegment()+productRandomKey+ ".jpeg");



        final UploadTask uploadTask = filePath.putFile(ImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(AddPost.this,"Error" + message,Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AddPost.this,"Image upload success..."  ,Toast.LENGTH_SHORT).show();

                Task<Uri>uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if(!task.isSuccessful()){
                            throw task.getException();
                        }
                        downloadImageURI = filePath.getDownloadUrl().toString();
                        return  filePath.getDownloadUrl();

                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful())
                        {
                            downloadImageURI = task.getResult().toString();

                            Toast.makeText(AddPost.this," Product image save successful",Toast.LENGTH_SHORT).show();
                            SaveProductInforToDatabase();
                            
                        }
                    }
                });


            }
        });


    }*/

    private void SaveProductInforToDatabase() {

        Calendar calendarDate =Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd yyy");
        savecurrentDate = currentDate.format(calendarDate.getTime());

        Calendar calendarTime =Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a ");
        savecurrentTime = currentTime.format(calendarTime.getTime());

        productRandomKey = savecurrentDate + savecurrentTime;


        final DatabaseReference ProductRef;
        ProductRef = FirebaseDatabase.getInstance().getReference();

        ProductRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (!(dataSnapshot.child("Products").child(productRandomKey).exists())) {

                    HashMap<String, Object> productMap = new HashMap<>();
                    productMap.put("date", savecurrentDate);
                    productMap.put("time", savecurrentTime);
                    productMap.put("description", Description);
                    productMap.put("image", downloadImageURI);
                    productMap.put("price", Price);
                    productMap.put("pName", Pname);
                    productMap.put("pId", productRandomKey);

                    ProductRef.child("Products").child(productRandomKey).updateChildren(productMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(AddPost.this, "Product is added successfull", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(AddPost.this, HomeActivity.class);
                                        startActivity(intent);
                                    } else {
                                        String message = task.getException().toString();
                                        Toast.makeText(AddPost.this, "Error " + message, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}