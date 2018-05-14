package com.example.baeza.bakingapp;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.baeza.bakingapp.ui.view.MainContentActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

//import static android.support.test.espresso.Espresso.onView;
//import static android.support.test.espresso.action.ViewActions.click;
//import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class RecyclerViewTest {
    @Rule
    ActivityTestRule<MainContentActivity> mActivityTestRule
            = new ActivityTestRule<>(MainContentActivity.class);

    @Test
    public void clickRecyclerItem_ChangeContainerFragment() {
//        onView(withId(R.id.recyclerView_steps)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }
}
