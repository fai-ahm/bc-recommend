package com.recommender.databasetesting;

import static org.junit.Assert.*;

import android.support.test.rule.ActivityTestRule;import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;import static org.junit.Assert.*;
public class DatabaseTest {
    @Rule
public ActivityTestRule<Database> gActivityTestRule = new ActivityTestRule<>(Database.class);
    private Database database = null;
    @Before
    public void setUp() throws Exception {
        database = gActivityTestRule.getActivity();
    }    @Test
    public void dataBaseEditText() {
        assertNotNull(database.findViewById(R.id.name_field));
    }    @After
    public void tearDown() throws Exception {
    }
}