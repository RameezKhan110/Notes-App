package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgotpassword extends AppCompatActivity {

    EditText mforgotemail;
    RelativeLayout mforgotpassword;
    TextView mgobacktologin;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);

        mforgotemail = findViewById(R.id.forgotemail);
        mforgotpassword = findViewById(R.id.forgotpassword);
        mgobacktologin = findViewById(R.id.gobacktologin);

        firebaseAuth = FirebaseAuth.getInstance();

        mforgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = mforgotemail.getText().toString().trim();
                if(mail.isEmpty()){
                    Toast.makeText(forgotpassword.this, "Enter Email First", Toast.LENGTH_SHORT).show();
                }
                else{
                    //send recover email to user
                    firebaseAuth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(forgotpassword.this, "Recover Mail Sent", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(forgotpassword.this, MainActivity.class));
                            }
                            else{
                                Toast.makeText(forgotpassword.this, "Wrong Email or Account Doesn't Exist", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        mgobacktologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(forgotpassword.this, MainActivity.class));
            }
        });
    }
}