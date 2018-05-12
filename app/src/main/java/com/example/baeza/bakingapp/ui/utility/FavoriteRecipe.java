package com.example.baeza.bakingapp.ui.utility;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.baeza.bakingapp.widget.RecipeService;

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

        Timber.d("SAVING THIS VALUE TO PREFS" +name);

        editor.apply();

//        RecipeService.startActionUpdateRecipe(context);

    }

    public  String getRecipeNameFromPref(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.RECIPE_NAME, Context.MODE_PRIVATE);
        Timber.d("VALOR DE PREFS "+sharedPreferences.getString(Constants.RECIPE_NAME, "Recipel"));
        return sharedPreferences.getString(Constants.RECIPE_NAME, "Recipel");
    }
}
