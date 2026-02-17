package com.uni_project.questmaster.ui.home;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.uni_project.questmaster.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class HomeActivityTest {

    @Rule
    public ActivityScenarioRule<HomeActivity> activityRule =
            new ActivityScenarioRule<>(HomeActivity.class);

    @Test
    public void homeActivity_UIElementsDisplayed() {
        // Check if toolbar is displayed
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()));

        // Check if fragment container is displayed
        onView(withId(R.id.fragmentContainerView)).check(matches(isDisplayed()));

        // Check if bottom navigation is displayed
        onView(withId(R.id.bottomNavigation)).check(matches(isDisplayed()));
    }
}
