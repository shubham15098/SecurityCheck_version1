package com.example.shubzz.securitycheck_version1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import android.os.Handler;

public class Splashscreen extends AppCompatActivity {
    String now_playing, earned;
    FirebaseDatabase database;
    FirebaseUser user;
    FirebaseFirestore db;
    DocumentReference docRef;
    Intent RegularUser;
    UserAccountModel userAccountModel;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent i = new Intent(Splashscreen.this, Login.class);
                startActivity(i);
                finish();
            }
        }, 4000);

    }
}
