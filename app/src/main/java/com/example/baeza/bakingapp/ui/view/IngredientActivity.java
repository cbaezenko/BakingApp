package com.example.baeza.bakingapp.ui.view;

import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.example.baeza.bakingapp.R;
import com.example.baeza.bakingapp.ui.data.Ingredient;
import com.example.baeza.bakingapp.ui.utility.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientActivity extends AppCompatActivity {

    @BindView(R.id.container)
    FrameLayout mFrameLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    List<Ingredient> mIngredientList;
    private String recipeName = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);

        ButterKnife.bind(this);

        if (savedInstanceState != null) {
            recipeName = savedInstanceState.getString(Constants.RECIPE_KEY);
        } else {
            Bundle bundle = getIntent().getExtras();
            mIngredientList = bundle.getParcelableArrayList(Constants.INGREDIENT_LIST_KEY);
            recipeName = bundle.getString(Constants.RECIPE_KEY);

            IngredientFragment ingredientFragment = new IngredientFragment();
            ingredientFragment.setArguments(bundleToFragment(mIngredientList));
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, ingredientFragment)
                    .commit();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            settingToolbar();
        }
    }

    private Bundle bundleToFragment(List<Ingredient> ingredientList) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Constants.INGREDIENT_LIST_KEY, (ArrayList<? extends Parcelable>) ingredientList);
        return bundle;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void settingToolbar() {
        mToolbar.setTitle(recipeName);
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString(Constants.RECIPE_KEY, recipeName);
        super.onSaveInstanceState(savedInstanceState);
    }
}
