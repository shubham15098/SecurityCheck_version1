package com.example.shubzz.securitycheck_version1;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import android.content.Context;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import android.content.Context;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ResultCallback<Status>{

    public static int mflag2 = 0;

    ProgressDialog progressDialog;

    public Map<String, String> myMap = new HashMap<>();
    public Map<String, String> guardA = new HashMap<>();
    public Map<String, String> guardB = new HashMap<>();
    public Map<String, String> guardC = new HashMap<>();

    protected GoogleApiClient mGoogleApiClient;
    protected ArrayList<Geofence> mGeofenceList;
    private Button mAddGeofencesButton;

    public static String myLocation = "";

    public int flag = 0;
    private int time;
    private String guardName="";
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Calendar calobj = Calendar.getInstance();
    String location="";


    private BroadcastReceiver broadcastReceiver;
    @Override
    protected void onResume() {
        super.onResume();
        if(broadcastReceiver == null){
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                    Log.d("asd",""+intent.getExtras().get("coordinates"));

                }
            };
        }
        registerReceiver(broadcastReceiver,new IntentFilter("location_update"));
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(broadcastReceiver != null){
            unregisterReceiver(broadcastReceiver);
        }
    }
    private void enable_buttons() {

        Intent i =new Intent(getApplicationContext(),GPS_Service.class);
        startService(i);

    }

    private boolean runtime_permissions() {
        if(Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION},100);

            return true;
        }
        return false;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 100){
            if( grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                enable_buttons();
            }else {
                runtime_permissions();
            }
        }
    }




    public String getMacId()
    {
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (!mWifi.isConnected())
        {
            return null;
        }
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        Log.v("wifi",wifiInfo.getBSSID());
        return wifiInfo.getBSSID();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");

        //start gps service
        if(!runtime_permissions())
            enable_buttons();
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.show();
//        Button bun = (Button) findViewById(R.id.bun);
//        bun.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                Intent i = new Intent(MainActivity.this,TakeAttendance.class);
//                startActivity(i);
//            }
//        });

//        ImageView img2 = (ImageView) findViewById(R.id.imageView);
//        img2.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                if(flag == 0)
//                {
//                    Toast.makeText(getApplicationContext(),"Not Connected to a Guard Post",Toast.LENGTH_SHORT).show();
//                }
//                else if(flag == 1)
//                {
//                    // find the guard
//                    String s = new SimpleDateFormat("HHmmss").format(Calendar.getInstance().getTime());
//                    s = s.substring(0,2);
//                    time = Integer.parseInt(s);
//                    Log.v("fuck hour", String.valueOf(time));
//
//                    if(time >= 0 && time < 8)
//                    {
//                        Log.v("fuck","inside");
//                        guardName = guardC.get(location);
//                    }
//                    else if(time >= 8 && time < 16)
//                    {
//                        guardName = guardA.get(location);
//                    }
//                    else if(time >= 16 && time < 24)
//                    {
//                        guardName = guardB.get(location);
//                    }
//
//                    Log.v("fuck",guardName);
//
//                    Intent i = new Intent(MainActivity.this,TakeAttendance.class);
//                    i.putExtra("gaurdname",guardName);
//                    i.putExtra("location",location);
//                    startActivity(i);
//                }
//
//            }
//        });



        // ---------------------geofence-----------------

        // Empty list for storing geofences.
        mGeofenceList = new ArrayList<Geofence>();

        // Get the geofences used. Geofence data is hard coded in this sample.
        populateGeofenceList();



        // first we will built google api client
        // Kick off the request to build GoogleApiClient.
        buildGoogleApiClient();

        // ----------------------------------------------

        //make map of csv file
        readCSV();

        readFromFirebase();

        //https://stackoverflow.com/questions/29533934/correct-way-to-run-a-continuously-called-method-in-own-thread?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa

        new Thread(new Runnable()
        {
            TextView t = (TextView) findViewById(R.id.check);
            ImageView img = (ImageView) findViewById(R.id.imageView);
            TextView t2 = (TextView) findViewById(R.id.multiAutoCompleteTextView2);
            TextView t3 = (TextView) findViewById(R.id.multiAutoCompleteTextView);

            @Override
            public void run()
            {
                while(true)
                {
                    try
                    {
                        Thread.sleep(100);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            if(mflag2 == 1)
                            {
                                // into a geofence


                                flag = 1;
                                t.setText( myLocation);
                                t2.setText("CONNECTED");
                                t3.setText("GOOD JOB");
                                img.setImageResource(R.drawable.ellipse1);
                                timer();

                            }
                            else if(mflag2 == 0)
                            {
                                //
                                String xyz = getMacId();

                                if(xyz != null && !xyz.isEmpty())
                                {
                                    xyz = xyz.substring(0, xyz.length() - 1);

                                    location = myMap.get(xyz);


                                    if (myMap.containsKey(xyz))
                                    {
                                        //here i am connected
                                        flag = 1;
                                        t.setText(location);
                                        t2.setText("CONNECTED");
                                        t3.setText("GOOD JOB");
                                        img.setImageResource(R.drawable.ellipse1);

                                    }
                                    else
                                    {
                                        flag = 0;
                                        t.setText("No mapped location");
                                        t3.setText("Go to a GuardPost");
                                        t2.setText("DISCONNECTED");
                                        img.setImageResource(R.drawable.disconnected);
                                    }
                                }
                                else
                                {
                                    //this means he is not in the wifi zone

                                    flag = 0;
                                    t2.setText("DISCONNECTED");
                                    t3.setText("Go to a GuardPost");
                                    t.setText("NO Location");
                                    img.setImageResource(R.drawable.disconnected);
                                }
                            }



                        }
                    });

                }
            }
        }).start();


    }

    private void readFromFirebase()
    {

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
                    progressDialog.dismiss();
                }

                if(g.getPOST().equals("New acad(2nd Floor)"))
                {
                    Log.v("qwerty",g.getGAURD3());
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



    private void readCSV()
    {
        InputStream is = getResources().openRawResource(R.raw.mapping);

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );

        String line = "";

        try
        {
            while((line = reader.readLine()) != null)
            {
                String[] tokens = line.split(",");

                String t = tokens[4].substring(0, tokens[4].length() - 1);
                myMap.put(t,tokens[2]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public void timer()
    {
        new CountDownTimer(30000, 1000) {
            //30 sec

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                mflag2 = 0;
            }
        }.start();
    }
    public void addGeofencesButtonHandler(View view)
    {
        if(flag == 0)
        {


//            Toast.makeText(this,"Clicked",
//                    Toast.LENGTH_SHORT
//            ).show();
            // clicking this button is very imp
            // it will config everything, i guess in our code we will have to do this in main or something
            if (!mGoogleApiClient.isConnected())
            {
                Toast.makeText(this, getString(R.string.not_connected), Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                LocationServices.GeofencingApi.addGeofences(
                        mGoogleApiClient,
                        // The GeofenceRequest object.
                        getGeofencingRequest(),
                        // A pending intent that that is reused when calling removeGeofences(). This
                        // pending intent is used to generate an intent when a matched geofence
                        // transition is observed.
                        getGeofencePendingIntent()
                ).setResultCallback(this); // Result processed in onResult().
            } catch (SecurityException securityException) {
                // Catch exception generated if the app does not use ACCESS_FINE_LOCATION permission.
            }


            Toast.makeText(this,"Not Connected to a Guard Post",Toast.LENGTH_SHORT).show();
        }
        else if(flag == 1)
        {

//            Toast.makeText(this,"Clicked",
//                    Toast.LENGTH_SHORT
//            ).show();
            // clicking this button is very imp
            // it will config everything, i guess in our code we will have to do this in main or something
            if (!mGoogleApiClient.isConnected())
            {
                Toast.makeText(getApplicationContext(), getString(R.string.not_connected), Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                LocationServices.GeofencingApi.addGeofences(
                        mGoogleApiClient,
                        // The GeofenceRequest object.
                        getGeofencingRequest(),
                        // A pending intent that that is reused when calling removeGeofences(). This
                        // pending intent is used to generate an intent when a matched geofence
                        // transition is observed.
                        getGeofencePendingIntent()
                ).setResultCallback(this); // Result processed in onResult().
            } catch (SecurityException securityException) {
                // Catch exception generated if the app does not use ACCESS_FINE_LOCATION permission.
            }



            // sending intent to another activity



            // find the guard
            String s = new SimpleDateFormat("HHmmss").format(Calendar.getInstance().getTime());
            s = s.substring(0,2);
            time = Integer.parseInt(s);
            Log.v("fuck hour", String.valueOf(time));

            if(time >= 0 && time < 8)
            {
                Log.v("fuck","inside");
                guardName = guardC.get(location);
                Log.v("fuck",guardName);
            }
            else if(time >= 8 && time < 16)
            {
                guardName = guardA.get(location);
            }
            else if(time >= 16 && time < 24)
            {
                guardName = guardB.get(location);
            }

            //Log.v("fuck",guardName);



            Intent i = new Intent(MainActivity.this,TakeAttendance.class);
            i.putExtra("gaurdname",guardName);
            i.putExtra("location",location);
            startActivity(i);
        }
    }

    @Override
    protected void onStart()
    {
        //connect google api client with location services
        super.onStart();
        if (!mGoogleApiClient.isConnecting() || !mGoogleApiClient.isConnected())
        {
            mGoogleApiClient.connect();
        }
    }


    @Override
    protected void onStop()
    {
        super.onStop();
        if (mGoogleApiClient.isConnecting() || mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
            mflag2 = 0;
        }
    }


    public void onResult(Status status)
    {
        // this basically notifies when a geo fence is added
        // comment it i guess, else it every time give a toast message

        if (status.isSuccess()) {
//            Toast.makeText(
//                    this,
//                    "Geofences Added",
//                    Toast.LENGTH_SHORT
//            ).show();
        } else {
            // Get the status code for the error and log it using a user-friendly message.
            String errorMessage = GeofenceErrorMessages.getErrorString(this,
                    status.getStatusCode());
        }
    }


    /**
     * Runs when a GoogleApiClient object successfully connects.
     */
    @Override
    public void onConnected(Bundle connectionHint)
    {

    }

    @Override
    public void onConnectionFailed(ConnectionResult result)
    {
        // Do something with result.getErrorCode());
    }

    @Override
    public void onConnectionSuspended(int cause)
    {
        mGoogleApiClient.connect();
    }



    protected synchronized void buildGoogleApiClient()
    {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    public void populateGeofenceList()
    {
        // this function reads our map in constant class and add all the locations
        // set by us into a geofence list called mGeofenceList
        // at the end of the function we will have list of all geofence

        for (Map.Entry<String, LatLng> entry : Constants.BAY_AREA_LANDMARKS.entrySet())
        {

            mGeofenceList.add(new Geofence.Builder()
                    // Set the request ID of the geofence. This is a string to identify this
                    // geofence.
                    .setRequestId(entry.getKey())

                    // Set the circular region of this geofence.
                    .setCircularRegion(
                            entry.getValue().latitude,
                            entry.getValue().longitude,
                            Constants.GEOFENCE_RADIUS_IN_METERS
                    )

                    // Set the expiration duration of the geofence. This geofence gets automatically
                    // removed after this period of time.
                    .setExpirationDuration(Constants.GEOFENCE_EXPIRATION_IN_MILLISECONDS)

                    // Set the transition types of interest. Alerts are only generated for these
                    // transition. We track entry and exit transitions in this sample.
                    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
                            Geofence.GEOFENCE_TRANSITION_EXIT)

                    // Create the geofence.
                    .build());
        }
    }

    private GeofencingRequest getGeofencingRequest()
    {

        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(mGeofenceList);
        return builder.build();
    }


    private PendingIntent getGeofencePendingIntent()
    {
        // here we are sending intent to our GeoFenceTransitionsIntentService class we made
        // then this class will do the work like showing notification

        Intent intent = new Intent(this, GeoFenceTransitionsIntentService.class);
        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when calling addgeoFences()
        return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}