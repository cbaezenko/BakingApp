package com.example.baeza.bakingapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.baeza.bakingapp.ui.view.SelectRecipeActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class CheckRecipeSelectedTest {

    private IdlingResource mIdlingResource;

    @Rule
    public ActivityTestRule<SelectRecipeActivity> mActivityTestRule =
            new ActivityTestRule<>(SelectRecipeActivity.class);

    @Before
    public void registerIdlingResource() {
        mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();
        Espresso.registerIdlingResources(mIdlingResource);
    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }

    /*
    * After get the recipe data, click on the first item, with name 'Nutella Pie', then in the next
    * activity check if the ingredients and steps belongs to 'Nutella Pie'.
    * */
    @Test
    public void clickFavoriteButtonInItem_saveTheRightItemInFavorites() {
        onView(withRecyclerView(R.id.recyclerView).atPositionOnView(0, R.id.button_ingredient)).perform(click());
        onView(withId(R.id.button_ingredient)).check(matches(withText("Ingredients Nutella Pie")));
    }

    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }
}
