package com.uni_project.questmaster.ui.home.fragments;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.uni_project.questmaster.R;
import com.uni_project.questmaster.ui.home.HomeActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class FeedFragmentTest {

    @Rule
    public ActivityScenarioRule<HomeActivity> activityRule =
            new ActivityScenarioRule<>(HomeActivity.class);

    @Test
    public void feedFragment_UIElementsDisplayed() {
        // Check if RecyclerView is displayed
        onView(withId(R.id.recycler_view_feed)).check(matches(isDisplayed()));

        // Check if SearchView is displayed
        onView(withId(R.id.search_view)).check(matches(isDisplayed()));
    }
}
