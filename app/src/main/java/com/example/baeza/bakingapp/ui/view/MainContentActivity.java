package com.example.baeza.bakingapp.ui.view;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.baeza.bakingapp.R;
import com.example.baeza.bakingapp.ui.data.Ingredient;
import com.example.baeza.bakingapp.ui.data.Recipe;
import com.example.baeza.bakingapp.ui.data.Step;
import com.example.baeza.bakingapp.ui.utility.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainContentActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;
    private Recipe recipe;
    private List<Step> stepList;
    private List<Ingredient> mIngredientList;
    private Bundle bundleToFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_content);

        ButterKnife.bind(this);

        //check for phone or tablet
        if(savedInstanceState!=null){
            retrieveInfoForSavedInstanceState(savedInstanceState);
        }else{
            retrieveInfoBundle();
        }
        retrieveInfoBundle();

        createBundleToFragment();

        SelectIngredientStep selectIngredientStep = new SelectIngredientStep();
        selectIngredientStep.setArguments(bundleToFragment);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_list, selectIngredientStep)
                .commit();

        settingToolbar();
    }

    public void settingToolbar(){
        toolbar.setTitle(recipe.getName());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void createBundleToFragment(){
        bundleToFragment = new Bundle();
        bundleToFragment.putParcelable(Constants.RECIPE_KEY, recipe);
        bundleToFragment.putParcelableArrayList(Constants.STEP_LIST_KEY, (ArrayList<? extends Parcelable>) stepList);
        bundleToFragment.putParcelableArrayList(Constants.INGREDIENT_LIST_KEY, (ArrayList<? extends Parcelable>) mIngredientList);
    }

    public void retrieveInfoBundle(){
        Bundle bundle = getIntent().getBundleExtra(Constants.RECIPE_KEY);
        recipe = bundle.getParcelable(Constants.RECIPE_KEY);
        stepList = bundle.getParcelableArrayList(Constants.STEP_LIST_KEY);
        mIngredientList = bundle.getParcelableArrayList(Constants.INGREDIENT_LIST_KEY);
    }

    public void retrieveInfoForSavedInstanceState(Bundle savedInstanceState){
        recipe = savedInstanceState.getParcelable(Constants.RECIPE_KEY);
        stepList = savedInstanceState.getParcelableArrayList(Constants.STEP_LIST_KEY);
        mIngredientList = savedInstanceState.getParcelableArrayList(Constants.INGREDIENT_LIST_KEY);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putParcelableArrayList(Constants.INGREDIENT_LIST_KEY, (ArrayList<? extends Parcelable>) mIngredientList);
        savedInstanceState.putParcelable(Constants.RECIPE_KEY, recipe);
        savedInstanceState.putParcelableArrayList(Constants.STEP_LIST_KEY, (ArrayList<? extends Parcelable>) stepList);
        super.onSaveInstanceState(savedInstanceState);
    }
}
