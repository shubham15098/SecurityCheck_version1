package com.example.shubzz.securitycheck_version1;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AllGaurdsGeo  extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = MapsActivity.class.getSimpleName();
    private HashMap<String, Circle > mMarkers = new HashMap<>();
    private HashMap<String, Marker > mMarkers2 = new HashMap<>();
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
    ArrayList<String> badAreas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        areas = new ArrayList<>();
        areasSorted = new ArrayList<>();
        goodAreas=new ArrayList<>();
        badAreas=new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance();

        PositionStrings.add("Gate No-1");
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


        Latitude.put("Gate No-1","28.5470619");
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


        Longitude.put("Gate No-1","77.2723056");
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
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                areas.clear();
                areasSorted.clear();
                goodAreas.clear();
                Log.e("Count " ,""+snapshot.getChildrenCount());
                for (DataSnapshot uniqueKeySnapshot : snapshot.getChildren()) {
                    Area a = uniqueKeySnapshot.getValue(Area.class);
                    areas.add(a);

                    Log.e("print ho rha h?",a.getAreaName());
                }
                Log.v("areas", String.valueOf(areas.size()));
                int max;
                //add areas of 0 defaults

                for (int i=0;i<areas.size();i++)
                    if(Integer.valueOf(areas.get(i).getAreaDefaults())==0)
                        goodAreas.add(areas.get(i).getAreaName());
                Log.d("asd",goodAreas.toString());
                for (int i=0;i<areas.size();i++)
                    if(Integer.valueOf(areas.get(i).getAreaDefaults())>0)
                        badAreas.add(areas.get(i).getAreaName());
                Log.d("asd",badAreas.toString());
                setMarker();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        setContentView(R.layout.activity_all_gaurds_location);
       SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapAll);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMaxZoomPreference(21);

    }

    private void subscribeToUpdates() {
        Log.d("asd","gotsomething");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Actual");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d("asd","gotsomething");

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.d(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    private void setMarker() {


        for(String i : PositionStrings){
            for(String j : goodAreas){
                if(i.equals(j)){
            LatLng location = new LatLng( Double.parseDouble(Latitude.get(i)), Double.parseDouble(Longitude.get(i)));
            if (!mMarkers2.containsKey(i)&&!mMarkers.containsKey(i)) {
                mMarkers.put(i, mMap.addCircle(new CircleOptions()
                        .center(location)
                        .radius(10)
                        .strokeWidth(10)
                        .strokeColor(Color.GREEN)
                        .fillColor(Color.argb(128, 0, 255, 0))
                        .clickable(true)));
                mMarkers2.put(i, mMap.addMarker(new MarkerOptions().title(i).position(location)));
                Log.d("asdgood",j);
            }
            else {
                mMarkers2.get(i).setPosition(location);
                mMarkers.get(i).setCenter(location);
            }
            }
        }
            for(String j : badAreas){
                if(i.equals(j)){
                    LatLng location = new LatLng( Double.parseDouble(Latitude.get(i)), Double.parseDouble(Longitude.get(i)));
                    if (!mMarkers2.containsKey(i)&&!mMarkers.containsKey(i)) {
                        mMarkers.put(i, mMap.addCircle(new CircleOptions()
                                .center(location)
                                .radius(10)
                                .strokeWidth(10)
                                .strokeColor(Color.RED)
                                .fillColor(Color.argb(128, 255, 0, 0))
                                .clickable(true)));
                        mMarkers2.put(i, mMap.addMarker(new MarkerOptions().title(i).position(location)));
                        Log.d("asdbad",j);
                    }
                    else {
                        mMarkers2.get(i).setPosition(location);
                        mMarkers.get(i).remove();
                        mMarkers.put(i, mMap.addCircle(new CircleOptions()
                                .center(location)
                                .radius(10)
                                .strokeWidth(10)
                                .strokeColor(Color.RED)
                                .fillColor(Color.argb(128, 255, 0, 0))
                                .clickable(true)));
                    }
                }
            }
    }

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Circle marker : mMarkers.values()) {
            builder.include(marker.getCenter());
        }
        for (Marker marker : mMarkers2.values()) {
            builder.include(marker.getPosition());
        }

        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 300));
    }

    void getFirebaseData()
    {
        databaseReference = firebaseDatabase.getReference("Areas");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                areas.clear();
                areasSorted.clear();
                goodAreas.clear();
                Log.e("Count " ,""+snapshot.getChildrenCount());
                for (DataSnapshot uniqueKeySnapshot : snapshot.getChildren()) {
                    Area a = uniqueKeySnapshot.getValue(Area.class);
                    areas.add(a);

                    Log.e("print ho rha h?",a.getAreaName());
                }
                Log.v("areas", String.valueOf(areas.size()));
                int max;
                //add areas of 0 defaults

                for (int i=0;i<areas.size();i++)
                    if(Integer.valueOf(areas.get(i).getAreaDefaults())==0)
                        goodAreas.add(areas.get(i).getAreaName());
                Log.d("asd",goodAreas.toString());
                for (int i=0;i<areas.size();i++)
                    if(Integer.valueOf(areas.get(i).getAreaDefaults())>0)
                        badAreas.add(areas.get(i).getAreaName());
                Log.d("asd",badAreas.toString());
                setMarker();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
