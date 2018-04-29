package com.example.shubzz.securitycheck_version1;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

public class StarOfTheWeek extends AppCompatActivity {
    ArrayList<GuardFromFirebase> guards;
    ArrayList<GuardFromFirebase> guardsSorted;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ArrayList<String> goodGuards;
    ArrayList<String> goodPhotos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star_of_the_week);
        guards = new ArrayList<>();
        guardsSorted = new ArrayList<>();
        goodGuards = new ArrayList<>();
        goodPhotos= new ArrayList<>();
        getFirebaseData();
    }

    public void getFirebaseData()
    {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Gaurdstest").child("Numbers");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                guards.clear();
                guardsSorted.clear();
                goodGuards.clear();
                goodPhotos.clear();
                Log.e("Count " ,""+snapshot.getChildrenCount());
                for (DataSnapshot uniqueKeySnapshot : snapshot.getChildren()) {
                    GuardFromFirebase a = uniqueKeySnapshot.getValue(GuardFromFirebase.class);
                    guards.add(a);
                }
                Log.v("areas", String.valueOf(guards.size()));
                int max;
                //add guards of 0 defaults

                for (int i=0;i<guards.size();i++)
                    if(Integer.valueOf(guards.get(i).getDEFAULTS())==0)
                        goodGuards.add(guards.get(i).getName());

                //fill good photos array and set pager

                for(int i =0;i< goodGuards.size();i++)
                {
                    FirebaseDatabase.getInstance().getReference().child("profile pics").child(goodGuards.get(i)).
                            addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            int size =0;
                            String insert ="http://mlzsalwar.ac.in/images/404.jpg";
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                goodPhotos.add(snapshot.getValue().toString());
                                //Log.e("pic",dataSnapshot.getValue().toString());
                                size++;
                            }
                            if(size ==0) goodPhotos.add(insert);
                            Log.e("msg", String.valueOf(goodPhotos.size()));

                            if(goodPhotos.size()==goodGuards.size())
                            {
                                ViewPager mPager = (ViewPager) findViewById(R.id.pager);
                                mPager.setAdapter(new ImageSliderAdapter(StarOfTheWeek.this,goodPhotos ));
                                CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
                                indicator.setViewPager(mPager);

                                mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                    public void onPageScrollStateChanged(int state) {}
                                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                                        TextView name =(TextView) findViewById(R.id.star_name);
                                        name.setText(goodGuards.get(position));
                                    }

                                    public void onPageSelected(int position) {
                                        // Check if this is the page you want.
                                    }
                                });
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
