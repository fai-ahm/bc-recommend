package com.recommender.databasetesting;


import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;


@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class UnitTestRobo {
    GiveBookSuggestionReccomend activity;
    @Before
    public void setUp() throws Exception {

        activity = Robolectric.setupActivity(GiveBookSuggestionReccomend.class);
    }
    @Test
    public void shouldWork() throws Exception{
        assertNotNull(activity);
    }
    @Test
    public void continueShouldLaunchMineActivity() {

        TextView book = (TextView) activity.findViewById(R.id.txt_giveSuggestionBook_title);//txt_giveSuggestionBook_title
        assertNotNull("TextView could not be found", book);
        assertTrue("TextView contains incorrect text",
                "BOOK".equals(book.getText().toString()));    }
}
