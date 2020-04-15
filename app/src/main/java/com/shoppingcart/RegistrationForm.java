package com.shoppingcart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.regex.Pattern;

public class RegistrationForm extends AppCompatActivity {


    private EditText userName, emailAddress, address, phoneNumber, password, confirmPassword;
    private ProgressDialog loadingBar;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile ("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");


    private Button nextPage;

    private static final String CHANNEL_ID = String.valueOf (0);
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    public static final String EXTRA_MESSAGE1 = "Second Message";
    public static final String EXTRA_MESSAGE2 = "Third Message";
    public static final String EXTRA_MESSAGE3 = "Fourth Message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_registration_form);

        userName = (EditText) findViewById (R.id.editText5);
        emailAddress = (EditText) findViewById (R.id.editText7);
        address = (EditText) findViewById (R.id.editText8);
        phoneNumber = (EditText) findViewById (R.id.editText9);
        password = (EditText) findViewById (R.id.editText10);
        confirmPassword = (EditText) findViewById (R.id.editText11);
        loadingBar = new ProgressDialog (this);
        nextPage = (Button) findViewById (R.id.button);


        nextPage.setOnClickListener (new View.OnClickListener () {
            /**
             * Called when the user taps the Register button
             */
            private boolean validateUsername() {
                String usernameInput = userName.getText ().toString ().trim ();

                if (usernameInput.isEmpty ()) {
                    userName.setError ("Field can't be empty");
                    return false;
                } else if (usernameInput.length () > 15) {
                    userName.setError ("Username too long");
                    return false;
                } else {
                    userName.setError (null);
                    return true;
                }
            }

            @Override
            public void onClick(View v) {
                CreateAccount ();
                // Do something in response to button
                Intent LoginIntent = new Intent (RegistrationForm.this, Login.class);

                EditText editText5 = (EditText) findViewById (R.id.editText5);
                EditText editText7 = (EditText) findViewById (R.id.editText7);
                EditText editText8 = (EditText) findViewById (R.id.editText8);
                EditText editText9 = (EditText) findViewById (R.id.editText9);

                String message = editText5.getText ().toString ();
                String message1 = editText7.getText ().toString ();
                String message2 = editText8.getText ().toString ();
                String message3 = editText9.getText ().toString ();

                LoginIntent.putExtra (EXTRA_MESSAGE, message);
                LoginIntent.putExtra (EXTRA_MESSAGE1, message1);
                LoginIntent.putExtra (EXTRA_MESSAGE2, message2);
                LoginIntent.putExtra (EXTRA_MESSAGE3, message3);
                startActivity (LoginIntent);

            }

            private boolean validateEmail() {
                String emailInput = emailAddress.getText ().toString ().trim ();

                if (emailInput.isEmpty ()) {
                    emailAddress.setError ("Field can't be empty");
                    return false;
                } else if (!Patterns.EMAIL_ADDRESS.matcher (emailInput).matches ()) {
                    emailAddress.setError ("Please enter a valid email address");
                    return false;
                } else {
                    emailAddress.setError (null);
                    return true;
                }
            }

            private boolean validatePassword() {
                String passwordInput = password.getText ().toString ().trim ();

                if (passwordInput.isEmpty ()) {
                    password.setError ("Field can't be empty");
                    return false;
                } else if (!PASSWORD_PATTERN.matcher (passwordInput).matches ()) {
                    password.setError ("Password too weak");
                    return false;
                } else {
                    password.setError (null);
                    return true;
                }
            }
        });
    }

    public void CreateAccount() {
        String user = userName.getText ().toString ();
        String email = emailAddress.getText ().toString ();
        String add = address.getText ().toString ();
        String number = phoneNumber.getText ().toString ();
        String pwd = password.getText ().toString ();
        String cnf_pwd = confirmPassword.getText ().toString ();

        if (TextUtils.isEmpty (user)) {
            Toast.makeText (this, "Please enter your Name..", Toast.LENGTH_SHORT).show ();
        } else if (TextUtils.isEmpty (email)) {
            Toast.makeText (this, "Please enter valid email address..", Toast.LENGTH_SHORT).show ();
        } else if (TextUtils.isEmpty (add)) {
            Toast.makeText (this, "Please enter your address..", Toast.LENGTH_SHORT).show ();
        } else if (TextUtils.isEmpty (number)) {
            Toast.makeText (this, "Please enter your phone number..", Toast.LENGTH_SHORT).show ();
        } else {
            /**Create Loading Bar**/
            loadingBar.setTitle ("Create Account");
            loadingBar.setMessage ("Please wait, while we are checking the credentials");
            loadingBar.setCanceledOnTouchOutside (false);
            loadingBar.show ();

            Validate (user, email, add, number, pwd, cnf_pwd);
        }
    }

    private void Validate(final String user, final String email, final String add, final String number, final String pwd, final String cnf_pwd) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance ().getReference ();

        RootRef.addListenerForSingleValueEvent (new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!(dataSnapshot.child ("Users").child (number).exists ())) {
                    HashMap<String, Object> userdataMap = new HashMap<> ();
                    userdataMap.put ("email", email);
                    userdataMap.put ("address", add);
                    userdataMap.put ("phone", number);
                    userdataMap.put ("password", pwd);
                    userdataMap.put ("con_Password", cnf_pwd);
                    userdataMap.put ("name", user);

                    RootRef.child ("Users").child (number).updateChildren (userdataMap)
                            .addOnCompleteListener (new OnCompleteListener<Void> () {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful ()) {
                                        Toast.makeText (RegistrationForm.this, "Congratulations, your account has been created.", Toast.LENGTH_SHORT).show ();
                                        loadingBar.dismiss ();

                                        Intent intent = new Intent (RegistrationForm.this, Login.class);
                                        startActivity (intent);
                                    } else {
                                        loadingBar.dismiss ();
                                        Toast.makeText (RegistrationForm.this, "Network Error,Please try again.", Toast.LENGTH_SHORT).show ();
                                    }
                                }
                            });

                } else {
                    Toast.makeText (RegistrationForm.this, "This" + number + "already exists.", Toast.LENGTH_SHORT).show ();
                    loadingBar.dismiss ();
                    Toast.makeText (RegistrationForm.this, "Please try again using another number.", Toast.LENGTH_SHORT).show ();

                    Intent intent = new Intent (RegistrationForm.this, MainActivity.class);
                    startActivity (intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

}








//            });
////
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                CharSequence name = "shopping-Cart";
//                String description = "shopforme";
//                int importance = NotificationManager.IMPORTANCE_DEFAULT;
//                NotificationChannel channel = new NotificationChannel (CHANNEL_ID, name, importance);
//                channel.setDescription (description); // Register the channel with the system; you can't change the importance or other notification behaviors after this
//                NotificationManager notificationManager = getSystemService (NotificationManager.class);
//                notificationManager.createNotificationChannel (channel);
//            }

//            EditText text = (EditText) findViewById (R.id.editText5);
//            String message1 = text.getText ().toString ();
//
//            intent.setFlags (Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
//            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
//                    .setSmallIcon(R.drawable.ic_launcher_background)
//                    .setContentTitle("Notification from Shop For Me" )
//                    .setContentText("Hello " + message + ", your registration is sucessful!")
//                    .setPriority(NotificationCompat.PRIORITY_DEFAULT) // Set the intent that will fire when the user taps the notification
//                    .setContentIntent(pendingIntent)
//                    .setAutoCancel(true);
//            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this); // notificationId is a unique int for each notification that you must define
//            notificationManager.notify(0, builder.build());





//    public void REGISTER(View view) {
//
//        EditText text = (EditText) findViewById(R.id.editText5);
//        String message = text.getText().toString();
//
//        // Create an explicit intent for an Activity in your app
//        Intent intent = new Intent(this, CustomerView.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
//                .setSmallIcon(R.drawable.ic_launcher_background)
//                .setContentTitle("Notification from Shop For Me" )
//                .setContentText("Hello " + message + ", your registration is sucessfull!")
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT) // Set the intent that will fire when the user taps the notification
//                .setContentIntent(pendingIntent)
//                .setAutoCancel(true);
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this); // notificationId is a unique int for each notification that you must define
//        notificationManager.notify(0, builder.build());
//    }
//}
