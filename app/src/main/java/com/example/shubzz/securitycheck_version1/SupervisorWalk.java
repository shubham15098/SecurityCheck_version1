package com.example.shubzz.securitycheck_version1;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SupervisorWalk extends AppCompatActivity {
    private List<Integer> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MoviesAdapter mAdapter;
    DatabaseReference Database3;
    ArrayList <Integer> myArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supervisor_walk);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new MoviesAdapter(movieList,getApplicationContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        prepareMovieData();
    }
    private void prepareMovieData() {
        // Movie movie = new Movie("Mad Max: Fury Road", "Action & Adventure", "2015");
        readFromFirebase();



        //movie = new Movie("Inside Out", "Animation, Kids & Family", "2015");

        Log.d("asd",movieList.toString());
        mAdapter.notifyDataSetChanged();
    }
    public void readFromFirebase()
    {movieList.clear();
        Database3 = FirebaseDatabase.getInstance().getReference("RoundCounter");

        Database3.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s)
            {


                walkClass w = dataSnapshot.getValue(walkClass.class);
                Log.d("asd", Integer.toString(w.getCounter()));
                movieList.add(w.getWalk());
                mAdapter.notifyDataSetChanged();



            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
class MoviesAdapter extends RecyclerView.Adapter<com.example.shubzz.securitycheck_version1.MoviesAdapter.MyViewHolder> {

    private List<Integer> moviesList;
    Context context;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView tick[]=new ImageView[4];

        public MyViewHolder(View view) {
            super(view);
            tick[0] = (ImageView) view.findViewById(R.id.tick1);
            tick[1] = (ImageView) view.findViewById(R.id.tick2);
            tick[2] = (ImageView) view.findViewById(R.id.tick3);
            tick[3]=(ImageView)view.findViewById(R.id.tick4);
        }
    }


    public MoviesAdapter(List<Integer> moviesList,Context context) {
        this.moviesList = moviesList;
        this.context=context;
    }

    @Override
    public com.example.shubzz.securitycheck_version1.MoviesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_walk, parent, false);

        return new com.example.shubzz.securitycheck_version1.MoviesAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final com.example.shubzz.securitycheck_version1.MoviesAdapter.MyViewHolder holder, int position) {
        int a  = moviesList.get(position);
        final int temp= 3;
        for(int i = 0;i<temp-a;i++) {
            final int finalI = i;
            //holder.tick[i].setImageDrawable(context.getResources().getDrawable(R.drawable.tick));
            Glide.with(context)
                    .asBitmap()
                    .load(R.drawable.tick)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            holder.tick[finalI].setImageBitmap(resource);
                        }
                    });
        }
        for(int i = 0;i<a;i++){
            final int finalI = i;
            //holder.tick[temp-i].setImageDrawable(context.getResources().getDrawable(R.drawable.tick));
            Glide.with(context)
                    .asBitmap()
                    .load(R.drawable.ic_launcher)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            holder.tick[temp-finalI].setImageBitmap(resource);
                        }
                    });
        }

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}