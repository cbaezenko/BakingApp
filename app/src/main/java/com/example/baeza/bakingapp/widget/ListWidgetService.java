package com.example.baeza.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.baeza.bakingapp.R;
import com.example.baeza.bakingapp.ui.data.Recipe;
import com.example.baeza.bakingapp.ui.data.network.utilities;
import com.example.baeza.bakingapp.ui.utility.FavoriteRecipe;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class ListWidgetService extends RemoteViewsService{

    private List<Recipe> mRecipeList = new ArrayList<>();
    private Context mContext;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(this.getApplicationContext());
    }

    class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory{

        public ListRemoteViewsFactory (Context applicationContext){
            mContext = applicationContext;
        }

        @Override
        public void onCreate() {
            getRetrofitAnswer();
        }

        @Override
        public void onDataSetChanged() {
            //I refresh the widget when the user select a new favorite recipe on
            //FavoriteRecipe.java from lines 77 to 80
        }

        @Override
        public void onDestroy() {
        }

        @Override
        public int getCount() {
            if(mRecipeList.size()==0)return 0;
            if(mRecipeList.get((new FavoriteRecipe(mContext).getRecipeIdFromPref())-1).getIngredients().size()!=0)
                return mRecipeList.get((new FavoriteRecipe(mContext).getRecipeIdFromPref())-1).getIngredients().size();
            return 0;
        }

        @Override
        public RemoteViews getViewAt(int i) {
            RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_item_ingredient);
            if(mRecipeList != null && (mRecipeList.size()>0)){
                views.setTextViewText(R.id.item_list, mRecipeList.get((new FavoriteRecipe(mContext).getRecipeIdFromPref())-1).getIngredients().get(i).getIngredient());
                views.setTextViewText(R.id.tv_quantity, Double.toString(mRecipeList.get((new FavoriteRecipe(mContext).getRecipeIdFromPref())-1).getIngredients().get(i).getQuantity()));
                views.setTextViewText(R.id.tv_measure, mRecipeList.get((new FavoriteRecipe(mContext).getRecipeIdFromPref())-1).getIngredients().get(i).getMeasure());

                Timber.d("recipe name%s", new FavoriteRecipe(mContext).getRecipeNameFromPref());
                Timber.d("recipeId%s", new FavoriteRecipe(mContext).getRecipeIdFromPref());

            }else{
            views.setTextViewText(R.id.item_list, "HOLA");}
            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 10;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
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
                    public void onNext(List<Recipe> recipeList) {
                        mRecipeList = recipeList;

                        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(mContext);
                        int [] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(mContext, BakingAppWidget.class));

                        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.appwidget_list);
                    }
                });
    }
}
