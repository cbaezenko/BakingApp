package com.example.baeza.bakingapp.ui.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.baeza.bakingapp.R;
import com.example.baeza.bakingapp.ui.data.Recipe;
import com.example.baeza.bakingapp.ui.data.Step;
import com.example.baeza.bakingapp.ui.manager.IngredientStepManager;
import com.example.baeza.bakingapp.ui.utility.Constants;
import com.example.baeza.bakingapp.ui.utility.StepRecyclerViewAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectIngredientStep extends Fragment implements IngredientStepManager.View {

    @BindView(R.id.button_ingredient)
    Button buttonIngredient;

    @BindView(R.id.recyclerView_steps)
    RecyclerView recyclerViewSteps;
    Recipe mRecipe;
    List<Step> mStepList;
    StepRecyclerViewAdapter mStepRecyclerViewAdapter;

    public SelectIngredientStep() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ingredients_steps, container, false);
        Bundle bundle = getArguments();

        ButterKnife.bind(this, rootView);
        if (bundle != null) {
            mRecipe = bundle.getParcelable(Constants.RECIPE_KEY);
            mStepList = bundle.getParcelableArrayList("steps");
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(container.getContext(), LinearLayoutManager.VERTICAL, false);

        mStepRecyclerViewAdapter = new StepRecyclerViewAdapter(getContext(), mStepList);

        recyclerViewSteps.setAdapter(mStepRecyclerViewAdapter);
        recyclerViewSteps.setHasFixedSize(true);
        recyclerViewSteps.setLayoutManager(layoutManager);

        buttonIngredient.setText(getContext().getString(R.string.ingredients) +" "+ mRecipe.getName());

        return rootView;
    }

    @OnClick(R.id.button_ingredient)
    void onClick(){
        Intent intent = new Intent(getContext(), IngredientActivity.class);
        startActivity(intent);
    }

    @Override
    public void showRecipeTitleView(String recipeTitle) {
        buttonIngredient.setText("ALGO " + recipeTitle);
    }
}
