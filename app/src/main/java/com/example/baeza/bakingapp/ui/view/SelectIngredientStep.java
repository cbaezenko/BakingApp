package com.example.baeza.bakingapp.ui.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.baeza.bakingapp.R;
import com.example.baeza.bakingapp.ui.manager.IngredientStepManager;

import butterknife.BindView;

public class SelectIngredientStep extends Fragment implements IngredientStepManager.View {

    @BindView(R.id.button_ingredient)
    Button buttonIngredient;

    public SelectIngredientStep (){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_ingredients_steps, container, false);
        return rootView;
    }

    @Override
    public void showRecipeTitleView(String recipeTitle) {
        buttonIngredient.setText("ALGO "+recipeTitle);
    }
}
