package com.example.shubzz.securitycheck_version1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WeeklyReport extends AppCompatActivity
{
    private FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;

    DatabaseReference databaseReference;
    DatabaseReference databaseReference2;

    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String,List<String>> listHash;

    List<String> mon = new ArrayList<>();
    List<String> tues = new ArrayList<>();
    List<String> wed = new ArrayList<>();
    List<String> thurs = new ArrayList<>();
    List<String> fri = new ArrayList<>();
    List<String> sat = new ArrayList<>();
    List<String> sun = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_report);

        listView = (ExpandableListView)findViewById(R.id.lvExp);
//        initData();
//        listAdapter = new ExpandableListAdapter(this,listDataHeader,listHash);
//        listView.setAdapter(listAdapter);
        getFirebaseData();
    }

    void getFirebaseData()
    {

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("weeklyReport");

        databaseReference.addChildEventListener(new ChildEventListener()
        {
            @Override
            public void onChildAdded(final DataSnapshot dataSnapshot, String s)
            {

                databaseReference2 = firebaseDatabase.getReference("weeklyReport")
                        .child(dataSnapshot.getKey());


                //=------------------------------

                databaseReference2.addChildEventListener(new ChildEventListener()
                {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot2, String s2)
                    {

                        String ss = dataSnapshot2.getValue().toString();
                        Log.v("value",ss);

                        if(dataSnapshot.getKey().equals("0"))
                        {
                            Log.v("value","hatt bc");
                            sun.add(ss);
                        }
                        else if(dataSnapshot.getKey().equals("1"))
                        {
                            mon.add(ss);
                        }
                        else if(dataSnapshot.getKey().equals("2"))
                        {
                            tues.add(ss);
                        }
                        else if(dataSnapshot.getKey().equals("3"))
                        {
                            wed.add(ss);
                        }
                        else if(dataSnapshot.getKey().equals("4"))
                        {
                            thurs.add(ss);
                        }
                        else if(dataSnapshot.getKey().equals("5"))
                        {
                            fri.add(ss);
                        }
                        else if(dataSnapshot.getKey().equals("6"))
                        {
                            sat.add(ss);
                        }



                        listView = (ExpandableListView)findViewById(R.id.lvExp);
                        initData();
                        listAdapter = new ExpandableListAdapter(getBaseContext(),listDataHeader,listHash);
                        listView.setAdapter(listAdapter);
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot2, String s2)
                    {
                        // i think empty the list here and refill it i.e. copy the above code
                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot2)
                    {


                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot2, String s2) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError2) {

                    }
                });




                listView = (ExpandableListView)findViewById(R.id.lvExp);
                initData();
                listAdapter = new ExpandableListAdapter(getBaseContext(),listDataHeader,listHash);
                listView.setAdapter(listAdapter);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s)
            {
                // i think empty the list here and refill it i.e. copy the above code
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot)
            {


            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    private void initData()
    {


        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();

        listDataHeader.add("Monday");
        listDataHeader.add("Tuesday");
        listDataHeader.add("Wednesday");
        listDataHeader.add("Thursday");
        listDataHeader.add("Friday");
        listDataHeader.add("Saturday");
        listDataHeader.add("Sunday");



        listHash.put(listDataHeader.get(0),sun);
        listHash.put(listDataHeader.get(1),mon);
        listHash.put(listDataHeader.get(2),tues);
        listHash.put(listDataHeader.get(3),wed);
        listHash.put(listDataHeader.get(4),thurs);
        listHash.put(listDataHeader.get(5),fri);
        listHash.put(listDataHeader.get(6),sat);
    }
}
