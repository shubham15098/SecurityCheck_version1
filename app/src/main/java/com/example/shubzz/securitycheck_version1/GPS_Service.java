package com.example.shubzz.securitycheck_version1;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.firebase.geofire.GeoFire;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GPS_Service extends Service {

    private LocationListener listener;
    private LocationManager locationManager;
    DatabaseReference ref ;
    GeoFire geoFire;
    FirebaseUser user;// = FirebaseAuth.getInstance().getCurrentUser();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        user = FirebaseAuth.getInstance().getCurrentUser();

        ref= FirebaseDatabase.getInstance().getReference().child("location").child("testingcordinates");
        geoFire = new GeoFire(ref);
        Log.d("asd","gpsservice start");
        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Intent i = new Intent("location_update");
                Log.d("asd",location.getLatitude()+""+location.getLatitude());
                ref.child("latitude").setValue(location.getLatitude());
                ref.child("longitude").setValue(location.getLongitude());

                i.putExtra("coordinates", location.getLongitude() + " " + location.getLatitude());
                sendBroadcast(i);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
                Log.d("asd","status");
            }

            @Override
            public void onProviderEnabled(String s) {
                Log.d("asd","provider enabled");
            }

            @Override
            public void onProviderDisabled(String s) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        };

        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

        //noinspection MissingPermission
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        Log.d("asd","listener registered");
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0  , 0, listener);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(locationManager != null){
            //noinspection MissingPermission
            locationManager.removeUpdates(listener);
        }
    }
}
