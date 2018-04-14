package com.example.baeza.bakingapp.ui.presenter;

import com.example.baeza.bakingapp.ui.data.Recipe;
import com.example.baeza.bakingapp.ui.interactor.SelectRecipeInteractor;
import com.example.baeza.bakingapp.ui.manager.SelectRecipeManager;

import java.util.ArrayList;

import timber.log.Timber;

public class SelectRecipePresenter implements SelectRecipeManager.Presenter {

    SelectRecipeManager.View view;
    SelectRecipeManager.Interactor interactor;
    public SelectRecipePresenter(SelectRecipeManager.View view){
        this.view = view;
        interactor = new SelectRecipeInteractor(this);
    }

    @Override
    public void getRecipesInteractor() {
        if(view != null){
            Timber.d("is it running?");
            interactor.getRecipesInteractor();
        }
    }

    @Override
    public void getRecipesView(ArrayList<Recipe> recipesArrayList) {
        if(view != null){

        }
    }
}
