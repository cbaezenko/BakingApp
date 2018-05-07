package com.example.baeza.bakingapp.ui.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    //the authority, which is how your code knows which Content provider to access.
    public static final String AUTHORITY = "com.example.baeza.bakingapp";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+AUTHORITY);

    //this is the path for the "recipe" directory
    public static final String PATH_RECIPE = "recipe";

    private DatabaseContract(){}

    public static class FavoriteRecipe implements BaseColumns{
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_RECIPE).build();

        public static final String TABLE_NAME = "recipe_favorite";
        public static final String ID = "recipe_id";
    }
}
