package com.example.shubzz.securitycheck_version1;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GuardsName extends AppCompatActivity {

    RecyclerView mRecyclerView;
    ArrayList<Guard> guards;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    recyler_adapter_guards_name adapter;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guards_name);
        mRecyclerView = (RecyclerView) findViewById(R.id.guard_name_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        
        guards = new ArrayList<>();
        adapter = new GuardsName.recyler_adapter_guards_name(guards);
        firebaseDatabase = FirebaseDatabase.getInstance();
        mRecyclerView.setAdapter(adapter);

        getFirebaseData();
    }

    void getFirebaseData()
    {
        databaseReference = firebaseDatabase.getReference("GuardsContacts").child("Numbers");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                guards.clear();
                for (DataSnapshot uniqueKeySnapshot : snapshot.getChildren()) {
                    Guard g = new Guard("","");
                        String key = (String) snapshot.getKey();
                        String value = (String) snapshot.getValue();
                        if (key.equals("name")) g.setName(value);
                        if (key.equals("number")) g.setNumber(value);
                        guards.add(g);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public class recyler_adapter_guards_name extends RecyclerView.Adapter<GuardsName.recyler_adapter_guards_name.MyViewHolder> {
        ArrayList<Guard> guards;

        public recyler_adapter_guards_name(ArrayList<Guard> guards) {
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
        }

        @Override
        public int getItemCount() {
            return guards.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
            TextView guardName;

            public MyViewHolder(View itemView) {
                super(itemView);
                guardName = (TextView) itemView.findViewById(R.id.guard_name);

              
            }

            @Override
            public void onClick(View view) {
                Intent i = new Intent (getApplicationContext(),GuardProfileActivity.class);
                i.putExtra("Position",getAdapterPosition());
                startActivity(i);
            }
        }
    }
}
