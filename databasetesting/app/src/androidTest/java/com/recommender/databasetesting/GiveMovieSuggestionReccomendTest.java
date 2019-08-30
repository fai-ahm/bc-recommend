package com.recommender.databasetesting;

import static org.junit.Assert.*;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;
public class GiveMovieSuggestionReccomendTest {
    @Rule
public ActivityTestRule<GiveMovieSuggestionReccomend> cActivityTestRule = new ActivityTestRule<>(GiveMovieSuggestionReccomend.class);
    // private Instrumentation.ActivityMonitor monitor1 = getInstrumentation().addMonitor(GetButton.class.getName(), null, false);
    private GiveMovieSuggestionReccomend pActivity = null;
    @Before
    public void setUp() throws Exception {
        pActivity = cActivityTestRule.getActivity();
    }    @Test
    public void testLaunchOfNextActivityByClick1() {
        assertNotNull(pActivity.findViewById(R.id.buttonRecommend));
        ///Espresso.onView(withId(R.id.buttonRecommend)).perform(click());
        Espresso.onView(withId(R.id.buttonRecommend)).check(matches(isEnabled()));
    }    @After
    public void tearDown() throws Exception {
        pActivity = null;
    }
}