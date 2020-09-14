package com.wipro.musicworld;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ArtistListFragmentTest {
    @Rule
    public ActivityTestRule<MainActivity> activityRule
            = new ActivityTestRule<>(MainActivity.class);


    @Test
    public void emptySearchUiTest() {

        Espresso.onView(ViewMatchers.withId(R.id.message_view)).check(ViewAssertions.matches(ViewMatchers.withText(R.string.message)));
    }

    @Test
    public void searchQueryCheck() {

        String name="gopi";
        // Context of the app under test.
        Espresso.onView(ViewMatchers.withId(R.id.search)).perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.search_src_text)).perform(ViewActions.typeTextIntoFocusedView(name));
       // Espresso.onView(ViewMatchers.withId(R.id.search_src_text))
           //     .perform(ViewActions.pressImeActionButton());
        Espresso.onView(ViewMatchers.withId(R.id.search_src_text))
                .check(ViewAssertions.matches(ViewMatchers.withText(name)));

    }



}
