package com.example.baeza.bakingapp.ui.interactor;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

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

    private SelectRecipeManager.Presenter presenter;

    public SelectRecipeInteractor(SelectRecipePresenter presenter) {
        this.presenter = presenter;
    }

    private void getRetrofitAnswer() {
        utilities.getIngredientService().getRecipe()
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Recipe>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.d(e);
                    }

                    @Override
                    public void onNext(List<Recipe> recipe) {
                        presenter.getRecipesView(recipe);
                    }
                });

    }

    @Override
    public void getRecipesInteractor() {
        getRetrofitAnswer();
    }

    @Override
    public void hasInternetConnectionInteractor(Context context) {
        presenter.hasInternetConnectionView(isNetworkConnection(context));
    }

    private boolean isNetworkConnection(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }
}
