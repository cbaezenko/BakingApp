package com.example.baeza.bakingapp.ui.utility;

import android.content.Context;
import android.content.SharedPreferences;

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
    }

    public int getRecipeIdFromPref(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.ID_RECIPE, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(Constants.ID_RECIPE, 1);
    }
}
