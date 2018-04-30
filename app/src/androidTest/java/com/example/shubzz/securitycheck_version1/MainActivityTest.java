package com.example.shubzz.securitycheck_version1;

import android.app.Instrumentation;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> act_rule = new ActivityTestRule<>(MainActivity.class);

    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(TakeAttendance.class.getName(),null,false);


    @Before
    public void setUp() throws Exception {
    }


    @Test
    public void connected()
    {
        // assertNotNull(act_rule.getActivity().findViewById(R.id.connected_status)
        onView(withId(R.id.multiAutoCompleteTextView2)).check(matches(withText("CONNECTED")));

        onView(withId(R.id.multiAutoCompleteTextView)).check(matches(withText("GOOD JOB")));

        onView(withId(R.id.imageView)).perform(click());

       // onView(withId(R.id.radioButton));
        // Activity attendance= getInstrumentation().waitForMonitorWithTimeout(monitor,5000);
        //assertNotNull(attendance);
    }

    @Test
    public void notconnected()
    {
        onView(withId(R.id.multiAutoCompleteTextView2)).check(matches(withText("DISCONNECTED")));
    }
    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getMacId() {
    }

    @Test
    public void onConnected() {
    }

    @Test
    public void onConnectionFailed() {
    }

    @Test
    public void onConnectionSuspended() {
    }
}