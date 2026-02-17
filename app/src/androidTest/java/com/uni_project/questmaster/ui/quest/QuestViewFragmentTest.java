package com.uni_project.questmaster.ui.quest;

import android.os.Bundle;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.uni_project.questmaster.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class QuestViewFragmentTest {

    @Before
    public void setUp() {
        // Launch the fragment with a dummy questId
        Bundle args = new Bundle();
        args.putString("questId", "dummyQuestId");

        FragmentScenario.launchInContainer(QuestViewFragment.class, args);
    }

    @Test
    public void questViewFragment_UIElementsDisplayed() {
        // Check if quest name TextView is displayed
        onView(withId(R.id.text_view_quest_name)).check(matches(isDisplayed()));

        // Check if quest description TextView is displayed
        onView(withId(R.id.text_view_quest_description)).check(matches(isDisplayed()));

        // Check if star button is displayed
        onView(withId(R.id.button_star)).check(matches(isDisplayed()));

        // Check if share button is displayed
        onView(withId(R.id.button_share)).check(matches(isDisplayed()));

        // Check if comments RecyclerView is displayed
        onView(withId(R.id.recycler_view_comments)).check(matches(isDisplayed()));

        // Check if comment input layout is displayed
        onView(withId(R.id.text_input_layout_comment)).check(matches(isDisplayed()));

        // Check if post comment button is displayed
        onView(withId(R.id.button_post_comment)).check(matches(isDisplayed()));
    }
}
