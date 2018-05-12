package com.example.baeza.bakingapp.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import timber.log.Timber;

public class RecipeService extends IntentService {

    public static final String ACTION_UPDATE_RECIPE = "com.example.baeza.bakingapp.widget.action.update_recipe";


    public RecipeService() {
        super("RecipeService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        if (intent != null){
            final String action = intent.getAction();
            if(ACTION_UPDATE_RECIPE.equals(action)){
                Timber.d("Service is done");
                handleActionUpdateRecipe();
            }
        }

    }

    private void handleActionUpdateRecipe(){
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int [] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakingAppWidget.class));

        BakingAppWidget.updateRecipeWidget(this, appWidgetManager, appWidgetIds);
    }

    //create a static method called startActionUpdateRecipe thath allows explicity
    //triggering the Service to perform this action, inside simple create an intent
    //that refers ti the same class and set to ACTION_UPDATE_RECIPE
    public static void startActionUpdateRecipe(Context context) {
        Intent intent = new Intent(context, RecipeService.class);
        intent.setAction(ACTION_UPDATE_RECIPE);
        context.startService(intent);
    }
}
