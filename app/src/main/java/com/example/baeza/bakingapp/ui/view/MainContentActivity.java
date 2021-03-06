package com.example.baeza.bakingapp.ui.view;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.baeza.bakingapp.R;
import com.example.baeza.bakingapp.ui.data.Ingredient;
import com.example.baeza.bakingapp.ui.data.Recipe;
import com.example.baeza.bakingapp.ui.data.Step;
import com.example.baeza.bakingapp.ui.manager.OnFragmentSelectedListener;
import com.example.baeza.bakingapp.ui.manager.OnIngredientListener;
import com.example.baeza.bakingapp.ui.manager.OnMediaCurrentPosition;
import com.example.baeza.bakingapp.ui.utility.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.baeza.bakingapp.ui.utility.Constants.MEDIA_CURRENT_POSITION;
import static com.example.baeza.bakingapp.ui.utility.Constants.MEDIA_CURRENT_STATE;

public class MainContentActivity extends AppCompatActivity
        implements OnFragmentSelectedListener, OnIngredientListener, OnMediaCurrentPosition, SelectIngredientStepFragment.OnFavoriteSignal{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.coordinator)
    CoordinatorLayout mCoordinatorLayout;

    private Recipe recipe;
    private List<Step> stepList;
    private List<Ingredient> mIngredientList;
    private int recipePosition = 0;

    private boolean mTwoPane;
    private long mediaCurrentPosition;
    private boolean mediaCurrentState;

    private static final String IS_INGREDIENT_SHOWN = "is_ingredient_shown";
    private static final String IS_STEP_SHOWN = "is_step_shown";

    private boolean isIngredientShown;
    private boolean isStepSelected;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_content);

        ButterKnife.bind(this);

        if (findViewById(R.id.layout_two_pane) != null) {
            mTwoPane = true;
            if (savedInstanceState != null) {
                retrieveInfoFromSavedInstanceState(savedInstanceState);
                if(isIngredientShown){
                    setFragment(new SelectIngredientStepFragment(), R.id.menu_fragment, createBundleToFragment());
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList(Constants.INGREDIENT_LIST_KEY, (ArrayList<? extends Parcelable>) mIngredientList);
                    replaceFragment(new IngredientFragment(), R.id.detail_container, bundle);

                }else {
                    setFragment(new SelectIngredientStepFragment(), R.id.menu_fragment, createBundleToFragment());
                    if (isStepSelected) {
                        createBundleAndReplaceFragmentNoClick(recipePosition);
                    }
                }
            } else {
                retrieveInfoBundle();
                setFragment(new SelectIngredientStepFragment(), R.id.menu_fragment, createBundleToFragment());
                setFragment(new StepFragment(), R.id.detail_container, createBundleToFragment());
            }
        } else {
            mTwoPane = false;
            if (savedInstanceState != null) {
                retrieveInfoFromSavedInstanceState(savedInstanceState);
                setFragment(new SelectIngredientStepFragment(), R.id.menu_fragment, createBundleToFragment());
            } else {
                retrieveInfoBundle();
                setFragment(new SelectIngredientStepFragment(), R.id.menu_fragment, createBundleToFragment());
            }
        }
        settingToolbar();
    }

    public void settingToolbar() {
        toolbar.setTitle(recipe.getName());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public Bundle createBundleToFragment() {
        Bundle bundleToFragment = new Bundle();
        bundleToFragment.putLong(MEDIA_CURRENT_POSITION, mediaCurrentPosition);
        bundleToFragment.putBoolean(MEDIA_CURRENT_STATE, mediaCurrentState);

        bundleToFragment.putParcelable(Constants.RECIPE_KEY, recipe);
        bundleToFragment.putParcelableArrayList(Constants.STEP_LIST_KEY, (ArrayList<? extends Parcelable>) stepList);
        bundleToFragment.putParcelableArrayList(Constants.INGREDIENT_LIST_KEY, (ArrayList<? extends Parcelable>) mIngredientList);
        bundleToFragment.putBoolean(Constants.SCREEN_PANES, mTwoPane);
        return bundleToFragment;
    }

    public void retrieveInfoBundle() {
        Bundle bundle = getIntent().getBundleExtra(Constants.RECIPE_KEY);
        recipe = bundle.getParcelable(Constants.RECIPE_KEY);
        stepList = bundle.getParcelableArrayList(Constants.STEP_LIST_KEY);
        mIngredientList = bundle.getParcelableArrayList(Constants.INGREDIENT_LIST_KEY);
    }

    public void retrieveInfoFromSavedInstanceState(Bundle savedInstanceState) {
        isIngredientShown = savedInstanceState.getBoolean(IS_INGREDIENT_SHOWN);
        isStepSelected = savedInstanceState.getBoolean(IS_STEP_SHOWN);
        mediaCurrentPosition = savedInstanceState.getLong(MEDIA_CURRENT_POSITION);
        mediaCurrentState = savedInstanceState.getBoolean(MEDIA_CURRENT_STATE);
        recipe = savedInstanceState.getParcelable(Constants.RECIPE_KEY);
        stepList = savedInstanceState.getParcelableArrayList(Constants.STEP_LIST_KEY);
        mIngredientList = savedInstanceState.getParcelableArrayList(Constants.INGREDIENT_LIST_KEY);
        recipePosition = savedInstanceState.getInt(Constants.RECIPE_POSITION);
    }

    private void setFragment(Fragment fragment, int container, Bundle bundle) {
        fragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .add(container, fragment)
                .commit();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean(IS_INGREDIENT_SHOWN, isIngredientShown);
        savedInstanceState.putBoolean(IS_STEP_SHOWN, isStepSelected);
        savedInstanceState.putParcelableArrayList(Constants.INGREDIENT_LIST_KEY, (ArrayList<? extends Parcelable>) mIngredientList);
        savedInstanceState.putParcelable(Constants.RECIPE_KEY, recipe);
        savedInstanceState.putParcelableArrayList(Constants.STEP_LIST_KEY, (ArrayList<? extends Parcelable>) stepList);
        savedInstanceState.putInt(Constants.RECIPE_POSITION, recipePosition);
        savedInstanceState.putLong(MEDIA_CURRENT_POSITION, mediaCurrentPosition);
        savedInstanceState.putBoolean(MEDIA_CURRENT_STATE, mediaCurrentState);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRecipeClicked(int recipePosition) {
        isIngredientShown = false;
        isStepSelected = true;
        this.recipePosition = recipePosition;
        createBundleAndReplaceFragment(recipePosition);
    }

    private void createBundleAndReplaceFragmentNoClick(int recipePosition) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.STEP_CONTENT, stepList.get(recipePosition));
        bundle.putLong(MEDIA_CURRENT_POSITION, mediaCurrentPosition);
        bundle.putBoolean(MEDIA_CURRENT_STATE, mediaCurrentState);
        replaceFragment(new StepFragment(), R.id.detail_container, bundle);
    }


    private void createBundleAndReplaceFragment(int recipePosition) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.STEP_CONTENT, stepList.get(recipePosition));
        replaceFragment(new StepFragment(), R.id.detail_container, bundle);
    }

    private void replaceFragment(Fragment fragment, int container, Bundle bundle) {
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(container, fragment)
                .commit();
    }

    @Override
    public void onIngredientClicked(Bundle bundle) {
        isIngredientShown = true;
        isStepSelected = false;
        replaceFragment(new IngredientFragment(), R.id.detail_container, bundle);
    }

    @Override
    public void currentPosition(long currentPosition) {
        this.mediaCurrentPosition = currentPosition;
    }

    @Override
    public void currentMediaState(boolean mediaState) {
        this.mediaCurrentState = mediaState;
    }

    private void showSnack(String text){
        Snackbar snackbar = Snackbar
                .make(mCoordinatorLayout, text, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public void showSignal(String text) {
        showSnack(text);
    }
}
