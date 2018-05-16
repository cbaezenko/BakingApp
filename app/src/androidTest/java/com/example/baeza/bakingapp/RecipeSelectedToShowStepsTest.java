//package com.example.baeza.bakingapp;
//
//import android.app.Activity;
//import android.app.Instrumentation;
//import static android.support.test.espresso.Espresso.onView;
//
//import android.support.test.espresso.intent.rule.IntentsTestRule;
//import android.support.test.espresso.matcher.ViewMatchers;
//import android.support.test.runner.AndroidJUnit4;
//
//import com.example.baeza.bakingapp.ui.view.MainContentActivity;
//import com.example.baeza.bakingapp.ui.view.SelectIngredientStepFragment;
//import com.example.baeza.bakingapp.ui.view.SelectRecipeActivity;
//
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import android.support.test.espresso.contrib.RecyclerViewActions;
//
//import static android.support.test.espresso.action.ViewActions.click;
//import static android.support.test.espresso.assertion.ViewAssertions.matches;
//import static android.support.test.espresso.intent.Intents.intending;
//import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
//import static android.support.test.espresso.matcher.ViewMatchers.withText;
//import static org.hamcrest.core.IsNot.not;
//
//@RunWith(AndroidJUnit4.class)
//public class RecipeSelectedToShowStepsTest {
//
//    @Rule
//    public IntentsTestRule <MainContentActivity> mIntentsTestRule =
//            new IntentsTestRule<>(MainContentActivity.class);
//
//    @Before
//    public void stubAllExternalIntents(){
//        intending(not(isInternal())).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));
//    }
//
//    @Test
//    public void clickOnShowRecipe_ShowRecipeNameInToolbar() {
//
////        onView(ViewMatchers.withId(R.id.recyclerView))
////                .perform(RecyclerViewActions.actionOnItemAtPosition(R.id.button_ingredient, click()));
//
//        onView(ViewMatchers.withId(R.id.recyclerView_steps))
//                .perform(RecyclerViewActions.actionOnItemAtPosition((0), click()));
//
//        onView(ViewMatchers.withId(R.id.toolbar)).check(matches(withText("Nutella Pie")));
//    }
//}