package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class createnote extends AppCompatActivity {

    EditText mcreatetitleofnote, mcreatecontentofnote;
    FloatingActionButton msavenote;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createnote);

        mcreatetitleofnote = findViewById(R.id.createtitleofnote);
        mcreatecontentofnote = findViewById(R.id.createcontentofnote);
        toolbar = findViewById(R.id.toolbarofcreatenote);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        msavenote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = mcreatetitleofnote.getText().toString();
                String content = mcreatecontentofnote.getText().toString();
                if(title.isEmpty() || content.isEmpty()){
                    Toast.makeText(createnote.this, "All Fields Are Required", Toast.LENGTH_SHORT).show();
                }
                else{

                    DocumentReference documentReference = firebaseFirestore.collection("notes").document(firebaseUser.getUid()).collection("mynotes").document();
                    Map<String, Object> note = new HashMap<>();
                    note.put("title", title);
                    note.put("content", content);

                    documentReference.set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(createnote.this, "Notes Created Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(createnote.this, notesactivity.class));
                        }
                    });
                    documentReference.set(note).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(createnote.this, "Failed To Create Note", Toast.LENGTH_SHORT).show();
                        }
                    });



                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}