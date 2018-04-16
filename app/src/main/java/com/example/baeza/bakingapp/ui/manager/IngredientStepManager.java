package com.example.baeza.bakingapp.ui.manager;

import com.example.baeza.bakingapp.ui.data.Recipe;

public interface IngredientStepManager {

    public interface Interactor{
        void getSelectedRecipeInteractor(Recipe recipe);
    }

    public interface View{
        void showRecipeTitleView(String recipeTitle);

    }

    public interface Presenter{
        void getSelectedRecipeInteractor(Recipe recipe);
        void showRecipeTitleView(String recipeTitle);
    }
}
