package com.example.baeza.bakingapp.ui.interactor;

import com.example.baeza.bakingapp.ui.data.Recipe;
import com.example.baeza.bakingapp.ui.manager.IngredientStepManager;

public class IngredientStepInteractor implements IngredientStepManager.Interactor {

    IngredientStepManager.Presenter mPresenter;

    public IngredientStepInteractor(IngredientStepManager.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getSelectedRecipeInteractor(Recipe recipe) {
    }
}
