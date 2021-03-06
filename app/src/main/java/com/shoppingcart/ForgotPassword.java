package com.shoppingcart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    private EditText passwordEmail;
    private Button sendEmail;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_forgot_password);

        passwordEmail = (EditText) findViewById (R.id.editText2);
        sendEmail = (Button) findViewById (R.id.button2);
        firebaseAuth = FirebaseAuth.getInstance ();

        sendEmail.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                String userEmail = passwordEmail.getText ().toString ().trim ();

                if(userEmail.equals (""))
                {
                    Toast.makeText (ForgotPassword.this,"Please enter your registered email ID..",Toast.LENGTH_SHORT).show ();
                }
                else
                {
                    firebaseAuth.sendPasswordResetEmail (userEmail).addOnCompleteListener (new OnCompleteListener<Void> () {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful ())
                            {
                                Toast.makeText (ForgotPassword.this,"Password Reset email sent!",Toast.LENGTH_SHORT).show ();
                                finish ();
                                startActivity (new Intent (ForgotPassword.this,Login.class));
                            }
                            else
                            {
                                Toast.makeText (ForgotPassword.this,"Error in sending password reset email",Toast.LENGTH_SHORT).show ();
                            }
                        }
                    });
                }
            }
        });
    }
}
