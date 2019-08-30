package com.recommender.databasetesting;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;import static org.junit.Assert.*;public class GiveButtonTest {    @Rule
public ActivityTestRule<GiveButton> gActivityTestRule = new ActivityTestRule<>(GiveButton.class);
    private Instrumentation.ActivityMonitor monitor1 = getInstrumentation().addMonitor(GiveMovieSuggestion.class.getName(), null, false); //GiveMovieGenre
    private Instrumentation.ActivityMonitor monitor2 = getInstrumentation().addMonitor(GiveBookSuggestion.class.getName(), null, false);
    private GiveButton giveButton = null;    @Before
    public void setUp() throws Exception {
        giveButton = gActivityTestRule.getActivity();
    }    //test button functionality to launch GiveMovieGenre page!
    @Test
    public void testButtonLaunch1() {
        assertNotNull(giveButton.findViewById(R.id.btn_giveCategory_movie));
        Espresso.onView(withId(R.id.btn_giveCategory_movie)).perform(click());
        Activity GiveMovieGenre = getInstrumentation().waitForMonitor(monitor1);
        assertNotNull(GiveMovieGenre);
        GiveMovieGenre.finish();
    }    //test button functionality to launch GiveBookGenre page!
    @Test
    public void testButtonLaunch2() {
        assertNotNull(giveButton.findViewById(R.id.btn_giveCategory_book));
        Espresso.onView(withId(R.id.btn_giveCategory_book)).perform(click());
        Activity GiveBookGenre = getInstrumentation().waitForMonitor(monitor2);
        assertNotNull(GiveBookGenre);
        GiveBookGenre.finish();
    }    @After
    public void tearDown() throws Exception {
        giveButton = null;
    }
}