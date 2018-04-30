package com.example.shubzz.securitycheck_version1;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GuardsName extends AppCompatActivity {

    RecyclerView mRecyclerView;
    ArrayList<GuardFromFirebase> guards;
    ArrayList<GuardFromFirebase> guardsSorted;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    recyler_adapter_guards_name adapter;
    ArrayList<String> goodGuards;

  
    

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guards_name);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_guards);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ii = new Intent(GuardsName.this,AllGaurdsGeoLocator.class);
                startActivity(ii);
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.guard_name_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        
        guards = new ArrayList<>();
        guardsSorted = new ArrayList<>();
        goodGuards = new ArrayList<>();
        adapter = new GuardsName.recyler_adapter_guards_name(guardsSorted);
        firebaseDatabase = FirebaseDatabase.getInstance();
        mRecyclerView.setAdapter(adapter);
   
        getFirebaseData();
        
    }

    void getFirebaseData()
    {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Gaurdstest").child("Numbers");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                guards.clear();
                guardsSorted.clear();
                goodGuards.clear();
                Log.e("Count " ,""+snapshot.getChildrenCount());
                for (DataSnapshot uniqueKeySnapshot : snapshot.getChildren()) {
                    GuardFromFirebase a = uniqueKeySnapshot.getValue(GuardFromFirebase.class);
                    guards.add(a);
                    mRecyclerView.setAdapter(adapter);
                    Log.e("print ho rha h?",a.getName());
                }
                Log.v("areas", String.valueOf(guards.size()));
                int max;
                //add guards of 0 defaults

                for (int i=0;i<guards.size();i++)
                    if(Integer.valueOf(guards.get(i).getDEFAULTS())==0)
                        goodGuards.add(guards.get(i).getName());


                while(guardsSorted.size()!=guards.size())
                {
                    GuardFromFirebase temp = new GuardFromFirebase();
                    int pos=0;
                    max =-1;
                    for( int i=0;i<guards.size();i++)
                    {

                        if (Integer.valueOf(guards.get(i).getDEFAULTS()) >= max)
                        {
                            temp = guards.get(i);
                            max = Integer.valueOf(guards.get(i).getDEFAULTS());
                            pos=i;
                        }
                    }
                    guardsSorted.add(temp);
                    Log.e("guards Sorted", temp.getDEFAULTS());
                    guards.get(pos).setDEFAULTS("-1");
                    Log.e("size sorted", String.valueOf(guardsSorted.size()));
                    //mRecyclerView.setAdapter(adapter);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public class recyler_adapter_guards_name extends RecyclerView.Adapter<GuardsName.recyler_adapter_guards_name.MyViewHolder> {
        ArrayList<GuardFromFirebase> guards;

        public recyler_adapter_guards_name(ArrayList<GuardFromFirebase> guards) {
            this.guards = guards;
        }


        @Override
        public GuardsName.recyler_adapter_guards_name.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.guards_name_recycler_content, parent, false);
            return new GuardsName.recyler_adapter_guards_name.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(GuardsName.recyler_adapter_guards_name.MyViewHolder holder, int position) {
            holder.guardName.setText(guards.get(position).getName());
            Log.e("good Guards", String.valueOf(goodGuards.size()));
            if(goodGuards.contains(guards.get(position).getName()))
            {
                holder.itemView.setBackgroundColor(Color.GREEN);
                holder.guardName.setBackgroundColor(Color.GREEN);

            }
            else
            {
                holder.itemView.setBackgroundColor(Color.RED);
                holder.guardName.setBackgroundColor(Color.RED);
            }


        }

        @Override
        public int getItemCount() {
            return guards.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
            TextView guardName;

            public MyViewHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(this);
                guardName = (TextView) itemView.findViewById(R.id.guard_name);


              
            }

            @Override
            public void onClick(View view) {
                Log.v("Clicked",guards.get(getAdapterPosition()).getName());



                Intent i = new Intent (getApplicationContext(),ImageSlider.class);
                i.putExtra("NAME",guards.get(getAdapterPosition()).getName());
                String Color ="Red";
                if(goodGuards.contains(guards.get(getAdapterPosition()).getName()))
                    Color="Green";
                i.putExtra("COLOR",Color);
                i.putExtra("DEFAULTS",guards.get(getAdapterPosition()).getDEFAULTS() );
                ArrayList<String> remarks = new ArrayList<>();
//                i.putStringArrayListExtra("REMARKS",teams);
                startActivity(i);

            }
        }
    }
}
