package com.example.baeza.bakingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.baeza.bakingapp.ui.utility.FavoriteRecipe;
import com.example.baeza.bakingapp.ui.view.SelectRecipeActivity;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class FavoriteRecipeSaveTest {

    @Rule
    public ActivityTestRule<SelectRecipeActivity> mActivityTestRule =
            new ActivityTestRule<>(SelectRecipeActivity.class);

    @Test
    public void clickFavoriteButtonInItem_saveTheRightItemInFavorites(){
     //Scroll to position to be matched (0) with title 'Nutella Pie' and click on it to save as Favorite
         onView(withId(R.id.recyclerView))
     .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

         //Get the actual favorite value
        FavoriteRecipe favoriteRecipe = new FavoriteRecipe(mActivityTestRule.getActivity().getApplicationContext());
         String favoriteRecipeString = favoriteRecipe.getRecipeNameFromPref();

         //check if the value in position 0, Nutella Pie, is actually save on the FavoriteRecipe.
         onView(withText("Nutella Pie")).check(matches(withText(favoriteRecipeString)));
    }
}
