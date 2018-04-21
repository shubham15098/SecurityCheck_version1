package com.example.shubzz.securitycheck_version1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AreasActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    ArrayList<String> areas;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    recyler_adapter_areas adapter;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_areas);
        mRecyclerView = (RecyclerView) findViewById(R.id.area_name_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        areas = new ArrayList<>();
        adapter = new AreasActivity.recyler_adapter_areas(areas);
        firebaseDatabase = FirebaseDatabase.getInstance();
        mRecyclerView.setAdapter(adapter);

        getFirebaseData();
    }

    void getFirebaseData()
    {

    }

    public class recyler_adapter_areas extends RecyclerView.Adapter<AreasActivity.recyler_adapter_areas.MyViewHolder> {
        ArrayList<String> areas;

        public recyler_adapter_areas(ArrayList<String> areas) {
            this.areas = areas;
        }


        @Override
        public AreasActivity.recyler_adapter_areas.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.areas_recycler_content, parent, false);
            return new AreasActivity.recyler_adapter_areas.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(AreasActivity.recyler_adapter_areas.MyViewHolder holder, int position) {
            holder.areaName.setText(areas.get(position));
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
