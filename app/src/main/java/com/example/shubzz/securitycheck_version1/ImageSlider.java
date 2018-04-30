package com.example.shubzz.securitycheck_version1;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

public class ImageSlider extends AppCompatActivity {
    private static ViewPager mPager;
    private DatabaseReference ref;
    private static int currentPage = 0;
    String s1="http://mlzsalwar.ac.in/images/404.jpg";
    String s2="https://i.pinimg.com/originals/e2/a6/57/e2a657e2476d73760d32f63429b0d597.jpg";
    ImageView profilephoto;
    DatabaseReference databaseReference2;

    ArrayList<String> ans = new ArrayList<>();

    private ArrayList<String> XMENArray = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_test);

        Intent i = getIntent();
        String guard= i.getStringExtra("NAME");
        Log.v("name recv", guard);
        profilephoto = (ImageView) findViewById(R.id.profilephoto);
        setProfilePhoto(guard);
        String def = i.getStringExtra("DEFAULTS");

        ArrayList<String> remarks;
//        remarks.clear();
        remarks= getRemarks(guard);

//        ArrayList<String> remarks = i.getStringArrayListExtra("REMARKS");
            TextView name = (TextView) findViewById(R.id.name);
            name.setText(guard.replace(","," "));
            TextView defaults = (TextView)findViewById(R.id.defaults);
            defaults.setText(def);
            ListView l = (ListView) findViewById(R.id.listview);

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_list_item_1,
                    remarks );

            l.setAdapter(arrayAdapter);
        final String color = i.getStringExtra("COLOR");
        Log.v("recv color", color);

        ref=  FirebaseDatabase.getInstance().getReference();
        FirebaseDatabase.getInstance().getReference().child("pics").child(guard).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {


                            XMENArray.add(snapshot.getValue().toString());

                        Log.v("print",snapshot.getValue().toString());

                    }
                    if(color.equals("Green"))
                    {
                        if(XMENArray.size()==0)XMENArray.add(s1);
                    }
                    mPager = (ViewPager) findViewById(R.id.pager);
                    mPager.setAdapter(new ImageSliderAdapter(ImageSlider.this,XMENArray));
                    CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
                    indicator.setViewPager(mPager);

                }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        }
    public ArrayList<String> getRemarks(final String guard)
    {

//        ans.clear();
        ans.add("(Scroll to see all defaults)");
        FirebaseDatabase.getInstance().getReference("Gaurdstest").child("Numbers").
                addChildEventListener(new ChildEventListener()

    {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s)
        {
            GuardFromFirebase g = dataSnapshot.getValue(GuardFromFirebase.class);
            String nameTemp = g.getName();
            Log.e("s",nameTemp);
            if(nameTemp.equals(guard))
            {
                // here i have found the id of my guard
                databaseReference2 = FirebaseDatabase.getInstance().getReference("Gaurdstest").child("Numbers")
                        .child(dataSnapshot.getKey()).child("remarks");

                databaseReference2.addChildEventListener(new ChildEventListener()

                {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot2, String s2)
                    {
                        String s = dataSnapshot2.getValue().toString();
                        Log.e("s",s);
                        ans.add(s);

                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot2, String s2)
                    {


                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot2) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot2, String s2) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError2)
                    {

                    }
                });
            }
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s)
        {
            // i think empty the list here and refill it i.e. copy the above code
            //getFirebaseData();

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError)
        {

        }
    });
        Log.e("ans", String.valueOf(ans.size()));

        return ans;
    }

    public void setProfilePhoto(String guard)
    {

        FirebaseDatabase.getInstance().getReference().child("profile pics").child(guard).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String url=s1;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                url=snapshot.getValue().toString();

                }

                Picasso.get().load(url).fit().centerCrop().into(profilephoto);


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    }
