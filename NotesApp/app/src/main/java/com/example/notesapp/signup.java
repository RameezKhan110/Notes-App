package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class signup extends AppCompatActivity {

    EditText msignupemail, msignuppassword;
    RelativeLayout msignup;
    TextView mgotologin;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        msignupemail = findViewById(R.id.signupemail);
        msignuppassword = findViewById(R.id.signuppassword);
        msignup = findViewById(R.id.signup);
        mgotologin = findViewById(R.id.gotologin);

        firebaseAuth = FirebaseAuth.getInstance();

        msignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = msignupemail.getText().toString().trim();
                String password = msignuppassword.getText().toString().trim();

                if(mail.isEmpty() || password.isEmpty()){
                    Toast.makeText(signup.this, "All fields are required", Toast.LENGTH_SHORT).show();
                }
                else if(password.length()<7){
                    Toast.makeText(signup.this, "Password should be Greater then 7", Toast.LENGTH_SHORT).show();
                }
                else{
                    //register user to firebase
                    firebaseAuth.createUserWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                Toast.makeText(signup.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                sendVerificationEmail();
                            }
                            else{
                                Toast.makeText(signup.this, "Failed To Register The User", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            }
        });

        mgotologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(signup.this, MainActivity.class));
            }
        });

    }

    //send email verification
    private void sendVerificationEmail(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser != null)
        {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(signup.this, "Verification Email Send, Verify It", Toast.LENGTH_SHORT).show();
                    firebaseAuth.signOut();
                    finish();
                    startActivity(new Intent(signup.this, MainActivity.class));
                }
            });
        }

        else{
            Toast.makeText(this, "Failed To Send Verification Email", Toast.LENGTH_SHORT).show();
        }
    }


}