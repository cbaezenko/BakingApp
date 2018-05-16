package com.example.baeza.bakingapp.ui.manager;

import com.example.baeza.bakingapp.ui.data.Recipe;

public interface IngredientStepManager {

    interface Interactor{
        void getSelectedRecipeInteractor(Recipe recipe);
    }

    interface View{
        void showRecipeTitleView(String recipeTitle);

    }

    interface Presenter{
        void getSelectedRecipeInteractor(Recipe recipe);
        void showRecipeTitleView(String recipeTitle);
    }
}
