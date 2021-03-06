package com.example.baeza.bakingapp;

import android.support.test.espresso.IdlingRegistry;
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
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class GetRecipeListTest {

    @Rule
    public ActivityTestRule<SelectRecipeActivity> mActivityTestRule =
            new ActivityTestRule<>(SelectRecipeActivity.class);

    @Before
    public void registerIdlingResource(){
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getIdlingResource());
    }

    @Test
    public void checkProgressIsGoneWithData(){
        onView(withId(R.id.progressBar))
                        .check(matches(ViewMatchers
                        .withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
    }

    @After
    public void unregisterIdlingResource(){
            IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getIdlingResource());
    }
}
