package com.example.baeza.bakingapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
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

//        onView(withRecyclerView(R.id.recyclerView).atPositionOnView(1, R.id.imageButton_favorite)).perform(click());

        FavoriteRecipe favoriteRecipe = new FavoriteRecipe(mActivityTestRule.getActivity().getApplicationContext());
        final String favoriteRecipeString = favoriteRecipe.getRecipeNameFromPref();

        //Check in console if the recipe was actually save.
        Timber.d("saved recipe" + favoriteRecipeString);

        onView(withRecyclerView(R.id.recyclerView)
                .atPositionOnView(1, R.id.card_title))
                .check(matches(withText(favoriteRecipeString.trim())));
    }

    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }
}
