package com.recommender.databasetesting;

import static org.junit.Assert.*;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;import static org.junit.Assert.*;public class SignInTest {


    @Rule
public ActivityTestRule <SignIn> pActivityTestRule = new ActivityTestRule<>(SignIn.class);
    private Instrumentation.ActivityMonitor monitor1 = getInstrumentation().addMonitor(MainActivity.class.getName(), null, false);
    private SignIn cActivity = null;
    @Before
    public void setUp() throws Exception{
        cActivity = pActivityTestRule.getActivity();
    }

    @Test
    public void testSignButton() {
        assertNotNull(cActivity.findViewById(R.id.sign_in_button));
        Espresso.onView(withId(R.id.sign_in_button)).perform(click());
    }

    @Test
    public void testSignOutButton() {
        assertNotNull(cActivity.findViewById(R.id.sign_out_button));
        Espresso.onView(withId(R.id.sign_out_button)).perform(click());
    }

    @Test
    public void testButtonNext() {
        assertNotNull(cActivity.findViewById(R.id.next_Button));
        Espresso.onView(withId(R.id.next_Button)).perform(click());
        Activity MainActivity = getInstrumentation().waitForMonitor(monitor1);
        assertNotNull(MainActivity);
        MainActivity.finish();

    }    @After
    public void tearDown() throws Exception {
        cActivity = null;
    }
}