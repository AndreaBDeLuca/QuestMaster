package com.uni_project.questmaster.ui.home.fragments;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.uni_project.questmaster.R;
import com.uni_project.questmaster.ui.home.HomeActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class SettingsFragmentTest {

    @Rule
    public ActivityScenarioRule<HomeActivity> activityRule =
            new ActivityScenarioRule<>(HomeActivity.class);

    @Test
    public void settingsFragment_UIElementsDisplayed() {
        // Navigate to SettingsFragment
        onView(withId(R.id.settingsFragment)).perform(click());

        // Check if dark mode switch is displayed
        onView(withId(R.id.switch_dark_mode)).check(matches(isDisplayed()));

        // Check if language setting is displayed
        onView(withId(R.id.setting_language)).check(matches(isDisplayed()));

        // Check if location setting is displayed
        onView(withId(R.id.setting_location)).check(matches(isDisplayed()));

        // Check if new quests notification switch is displayed
        onView(withId(R.id.switch_notifications_new_quests)).check(matches(isDisplayed()));

        // Check if comments notification switch is displayed
        onView(withId(R.id.switch_notifications_comments)).check(matches(isDisplayed()));

        // Check if clear search history is displayed
        onView(withId(R.id.setting_clear_search_history)).check(matches(isDisplayed()));

        // Check if app info is displayed
        onView(withId(R.id.setting_app_info)).check(matches(isDisplayed()));
    }
}
