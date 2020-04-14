package com.shoppingcart;

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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.shoppingcart.Database.DatabaseHelper;

public class RegistrationForm extends AppCompatActivity {

    DatabaseHelper database;
    EditText userName;
    EditText emailAddress;
    EditText address;
    EditText phoneNumber;
    EditText password;
    EditText confirmPassword;
    private ProgressDialog loadingBar;

    private Button nextPage;

    private static final String CHANNEL_ID = String.valueOf(0);
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    public static final String EXTRA_MESSAGE1 = "Second Message";
    public static final String EXTRA_MESSAGE2 = "Third Message";
    public static final String EXTRA_MESSAGE3 = "Fourth Message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_registration_form);

        database = new DatabaseHelper (this);
        userName = (EditText)findViewById (R.id.editText5);
        emailAddress = (EditText)findViewById (R.id.editText7);
        address = (EditText)findViewById (R.id.editText8);
        phoneNumber = (EditText)findViewById (R.id.editText9);
        password = (EditText)findViewById (R.id.editText10);
        confirmPassword = (EditText)findViewById (R.id.editText11);
        loadingBar = new ProgressDialog(this);
        nextPage = (Button) findViewById(R.id.button);
        nextPage.setOnClickListener (new View.OnClickListener () { /** Called when the user taps the Register button */


        @Override
        public void onClick(View v) {
            // Do something in response to button
            Intent LoginIntent = new Intent (RegistrationForm.this,Login.class);

            EditText editText5 = (EditText) findViewById (R.id.editText5);
            EditText editText7 = (EditText) findViewById (R.id.editText7);
            EditText editText8 = (EditText) findViewById (R.id.editText8);
            EditText editText9 = (EditText) findViewById (R.id.editText9);

            String message = editText5.getText ().toString ();
            String message1 = editText7.getText ().toString ();
            String message2 =editText8.getText ().toString ();
            String message3 =editText9.getText ().toString ();

            LoginIntent.putExtra (EXTRA_MESSAGE, message);
            LoginIntent.putExtra (EXTRA_MESSAGE1, message1);
            LoginIntent.putExtra (EXTRA_MESSAGE2, message2);
            LoginIntent.putExtra (EXTRA_MESSAGE3, message3);
            startActivity (LoginIntent);

        }
        });

            nextPage.setOnClickListener (new View.OnClickListener (){
                @Override
                public void onClick(View view) {
                    String user = userName.getText ().toString ().trim ();
                    String email = emailAddress.getText ().toString ().trim ();
                    String add = address.getText ().toString ().trim ();
                    String number = phoneNumber.getText ().toString ().trim ();
                    String pwd = password.getText ().toString ().trim ();
                    String cnf_pwd = confirmPassword.getText ().toString ().trim ();

                    if (pwd.equals (cnf_pwd)){
                        long val = database.addUser (user,pwd);
                        if (val > 0) {
                            Toast.makeText (RegistrationForm.this, "Register Successfully", Toast.LENGTH_SHORT).show ();
                            Intent moveToLogin = new Intent (RegistrationForm.this, Login.class);
                            startActivity (moveToLogin);

                            /**Create Loading Bar**/
                            loadingBar.setTitle ("Create Account");
                            loadingBar.setMessage ("Please wait, while we are checking the credentials");
                            loadingBar.setCanceledOnTouchOutside (false);
                            loadingBar.show ();
                        }
                        else {
                            Toast.makeText (RegistrationForm.this, "Registration Error", Toast.LENGTH_SHORT).show ();
                        }
                    }
                    else {
                        Toast.makeText (RegistrationForm.this,"Password is not matching",Toast.LENGTH_SHORT).show();
                    }
                }
            });
//
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



    }

    public void REGISTER(View view) {

        EditText text = (EditText) findViewById(R.id.editText5);
        String message = text.getText().toString();

        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(this, CustomerView.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Notification from Shop For Me" )
                .setContentText("Hello " + message + ", your registration is sucessfull!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT) // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this); // notificationId is a unique int for each notification that you must define
        notificationManager.notify(0, builder.build());
    }
}
