package com.example.baeza.bakingapp.ui.presenter;

import android.content.Context;

import com.example.baeza.bakingapp.ui.data.Recipe;
import com.example.baeza.bakingapp.ui.interactor.SelectRecipeInteractor;
import com.example.baeza.bakingapp.ui.manager.SelectRecipeManager;

import java.util.List;

import timber.log.Timber;

public class SelectRecipePresenter implements SelectRecipeManager.Presenter {

    SelectRecipeManager.View view;
    SelectRecipeManager.Interactor interactor;

    public SelectRecipePresenter(SelectRecipeManager.View view) {
        this.view = view;
        interactor = new SelectRecipeInteractor(this);
    }

    @Override
    public void getRecipesInteractor() {
        if (view != null) {
            interactor.getRecipesInteractor();
        }
    }

    @Override
    public void hasInternetConnectionInteractor(Context context) {
        if(view != null){
            interactor.hasInternetConnectionInteractor(context);
        }
    }

    @Override
    public void getRecipesView(List<Recipe> recipeList) {
        if (view != null) {
            view.getRecipesView(recipeList);
        }
    }

    @Override
    public void hasInternetConnectionView(boolean hasInternetConnection) {
        if(view != null){
            view.hasInternetConnectionView(hasInternetConnection);
        }
    }
}
