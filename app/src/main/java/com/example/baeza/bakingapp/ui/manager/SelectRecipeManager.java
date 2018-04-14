package com.example.baeza.bakingapp.ui.manager;

import com.example.baeza.bakingapp.ui.data.Recipe;

import java.util.ArrayList;

public interface SelectRecipeManager {

    public interface View{
        void getRecipesView(ArrayList<Recipe> recipeArrayList);
    }
    public interface Interactor {
        void getRecipesInteractor();
    }
    public interface Presenter{
        void getRecipesInteractor();
        void getRecipesView(ArrayList<Recipe> recipesArrayList);
    }
}
