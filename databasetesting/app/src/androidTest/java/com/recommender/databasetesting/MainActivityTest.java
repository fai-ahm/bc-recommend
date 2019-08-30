package com.recommender.databasetesting;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> bActivityTestRule = new ActivityTestRule<>(MainActivity.class);
    private Instrumentation.ActivityMonitor monitor1 = getInstrumentation().addMonitor(GetButton.class.getName(), null, false);
    private Instrumentation.ActivityMonitor monitor2 = getInstrumentation().addMonitor(GiveButton.class.getName(), null, false);
    private Instrumentation.ActivityMonitor monitor3 = getInstrumentation().addMonitor(Profile.class.getName(), null, false);    private MainActivity tActivity = null;    @Before
    public void setUp() throws Exception {
        tActivity = bActivityTestRule.getActivity();
    }    //test checks for the existence of a button with a special word "GET"
    @Test
    public void testLaunch1() {
        Espresso.onView(withId(R.id.btn_start_get)).check(matches(ViewMatchers.withText("GET")));
    }    ///test checks for the existence of a button with a special word "GIVE"
    @Test
    public void testLaunch2() {
        Espresso.onView(withId(R.id.btn_start_give)).check(matches(ViewMatchers.withText("GIVE")));
    }    //test to check button functionality GET
    @Test
    public void testLaunchOfNextActivityByClick1() {
        assertNotNull(tActivity.findViewById(R.id.btn_start_get));
        Espresso.onView(withId(R.id.btn_start_get)).perform(click());
        Activity GetButton = getInstrumentation().waitForMonitorWithTimeout(monitor1, 5000);
        assertNotNull(GetButton);
        GetButton.finish();
    }    //test to check button functionality GIVE
    @Test
    public void testLaunchOfNextActivityByClick2() {
        assertNotNull(tActivity.findViewById(R.id.btn_start_give));
        Espresso.onView(withId(R.id.btn_start_give)).perform(click());
        Activity GiveButton = getInstrumentation().waitForMonitorWithTimeout(monitor2, 5000);
        assertNotNull(GiveButton);
        GiveButton.finish();
    }    //test to check button functionality Profile
    @Test
    public void testLaunchOfNextActivityByClick3() {
        assertNotNull(tActivity.findViewById(R.id.buttonProfile));
        Espresso.onView(withId(R.id.buttonProfile)).perform(click());
        Activity Profile = getInstrumentation().waitForMonitorWithTimeout(monitor3, 5000);
        assertNotNull(Profile);
        Profile.finish();
    }    @After
    public void tearDown() throws Exception {
        tActivity = null;
    }
}