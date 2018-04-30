package com.example.shubzz.securitycheck_version1;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AllGaurdsGeoLocator  extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = MapsActivity.class.getSimpleName();
    private HashMap<String, Circle > mMarkers = new HashMap<>();
    private HashMap<String, Marker> mMarkers2 = new HashMap<>();
    private GoogleMap mMap;
    Circle circle;
    public Map<String, String> Latitude = new HashMap<>();
    public Map<String, String> Longitude = new HashMap<>();
    ArrayList<String> PositionStrings = new ArrayList<String>();
    ArrayList<Area> areas;
    ArrayList<Area> areasSorted;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    AreasActivity.recyler_adapter_areas adapter;
    ArrayList<String> goodAreas;
    public Map<String, String> guardA = new HashMap<>();
    public Map<String, String> guardB = new HashMap<>();
    public Map<String, String> guardC = new HashMap<>();
    ArrayList<String> badAreas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        readFromFirebase();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_gaurds_location);
        areas = new ArrayList<>();
        areasSorted = new ArrayList<>();
        goodAreas=new ArrayList<>();
        badAreas=new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance();

        PositionStrings.add("Gate No -1");
        PositionStrings.add("Gate No-3");
        PositionStrings.add("Gate No-4");
        PositionStrings.add("Gate No-6");
        PositionStrings.add("Boys Hostel");
        PositionStrings.add("Girls Hostel");
        PositionStrings.add("Dining Hall");
        PositionStrings.add("Dining Hall");
        PositionStrings.add("Faculty");
        PositionStrings.add("Library");
        PositionStrings.add("Library");
        PositionStrings.add("New acad(Ground)");
        PositionStrings.add("Ground Floor");
        PositionStrings.add("Back Side");
        PositionStrings.add("Phd Hostel");
        PositionStrings.add("Faculty new");


        Latitude.put("Gate No -1","28.5470619");
        Latitude.put("Gate No-3","28.5448674");
        Latitude.put("Gate No-4","28.545044");
        Latitude.put("Gate No-6","28.544584");
        Latitude.put("Boys Hostel","28.5474018");
        Latitude.put("Girls Hostel","28.5465173");
        Latitude.put("Dining Hall","28.5464677");
        Latitude.put("Faculty","28.544114");
        Latitude.put("Library","28.5465267");
        Latitude.put("New acad(Ground)","28.544015");
        Latitude.put("Ground Floor","28.5465173");
        Latitude.put("Back Side","28.544372");
        Latitude.put("Phd Hostel","28.547758");
        Latitude.put("Faculty new","28.544232");


        Longitude.put("Gate No -1","77.2723056");
        Longitude.put("Gate No-3","77.2711564");
        Longitude.put("Gate No-4","77.270038");
        Longitude.put("Gate No-6","77.273047");
        Longitude.put("Boys Hostel","77.2717317");
        Longitude.put("Girls Hostel","77.2713468");
        Longitude.put("Dining Hall","77.2733446");
        Longitude.put("Faculty","77.2686064");
        Longitude.put("Library","77.2733446");
        Longitude.put("New acad(Ground)","77.271508");
        Longitude.put("Ground Floor","77.2713468");
        Longitude.put("Back Side"," 77.272953");
        Longitude.put("Phd Hostel","77.274041");
        Longitude.put("Faculty new","77.270151");
        databaseReference = firebaseDatabase.getReference("Areas");


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapAll);
        mapFragment.getMapAsync(this);


    }

    private void readFromFirebase()
    {
        ProgressDialog progressDialog = new ProgressDialog(AllGaurdsGeoLocator.this);
        progressDialog.show();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("timetable").child("gaurdPostings");

        databaseReference.addChildEventListener(new ChildEventListener()

        {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot2, String s2)
            {
                mappingGuard g = dataSnapshot2.getValue(mappingGuard.class);
                Log.v("fuck you",g.getGAURD1());
                guardA.put(g.getPOST(),g.getGAURD1());
                guardB.put(g.getPOST(),g.getGaurd2());
                guardC.put(g.getPOST(),g.getGAURD3());
                if(guardA.size()>=25){
                    setMarker();
                }

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

        progressDialog.dismiss();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMaxZoomPreference(21);

    }



    private void setMarker() {

        String s = new SimpleDateFormat("HHmmss").format(Calendar.getInstance().getTime());
        s = s.substring(0,2);
        int time = Integer.parseInt(s);
        String guardName = null;

        for(String i : PositionStrings){
           //get hashmap values of gaurd on duty that time
            // plot them
            Log.d("asd",""+guardC.keySet());
            if(time >= 0 && time < 8)
            {
                Log.v("fuck","inside");
                guardName = guardC.get(i);
            }
            else if(time >= 8 && time < 16)
            {
                guardName = guardA.get(i);
            }
            else if(time >= 16 && time < 24)
            {
                guardName = guardB.get(i);
            }
              Log.d("asd","a"+guardName);

            LatLng location = new LatLng( Double.parseDouble(Latitude.get(i)), Double.parseDouble(Longitude.get(i)));
            if (!mMarkers2.containsKey(i)) {

                mMarkers2.put(i, mMap.addMarker(new MarkerOptions().title(guardName+"("+i+")").position(location)));
                Log.d("asdbad","g");
            }
            else {
                mMarkers2.get(i).setPosition(location);

            }

        }

        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        for (Marker marker : mMarkers2.values()) {
            builder.include(marker.getPosition());
        }

      mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 300));
    }


}
