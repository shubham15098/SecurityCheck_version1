package com.example.shubzz.securitycheck_version1;

import com.android21buttons.fragmenttestrule.FragmentTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

public class Fragment_HomeTest {

    @Rule
    public FragmentTestRule<?, Fragment_Home> fragmentTestRule =
            FragmentTestRule.create(Fragment_Home.class);

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testing()
    {
        onView(withId(R.id.Areas_tv)).check(matches(withText("Areas")));
        onView(withId(R.id.guards_tv)).check(matches(withText("Guards")));
        onView(withId(R.id.supervisortv)).check(matches(withText("Supervisor")));
        onView(withId(R.id.startv)).check(matches(withText("StarOfWeek")));
        onView(withId(R.id.graphtv)).check(matches(withText("Graphs")));
        onView(withId(R.id.wrtv)).check(matches(withText("Weekly Report")));

    }


    @After
    public void tearDown() throws Exception {
    }
}