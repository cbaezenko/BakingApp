package com.example.baeza.bakingapp.ui.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.baeza.bakingapp.R;
import com.example.baeza.bakingapp.ui.data.Recipe;
import com.example.baeza.bakingapp.ui.utility.Constants;


public class MainContentActivity extends AppCompatActivity {

    private static final String TAG = "content";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_content);

        //check for phone or tablet
        Recipe recipe = getIntent().getParcelableExtra(Constants.RECIPE_KEY);

        SelectIngredientStep selectIngredientStep = new SelectIngredientStep();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.RECIPE_KEY, recipe);
        selectIngredientStep.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_list, selectIngredientStep)
                .commit();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(recipe.getName());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}
