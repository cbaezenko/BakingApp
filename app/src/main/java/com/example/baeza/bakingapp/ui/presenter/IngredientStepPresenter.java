package com.example.baeza.bakingapp.ui.presenter;

import com.example.baeza.bakingapp.ui.data.Recipe;
import com.example.baeza.bakingapp.ui.interactor.IngredientStepInteractor;
import com.example.baeza.bakingapp.ui.manager.IngredientStepManager;

public class IngredientStepPresenter implements IngredientStepManager.Presenter{

    IngredientStepManager.View view;
    IngredientStepInteractor interactor;

    public IngredientStepPresenter(IngredientStepManager.View view){
        this.view = view;
        interactor = new IngredientStepInteractor(this);
    }


    @Override
    public void getSelectedRecipeInteractor(Recipe recipe) {
        if(view != null){
            interactor.getSelectedRecipeInteractor(recipe);
        }
    }

    @Override
    public void showRecipeTitleView(String recipeTitle) {
        if(view != null){
            view.showRecipeTitleView(recipeTitle);
        }
    }
}
