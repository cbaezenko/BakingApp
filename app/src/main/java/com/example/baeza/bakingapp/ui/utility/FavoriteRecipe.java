package com.example.baeza.bakingapp.ui.utility;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.baeza.bakingapp.R;
import com.example.baeza.bakingapp.ui.data.Recipe;
import com.example.baeza.bakingapp.ui.data.network.utilities;
import com.example.baeza.bakingapp.widget.BakingAppWidget;
import com.example.baeza.bakingapp.widget.RecipeService;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class FavoriteRecipe {

    private Context context;
    public FavoriteRecipe(){}

    public FavoriteRecipe(Context context){
        this.context = context;
    }

    public void saveRecipeIdToPref(int id){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.ID_RECIPE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(Constants.ID_RECIPE, id);
        editor.apply();

        RecipeService.startActionUpdateRecipe(context);
    }

    public int getRecipeIdFromPref(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.ID_RECIPE, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(Constants.ID_RECIPE, 1);
    }

    public  void saveRecipeNameToPref(String name){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.RECIPE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.RECIPE_NAME, name);

        editor.apply();
        informWidgetToUpdate();
    }

    public  String getRecipeNameFromPref(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.RECIPE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Constants.RECIPE_NAME, "Brownies");
    }

    private void informWidgetToUpdate(){
        getRetrofitAnswer();
    }

    private void getRetrofitAnswer() {
        utilities.getIngredientService().getRecipe()
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Recipe>>() {
                    @Override
                    public void onCompleted() { }

                    @Override
                    public void onError(Throwable e) {
                        Timber.d(e);
                    }

                    @Override
                    public void onNext(List<Recipe> recipeList) {

                        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                        int [] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, BakingAppWidget.class));

                        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.appwidget_list);

                    }
                });
    }
}
