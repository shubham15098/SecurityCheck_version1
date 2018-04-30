package com.example.shubzz.securitycheck_version1;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


public class Fragment_Home extends Fragment {



    public Fragment_Home() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_fragment__home, container, false);

//        ImageButton img = (ImageButton) rootView.findViewById(R.id.areasIcon);
//        img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v)
//            {
//                Intent i = new Intent(getActivity(),AreasActivity.class);
//                startActivity(i);
//            }
//        });

        ImageButton areas=(ImageButton)rootView.findViewById(R.id.areasIcon);
        ImageButton gaurds=(ImageButton)rootView.findViewById(R.id.guardsIcon);
        ImageButton supervisor=(ImageButton)rootView.findViewById(R.id.supervisorIcon);
        ImageButton star=(ImageButton)rootView.findViewById(R.id.callIcon);
        ImageButton graphs=(ImageButton)rootView.findViewById(R.id.supervisorIcon2);
        ImageButton weeklyreport=(ImageButton)rootView.findViewById(R.id.weeklyIcon2);

        areas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),AreasActivity.class);
                startActivity(i);
            }});
        gaurds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),GuardsName.class);
                startActivity(i);
            }});
        supervisor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),SupervisorWalk.class);
                startActivity(i);
            }});
        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),StarOfTheWeek.class);
                startActivity(i);
            }});
        graphs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),Graphs.class);
                startActivity(i);
            }});
        weeklyreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),WeeklyReport.class);
                startActivity(i);
            }});

        return rootView;
    }


}
