package com.example.baeza.bakingapp.ui.manager;

import com.example.baeza.bakingapp.ui.data.Recipe;

import java.util.ArrayList;
import java.util.List;

public interface SelectRecipeManager {

    public interface View{
        void getRecipesView(List<Recipe> recipeList);
    }
    public interface Interactor {
        void getRecipesInteractor();
    }
    public interface Presenter{
        void getRecipesInteractor();
        void getRecipesView(List<Recipe> recipesList);
    }
}
