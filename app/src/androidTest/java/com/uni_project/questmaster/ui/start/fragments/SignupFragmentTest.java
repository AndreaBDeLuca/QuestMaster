package com.uni_project.questmaster.ui.start.fragments;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.uni_project.questmaster.R;
import com.uni_project.questmaster.ui.start.StartActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class SignupFragmentTest {

    @Rule
    public ActivityScenarioRule<StartActivity> activityRule =
            new ActivityScenarioRule<>(StartActivity.class);

    @Test
    public void signupScreen_UIElementsDisplayed() {
        // Navigate from Login to Signup
        onView(withId(R.id.signup_button)).perform(click());

        // Check if username input is displayed
        onView(withId(R.id.textInputUsername)).check(matches(isDisplayed()));

        // Check if email input is displayed
        onView(withId(R.id.textInputEmail)).check(matches(isDisplayed()));

        // Check if password input is displayed
        onView(withId(R.id.textInputPassword)).check(matches(isDisplayed()));

        // Check if confirm password input is displayed
        onView(withId(R.id.textInputConfirmPassword)).check(matches(isDisplayed()));

        // Check if signup button is displayed
        onView(withId(R.id.signup_button)).check(matches(isDisplayed()));

        // Check if google signup button is displayed
        onView(withId(R.id.google_signup_button)).check(matches(isDisplayed()));
    }
}
