package com.example.baeza.bakingapp;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.baeza.bakingapp.IdlingResource.EspressoIdlingResource;
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
@LargeTest
public class CheckRecipeSelectedTest {

    @Rule
    public ActivityTestRule<SelectRecipeActivity> mActivityTestRule =
            new ActivityTestRule<>(SelectRecipeActivity.class);

    @Before
    public void registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getIdlingResource());
    }

    /*
    * After get the recipe data, click on the first item, with name 'Nutella Pie', then in the next
    * activity check if the ingredients and steps belongs to 'Nutella Pie'.
    * */

    @Test
    public void clickOnRecipe_checkCorrectRecipeIsShown() {
        onView(ViewMatchers.withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.button_ingredient)).check(matches(withText("Ingredients Nutella Pie")));
    }

    @After
    public void unregisterIdlingResource() {
            IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getIdlingResource());
    }
}
