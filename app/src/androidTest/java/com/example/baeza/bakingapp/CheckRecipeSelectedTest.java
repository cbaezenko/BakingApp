package com.example.baeza.bakingapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.example.baeza.bakingapp.ui.utility.FavoriteRecipe;
import com.example.baeza.bakingapp.ui.view.SelectRecipeActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
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

    /*
    * After get the recipe data, click on the first item, with name 'Nutella Pie', then in the next
    * activity check if the ingredients and steps belongs to 'Nutella Pie'.
    * */

    @Test
    public void checkRecyclerViewIsDisplayed(){
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()));
    }

    @Test
    public void clickOnRecipe_checkCorrectRecipeIsShown() {
//        FavoriteRecipe favoriteRecipe = new FavoriteRecipe(mActivityTestRule.getActivity().getApplicationContext());
//        int favoritePosition = favoriteRecipe.getRecipeIdFromPref();
//        if(favoritePosition > 3){
//            favoritePosition = 0;
//        }

        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
//        onView(withId(R.id.switch_favorite)).perform(click());
        onView(withId(R.id.button_ingredient)).check(matches(withText("Ingredients Nutella Pie")));



//        onView(withRecyclerView(R.id.recyclerView).atPositionOnView(0, R.id.button_ingredient)).perform(click());
//        onView(withId(R.id.button_ingredient)).check(matches(withText("Ingredients Nutella Pie")));
    }

    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }
}
