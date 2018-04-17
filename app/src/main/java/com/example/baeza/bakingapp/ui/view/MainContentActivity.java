package com.example.baeza.bakingapp.ui.view;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.baeza.bakingapp.R;
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
    private Bundle bundleToFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_content);

        ButterKnife.bind(this);

        //check for phone or tablet
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
        bundleToFragment.putParcelableArrayList("steps", (ArrayList<? extends Parcelable>) stepList);
    }

    public void retrieveInfoBundle(){
        Bundle bundle = getIntent().getBundleExtra("bundle");
        recipe = bundle.getParcelable(Constants.RECIPE_KEY);
        stepList = bundle.getParcelableArrayList("steps");
    }
}
