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
public class PersonalProfileFragmentTest {

    @Rule
    public ActivityScenarioRule<HomeActivity> activityRule =
            new ActivityScenarioRule<>(HomeActivity.class);

    @Test
    public void personalProfileFragment_UIElementsDisplayed() {
        // Navigate to PersonalProfileFragment
        onView(withId(R.id.personalProfileFragment)).perform(click());

        // Check if profile image is displayed
        onView(withId(R.id.profileImage)).check(matches(isDisplayed()));

        // Check if profile name is displayed
        onView(withId(R.id.profileName)).check(matches(isDisplayed()));

        // Check if profile description is displayed
        onView(withId(R.id.profileDescription)).check(matches(isDisplayed()));

        // Check if saved quests button is displayed
        onView(withId(R.id.savedQuestsButton)).check(matches(isDisplayed()));

        // Check if followers layout is displayed
        onView(withId(R.id.followersLayout)).check(matches(isDisplayed()));

        // Check if following layout is displayed
        onView(withId(R.id.followingLayout)).check(matches(isDisplayed()));
    }
}
