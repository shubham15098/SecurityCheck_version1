package com.example.shubzz.securitycheck_version1;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

public class ImageSlider extends AppCompatActivity {
    private static ViewPager mPager;
    private static int currentPage = 0;
   String s1="https://images.pexels.com/photos/70497/pexels-photo-70497.jpeg?auto=compress&cs=tinysrgb&h=350";
    String s2="https://i.pinimg.com/originals/e2/a6/57/e2a657e2476d73760d32f63429b0d597.jpg";

    private ArrayList<String> XMENArray = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_test);
            init();
        }
        private void init() {

                XMENArray.add(s1);
                XMENArray.add(s2);
            mPager = (ViewPager) findViewById(R.id.pager);
            mPager.setAdapter(new ImageSliderAdapter(ImageSlider.this,XMENArray));
            CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
            indicator.setViewPager(mPager);

            // Auto start of viewpager

        }

    }
