package com.example.shubzz.securitycheck_version1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AreasActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    ArrayList<Area> areas;
    ArrayList<Area> areasSorted;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    recyler_adapter_areas adapter;
    ArrayList<String> goodAreas;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_areas);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_area);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ii = new Intent(AreasActivity.this,AllGaurdsPointsGeo.class);
                startActivity(ii);
            }
        });



        mRecyclerView = (RecyclerView) findViewById(R.id.area_name_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        areas = new ArrayList<>();
        areasSorted = new ArrayList<>();
        goodAreas=new ArrayList<>();
        adapter = new AreasActivity.recyler_adapter_areas(areasSorted);
        firebaseDatabase = FirebaseDatabase.getInstance();
        mRecyclerView.setAdapter(adapter);

        getFirebaseData();
    }

    void getFirebaseData()
    {
        final ProgressDialog progressDialog=new ProgressDialog(this);
    progressDialog.show();
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
                    mRecyclerView.setAdapter(adapter);
                    Log.e("print ho rha h?",a.getAreaName());
                }
                Log.v("areas", String.valueOf(areas.size()));
                int max;
                //add areas of 0 defaults

                for (int i=0;i<areas.size();i++)
                    if(Integer.valueOf(areas.get(i).getAreaDefaults())==0)
                        goodAreas.add(areas.get(i).getAreaName());


                while(areasSorted.size()!=areas.size())
                {
                    Area temp = new Area();
                    int pos=0;
                    max =-1;
                    for(int i=0;i<areas.size();i++)
                    {

                        if (Integer.valueOf(areas.get(i).getAreaDefaults()) >= max)
                        {
                            temp = areas.get(i);
                            max = Integer.valueOf(areas.get(i).getAreaDefaults());
                            pos=i;
                        }
                    }
                    areasSorted.add(temp);
                    Log.e("Areas Sorted", temp.getAreaDefaults());
                    areas.get(pos).setAreaDefaults("-1");

//                    mRecyclerView.setAdapter(adapter);
                }
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public class recyler_adapter_areas extends RecyclerView.Adapter<AreasActivity.recyler_adapter_areas.MyViewHolder> {
        ArrayList<Area> areas;

        public recyler_adapter_areas(ArrayList<Area> areas) {
            this.areas = areas;
        }


        @Override
        public AreasActivity.recyler_adapter_areas.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.areas_recycler_content, parent, false);
            return new AreasActivity.recyler_adapter_areas.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(AreasActivity.recyler_adapter_areas.MyViewHolder holder, int position) {
            holder.areaName.setText(areas.get(position).getAreaName());
            Log.e("goodareas", String.valueOf(goodAreas.size()));
            if(goodAreas.contains(areas.get(position).getAreaName()))
            {
                holder.itemView.setBackgroundColor(Color.GREEN);
                holder.areaName.setBackgroundColor(Color.GREEN);

            }
            else
            {
                holder.itemView.setBackgroundColor(Color.RED);
                holder.areaName.setBackgroundColor(Color.RED);
            }
        }

        @Override
        public int getItemCount() {
            return areas.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
            TextView areaName;

            public MyViewHolder(View itemView) {
                super(itemView);
                areaName = (TextView) itemView.findViewById(R.id.area_name);



            }

            @Override
            public void onClick(View view) {

            }
        }
    }
}
