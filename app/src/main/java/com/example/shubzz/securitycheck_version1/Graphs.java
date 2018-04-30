package com.example.shubzz.securitycheck_version1;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Graphs extends AppCompatActivity {

    ArrayList<Area> Areas;
    ArrayList<String> goodAreas;
    ArrayList<GuardFromFirebase> Guards;
    ArrayList<String> goodGuards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphs);
        Areas = new ArrayList<>();
        goodAreas = new ArrayList<>();
        Guards=new ArrayList<>();
        goodGuards = new ArrayList<>();

        BarChart bar = (BarChart) findViewById(R.id.barchart);
        ArrayList<BarEntry> Barentry = new ArrayList<>();
        Barentry.add(new BarEntry(62.35f, 0));
        Barentry.add(new BarEntry(7.26f, 1));
        Barentry.add(new BarEntry(30.3f, 2));

        BarDataSet dataSet3 = new BarDataSet(Barentry, "Defaults");
        ArrayList<String> labels = new ArrayList<>();
        labels.add("Attendance");
        labels.add("Uniform");
        labels.add("Sleeping");

        BarData data3 = new BarData(labels, dataSet3);
        data3.setGroupSpace(0.5f);
        dataSet3.setBarSpacePercent(50f);
        ArrayList<Integer> colors2 = new ArrayList<>();
        colors2.add(Color.parseColor("#E0115F"));
        dataSet3.setColors(colors2);
        bar.setData(data3);
       // bar.getXAxis().setEnabled(true);
//        bar.getYAxis().setEnabled(false);
        bar.getAxisLeft().setDrawAxisLine(false);
        bar.getAxisRight().setDrawAxisLine(false);
        bar.getAxisRight().setEnabled(false);
        bar.getXAxis().setDrawLabels(true);
//        bar.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xVals));

        final PieChart guards= (PieChart) findViewById(R.id.guardspiechart) ;
        final PieChart areas= (PieChart) findViewById(R.id.areaspiechart) ;
        guards.setUsePercentValues(true);
        areas.setUsePercentValues(true);


        FirebaseDatabase.getInstance().getReference("Areas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                Areas.clear();
                goodAreas.clear();
                for (DataSnapshot uniqueKeySnapshot : snapshot.getChildren()) {
                    Area a = uniqueKeySnapshot.getValue(Area.class);
                    Areas.add(a);
                }
                for (int i = 0; i < Areas.size(); i++)
                    if (Integer.valueOf(Areas.get(i).getAreaDefaults()) == 0)
                        goodAreas.add(Areas.get(i).getAreaName());

                FirebaseDatabase.getInstance().getReference("Gaurdstest").child("Numbers")
                        .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        Guards.clear();
                        goodGuards.clear();
                        Log.e("Count " ,""+snapshot.getChildrenCount());
                        for (DataSnapshot uniqueKeySnapshot : snapshot.getChildren()) {
                            GuardFromFirebase a = uniqueKeySnapshot.getValue(GuardFromFirebase.class);
                            Guards.add(a);
                        }
                        for (int i=0;i<Guards.size();i++)
                            if(Integer.valueOf(Guards.get(i).getDEFAULTS())==0)
                                goodGuards.add(Guards.get(i).getName());


                Log.e("size", String.valueOf(Areas.size()));
                Log.e("size", String.valueOf(Guards.size()));



                    ArrayList<Entry> yvaluesG = new ArrayList<Entry>();
                    yvaluesG.add(new Entry((Guards.size() - goodGuards.size()) * 100 / Guards.size(), 0));
                    yvaluesG.add(new Entry((goodGuards.size() * 100) / Guards.size(), 1));
                    PieDataSet dataSet = new PieDataSet(yvaluesG, "Guards");
                    ArrayList<String> xValsG = new ArrayList<String>();
                    xValsG.add("Defaulters");
                    xValsG.add("Superstars");
                    PieData data = new PieData(xValsG, dataSet);
                    data.setValueFormatter(new PercentFormatter());
                    ArrayList<Integer> colors = new ArrayList<>();
                    colors.add(Color.parseColor("#FF4081"));
                    colors.add(Color.parseColor("#4FC3F7"));
                    dataSet.setColors(colors);
                    guards.setData(data);
                    guards.setDrawHoleEnabled(false);

                    ArrayList<Entry> yvaluesA = new ArrayList<Entry>();
                    yvaluesA.add(new Entry((Areas.size() - goodAreas.size()) * 100 / Areas.size(), 0));
                    yvaluesA.add(new Entry((goodAreas.size() * 100) / Areas.size(), 1));

                    PieDataSet dataSet2 = new PieDataSet(yvaluesA, "areas");
                    ArrayList<String> xValsA = new ArrayList<String>();
                    xValsA.add("Red zones");
                    xValsA.add("Green zones");
                    PieData data2 = new PieData(xValsA, dataSet2);
                    dataSet2.setColors(colors);
                    data2.setValueFormatter(new PercentFormatter());
                    areas.setData(data2);
                    areas.setDrawHoleEnabled(false);
                    guards.invalidate();
                    areas.invalidate();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Log.e("size", String.valueOf(Areas.size()));
        Log.e("size", String.valueOf(Guards.size()));

    }


}

