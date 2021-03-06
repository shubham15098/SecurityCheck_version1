package com.example.shubzz.securitycheck_version1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVWriter;

public class Home_Manager extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{
ArrayList<GuardFromFirebase> guards;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__manager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        guards = new ArrayList<>();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exportToCsv();
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // view pager -----------------------------

        // Find the view pager that will allow the user to swipe between fragments
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        // Create an adapter that knows which fragment should be shown on each page
        SimpleFragmentPagerAdapter adapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager());

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);

        //tab
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);


    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home__manager, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void exportToCsv()
    {


        FirebaseDatabase.getInstance().getReference("Gaurdstest").child("Numbers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                guards.clear();
                Log.e("Count " ,""+snapshot.getChildrenCount());
                for (DataSnapshot uniqueKeySnapshot : snapshot.getChildren()) {
                    GuardFromFirebase a = uniqueKeySnapshot.getValue(GuardFromFirebase.class);
                    guards.add(a);
                }

                Log.e("print", String.valueOf(guards.size()));

                    Log.e("print2", String.valueOf(guards.size()));
                    String baseDir = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
                    String fileName = "guards.csv";
                    String filePath = baseDir + File.separator + fileName;
                    File f = new File(filePath);
                    CSVWriter writer = null;

                    if (f.exists() && !f.isDirectory()) {
                        try {
                            FileWriter mFileWriter = new FileWriter(filePath, false);
                            writer = new CSVWriter(mFileWriter);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    } else {
                        try {
                            writer = new CSVWriter(new FileWriter(filePath));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    List<String[]> data = new ArrayList<>();
                    data.add(new String[]{"Name", "Number", "Deaults"});
                    for (GuardFromFirebase user : guards) {
                        data.add(new String[]{user.getName(), user.getNumber(), user.getDEFAULTS()});
                    }
                    try {

                        writer.writeAll(data);
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Intent i = new Intent();
                    i.setAction(android.content.Intent.ACTION_VIEW);
                    i.setDataAndType(Uri.fromFile(f), "text/csv");
                    startActivity(i);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
}
