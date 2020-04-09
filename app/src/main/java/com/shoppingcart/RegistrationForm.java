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

public class RegistrationForm extends AppCompatActivity {

    private Button nextPage;
    private ProgressDialog loadingBar;
    private static final String CHANNEL_ID = String.valueOf(0);
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    public static final String EXTRA_MESSAGE1 = "Second Message";
    public static final String EXTRA_MESSAGE2 = "Third Message";
    public static final String EXTRA_MESSAGE3 = "Fourth Message";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_registration_form);

        loadingBar = new ProgressDialog(this);
        nextPage = (Button) findViewById(R.id.button);
        nextPage.setOnClickListener (new View.OnClickListener () { /** Called when the user taps the Register button */

        @Override
        public void onClick(View v) {
            // Do something in response to button
            Intent intent = new Intent (RegistrationForm.this,CustomerView.class);

            EditText editText5 = (EditText) findViewById (R.id.editText5);
            EditText editText7 = (EditText) findViewById (R.id.editText7);
            EditText editText8 = (EditText) findViewById (R.id.editText8);
            EditText editText9 = (EditText) findViewById (R.id.editText9);

            String message = editText5.getText ().toString ();
            String message1 = editText7.getText ().toString ();
            String message2 =editText8.getText ().toString ();
            String message3 =editText9.getText ().toString ();

            intent.putExtra (EXTRA_MESSAGE, message);
            intent.putExtra (EXTRA_MESSAGE1, message1);
            intent.putExtra (EXTRA_MESSAGE2, message2);
            intent.putExtra (EXTRA_MESSAGE3, message3);
            startActivity (intent);


            /**Create Loading Bar**/
            loadingBar.setTitle ("Create Account");
            loadingBar.setMessage ("Please wait, while we are checking the credentials");
            loadingBar.setCanceledOnTouchOutside (false);
            loadingBar.show ();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                CharSequence name = "shopping-Cart";
                String description = "shopforme";
                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel channel = new NotificationChannel (CHANNEL_ID, name, importance);
                channel.setDescription (description); // Register the channel with the system; you can't change the importance or other notification behaviors after this
                NotificationManager notificationManager = getSystemService (NotificationManager.class);
                notificationManager.createNotificationChannel (channel);
            }

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
        });


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
