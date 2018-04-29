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

import java.util.HashMap;

public class AllGaurdsGeo  extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = MapsActivity.class.getSimpleName();
    private HashMap<String, Circle > mMarkers = new HashMap<>();
    private HashMap<String, Marker > mMarkers2 = new HashMap<>();
    private GoogleMap mMap;
    Circle circle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_gaurds_location);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapAll);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        circle = mMap.addCircle(new CircleOptions()
                .center(new LatLng(1, 1))
                .radius(1000)
                .strokeWidth(10)
                .strokeColor(Color.GREEN)
                .fillColor(Color.argb(128, 255, 0, 0))
                .clickable(true));

        mMap.setMaxZoomPreference(21);
        subscribeToUpdates();
    }

    private void subscribeToUpdates() {
        Log.d("asd","gotsomething");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Actual");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d("asd","gotsomething");
                setMarker(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                setMarker(dataSnapshot);
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

    private void setMarker(DataSnapshot dataSnapshot) {

        String key = dataSnapshot.getKey();
        HashMap<String, Object> value = (HashMap<String, Object>) dataSnapshot.getValue();
        Log.d("asdhash",value.toString());
        double lat = Double.parseDouble(value.get("latitude").toString());
        double lng = Double.parseDouble(value.get("longitude").toString());
        LatLng location = new LatLng(lat, lng);
        if (!mMarkers.containsKey(key)) {
            mMarkers.put(key, mMap.addCircle(new CircleOptions()
                    .center(location)
                    .radius(1000)
                    .strokeWidth(10)
                    .strokeColor(Color.GREEN)
                    .fillColor(Color.argb(128, 255, 0, 0))
                    .clickable(true)));
            mMarkers2.put(key, mMap.addMarker(new MarkerOptions().title(key).position(location)));
        } else {

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



}
