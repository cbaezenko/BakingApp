package com.example.baeza.bakingapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.baeza.bakingapp.ui.utility.FavoriteRecipe;
import com.example.baeza.bakingapp.ui.view.SelectRecipeActivity;

import static android.support.test.espresso.assertion.ViewAssertions.matches;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import timber.log.Timber;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class FavoriteRecipeSaveTest {


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

    @Test
    public void clickFavoriteButtonInItem_saveTheRightItemInFavorites() {

        //all this should happen after  the retrofit answer with the data to fill the recycler view.

        //Scroll to position to the first recyclerView item (0)
        onView(withId(R.id.recyclerView)).
                perform(RecyclerViewActions.scrollToPosition(0));

        //once we are in the position 0, click on the imageButton_favorite
        onView(withId(R.id.recyclerView)).
                perform(RecyclerViewActions.
                        actionOnItem((withId(R.id.imageButton_favorite)), click()));

        //Get the actual favorite value
        FavoriteRecipe favoriteRecipe = new FavoriteRecipe(mActivityTestRule.getActivity().getApplicationContext());
        String favoriteRecipeString = favoriteRecipe.getRecipeNameFromPref();

        //Check in console if the recipe was actually save.
        Timber.d("favorite recipe " + favoriteRecipeString);

        //check if the value in position 0, Nutella Pie, is actually save on the FavoriteRecipe.
        onView(withText("Nutella Pie")).check(matches(withText(favoriteRecipeString)));
    }
}
