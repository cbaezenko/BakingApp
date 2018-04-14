package com.example.baeza.bakingapp.ui.interactor;

import com.example.baeza.bakingapp.ui.data.Recipe;
import com.example.baeza.bakingapp.ui.data.network.utilities;
import com.example.baeza.bakingapp.ui.manager.SelectRecipeManager;
import com.example.baeza.bakingapp.ui.presenter.SelectRecipePresenter;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class SelectRecipeInteractor implements SelectRecipeManager.Interactor {

    SelectRecipeManager.Presenter presenter;

    public SelectRecipeInteractor(SelectRecipePresenter presenter){
        this.presenter = presenter;
    }

    private void getRetrofitAnswer(){
        utilities.getIngredientService().getRecipe()
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber <List<Recipe>>() {
                    @Override
                    public void onCompleted() {
                    Timber.d("mostrar completo");
                    }

                    @Override
                    public void onError(Throwable e) {
                    Timber.d("mostrar error"+e.getCause()+e.getMessage().toString());
                    }

                    @Override
                    public void onNext(List<Recipe> recipe) {
                    Timber.d("mostrar recipe name "+recipe.get(0).getIngredients().get(1).getIngredient().toString());
                    }
                });

    }

    @Override
    public void getRecipesInteractor() {
        Timber.d("mostrar inicio de metodo");
        getRetrofitAnswer();
    }
}
