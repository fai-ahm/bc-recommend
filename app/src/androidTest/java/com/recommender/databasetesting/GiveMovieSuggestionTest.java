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
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;public class GiveMovieSuggestionTest {
    @Rule
    public ActivityTestRule<GiveMovieSuggestion> movieSuggestionActivityTestRule = new ActivityTestRule<>(GiveMovieSuggestion.class);
    // private Instrumentation.ActivityMonitor monitor1 = getInstrumentation().addMonitor(GiveMovieSuggestionReccomend.class.getName(), null, false);
    private GiveMovieSuggestion suggestion = null;
    private String textFromUser = "Team3";    @Before
    public void setUp() throws Exception {
        suggestion = movieSuggestionActivityTestRule.getActivity();
    }    // text specific TextViews
    @Test
    public void textMovie() {
        Espresso.onView(withId(R.id.txt_getGenre_title)).check(matches(ViewMatchers.withText("MOVIE")));
        Espresso.onView(withId(R.id.txt_getGenre_subTitle2)).check(matches(ViewMatchers.withText("SEARCH")));
    }    // test text input, closeKeyboard and clickable the button
    @Test
    public void editTheTextFromUser() {
        Espresso.onView(withId(R.id.srch_giveGenre_searchbar)).perform(typeText(textFromUser));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.btnSearch)).perform(click());
    }    // test go to another page    @Test
    public void testButtonNextPagexxx() {
        assertNotNull(suggestion.findViewById(R.id.btnSearch));
        Espresso.onView(withId(R.id.btnSearch)).check(matches(isEnabled()));
    }    @After
    public void tearDown() throws Exception {
        suggestion = null;
    }
}