package com.example.baeza.bakingapp;

import android.content.Context;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.baeza.bakingapp.IdlingResource.EspressoIdlingResource;
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
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class FavoriteRecipeSaveTest {

    private static final String SELECTED_RECIPE = "Nutella Pie";

    @Rule
    public ActivityTestRule<SelectRecipeActivity> mActivityTestRule =
            new ActivityTestRule<>(SelectRecipeActivity.class);

    @Before
    public void registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getIdlingResource());
    }

    @After
    public void unregisterIdlingResource() {
            IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getIdlingResource());
    }

    @Test
    public void clickOnRecipe_goToDetails_checkAsFavoriteAndCheck() {
        onView(withId(R.id.recyclerView))
                        .perform(RecyclerViewActions
                        .actionOnItemAtPosition(0, click()));

        Context context = mActivityTestRule.getActivity().getApplicationContext();
        FavoriteRecipe favoriteRecipe = new FavoriteRecipe(context);
        String savedRecipeName = favoriteRecipe.getRecipeNameFromPref();

        if(savedRecipeName.equals(SELECTED_RECIPE)){
            //if is already as Favorite, test is positive
            onView(withText(savedRecipeName)).check(matches(withText(SELECTED_RECIPE)));

        }else{
            //if Nutella Pie was not in favoites, we add it.
            onView(withId(R.id.switch_favorite)).perform(click());
            savedRecipeName = favoriteRecipe.getRecipeNameFromPref();
            onView(withText(savedRecipeName)).check(matches(withText(SELECTED_RECIPE)));
        }
    }
}
