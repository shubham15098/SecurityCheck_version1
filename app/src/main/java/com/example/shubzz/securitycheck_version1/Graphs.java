package com.example.shubzz.securitycheck_version1;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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

import java.util.ArrayList;

public class Graphs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphs);
        PieChart guards = (PieChart) findViewById(R.id.guardspiechart);
        PieChart areas = (PieChart) findViewById(R.id.areaspiechart);
        BarChart bar = (BarChart) findViewById(R.id.barchart);
        guards.setUsePercentValues(true);
        areas.setUsePercentValues(true);

        ArrayList<Entry> yvaluesG = new ArrayList<Entry>();
        yvaluesG.add(new Entry(8f, 0));
        yvaluesG.add(new Entry(15f, 1));
        PieDataSet dataSet = new PieDataSet(yvaluesG, "Guards");
        ArrayList<String> xValsG = new ArrayList<String>();
        xValsG.add("Defaulters");
        xValsG.add("Superstars");
        PieData data = new PieData(xValsG, dataSet);
        data.setValueFormatter(new PercentFormatter());
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.RED);
        colors.add(Color.GREEN);
        dataSet.setColors(colors);
        guards.setData(data);
        guards.setDrawHoleEnabled(false);



        ArrayList<Entry> yvaluesA = new ArrayList<Entry>();
        yvaluesA.add(new Entry(4f, 0));
        yvaluesA.add(new Entry(10f, 1));
        PieDataSet dataSet2 = new PieDataSet(yvaluesA, "areas");
        ArrayList<String> xValsA = new ArrayList<String>();
        xValsA.add("Red zones");
        xValsA.add("Green zones");
        PieData data2 = new PieData(xValsA, dataSet2);
        dataSet2.setColors(colors);
        data2.setValueFormatter(new PercentFormatter());
        areas.setData(data2);
        areas.setDrawHoleEnabled(false);

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
        dataSet3.setColors(ColorTemplate.COLORFUL_COLORS);
        bar.setData(data3);
        bar.getXAxis().setEnabled(false);
//        bar.getYAxis().setEnabled(false);
        bar.getAxisLeft().setDrawAxisLine(false);
        bar.getAxisRight().setDrawAxisLine(false);
        bar.getAxisRight().setEnabled(false);
        bar.getXAxis().setDrawLabels(true);
//        bar.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xVals));
    }
}
