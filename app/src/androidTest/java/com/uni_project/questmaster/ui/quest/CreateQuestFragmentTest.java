package com.uni_project.questmaster.ui.quest;

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
public class CreateQuestFragmentTest {

    @Rule
    public ActivityScenarioRule<HomeActivity> activityRule =
            new ActivityScenarioRule<>(HomeActivity.class);

    @Test
    public void createQuestFragment_UIElementsDisplayed() {
        // Navigate to CreateQuestFragment
        onView(withId(R.id.createQuestFragment)).perform(click());

        // Check if quest name input is displayed
        onView(withId(R.id.edit_text_quest_name)).check(matches(isDisplayed()));

        // Check if quest description input is displayed
        onView(withId(R.id.edit_text_quest_description)).check(matches(isDisplayed()));

        // Check if ppq input is displayed
        onView(withId(R.id.edit_text_ppq)).check(matches(isDisplayed()));

        // Check if upload image button is displayed
        onView(withId(R.id.button_upload_image)).check(matches(isDisplayed()));

        // Check if save quest button is displayed
        onView(withId(R.id.button_save_quest)).check(matches(isDisplayed()));

        // Check if map options radio group is displayed
        onView(withId(R.id.map_options_radiogroup)).check(matches(isDisplayed()));
    }
}
