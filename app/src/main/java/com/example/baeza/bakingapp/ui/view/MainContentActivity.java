package com.example.baeza.bakingapp.ui.view;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.baeza.bakingapp.R;
import com.example.baeza.bakingapp.ui.data.Ingredient;
import com.example.baeza.bakingapp.ui.data.Recipe;
import com.example.baeza.bakingapp.ui.data.Step;
import com.example.baeza.bakingapp.ui.manager.OnFragmentSelectedListener;
import com.example.baeza.bakingapp.ui.manager.OnIngredientListener;
import com.example.baeza.bakingapp.ui.utility.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainContentActivity extends AppCompatActivity
        implements OnFragmentSelectedListener, OnIngredientListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private Recipe recipe;
    private List<Step> stepList;
    private List<Ingredient> mIngredientList;
    private int recipePosition = 0;
    private final static String POSITION = "POSITION";

    private boolean mTwoPane;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_content);

        ButterKnife.bind(this);

        if (findViewById(R.id.layout_two_pane) != null) {
            mTwoPane = true;
            if (savedInstanceState != null) {
                retrieveInfoFromSavedInstanceState(savedInstanceState);
                setFragment(new SelectIngredientStepFragment(), R.id.menu_fragment, createBundleToFragment());
                createBundleAndReplaceFragment(recipePosition);
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
        recipe = savedInstanceState.getParcelable(Constants.RECIPE_KEY);
        stepList = savedInstanceState.getParcelableArrayList(Constants.STEP_LIST_KEY);
        mIngredientList = savedInstanceState.getParcelableArrayList(Constants.INGREDIENT_LIST_KEY);
        recipePosition = savedInstanceState.getInt(POSITION);
    }

    private void setFragment(Fragment fragment, int container, Bundle bundle) {
        fragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .add(container, fragment)
                .commit();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putParcelableArrayList(Constants.INGREDIENT_LIST_KEY, (ArrayList<? extends Parcelable>) mIngredientList);
        savedInstanceState.putParcelable(Constants.RECIPE_KEY, recipe);
        savedInstanceState.putParcelableArrayList(Constants.STEP_LIST_KEY, (ArrayList<? extends Parcelable>) stepList);
        savedInstanceState.putInt(POSITION, recipePosition);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRecipeClicked(int recipePosition) {
        this.recipePosition = recipePosition;
        createBundleAndReplaceFragment(recipePosition);
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
        replaceFragment(new IngredientFragment(), R.id.detail_container, bundle);
    }
}
