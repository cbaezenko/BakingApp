package com.example.baeza.bakingapp.ui.manager;

import android.content.Context;

import com.example.baeza.bakingapp.ui.data.Recipe;

import java.util.ArrayList;
import java.util.List;

public interface SelectRecipeManager {

    interface View {
        void getRecipesView(List<Recipe> recipeList);
        void hasInternetConnectionView(boolean hasInternetConnection);
    }

    interface Interactor {
        void getRecipesInteractor();

        void hasInternetConnectionInteractor(Context context);
    }

    interface Presenter {
        void getRecipesInteractor();
        void hasInternetConnectionInteractor(Context context);

        void getRecipesView(List<Recipe> recipesList);
        void hasInternetConnectionView(boolean hasInternetConnection);
    }
}
