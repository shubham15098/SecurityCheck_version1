package com.example.shubzz.securitycheck_version1;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.v7.widget.RecyclerView;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class GuardsNameTest {

    @Rule
    public ActivityTestRule<GuardsName> activity = new ActivityTestRule<GuardsName>(GuardsName.class);

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testRecycle()
    {
        onView(withId(R.id.guard_name_recycler_view)).perform(RecyclerViewActions.scrollToPosition(30));
    }

    @After
    public void tearDown() throws Exception {
    }
}