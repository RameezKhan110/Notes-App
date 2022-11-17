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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    EditText mloginemail, mloginpassword;
    RelativeLayout mlogin, mgotosignup;
    TextView mgotoforgot;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mloginemail = findViewById(R.id.loginemail);
        mloginpassword = findViewById(R.id.loginpassword);
        mlogin = findViewById(R.id.login);
        mgotosignup = findViewById(R.id.gotosignup);
        mgotoforgot = findViewById(R.id.gotoforgot);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        if(firebaseUser != null){
            finish();
            startActivity(new Intent(MainActivity.this, notesactivity.class));
        }

        mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = mloginemail.getText().toString().trim();
                String password = mloginpassword.getText().toString().trim();

                if(mail.isEmpty() || password.isEmpty()){
                    Toast.makeText(MainActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                }
                else{
                    //login the user
                    firebaseAuth.signInWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                checkmailverification();
                            }
                            else{
                                Toast.makeText(MainActivity.this, "Account Doesn't Exist", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                }
            }
        });

        mgotoforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, forgotpassword.class);
                startActivity(intent);
            }
        });

        mgotosignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, signup.class));
            }
        });
    }

    private void checkmailverification(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser.isEmailVerified()){
            Toast.makeText(this, "Logged In", Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(MainActivity.this, notesactivity.class));
        }
        else{
            Toast.makeText(this, "Verify Your Email First", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }

}