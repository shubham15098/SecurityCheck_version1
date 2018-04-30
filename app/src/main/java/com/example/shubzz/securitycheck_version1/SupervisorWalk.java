package com.example.shubzz.securitycheck_version1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class SupervisorWalk extends AppCompatActivity {

    DatabaseReference Database3;
    ArrayList <Integer> myArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supervisor_walk);

//        ---------------------------------------------------
        readFromFirebase();

//        ---------------------------------------------------
    }

    public void readFromFirebase()
    {
        Database3 = FirebaseDatabase.getInstance().getReference("RoundCounter");

        Database3.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s)
            {


                    walkClass w = dataSnapshot.getValue(walkClass.class);
                    Log.d("asd", Integer.toString(w.getCounter()));

                    myArray.add(w.getWalk());




            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
