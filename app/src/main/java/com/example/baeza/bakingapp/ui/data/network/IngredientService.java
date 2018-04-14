package com.example.baeza.bakingapp.ui.data.network;

import com.example.baeza.bakingapp.ui.data.Recipe;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

public interface IngredientService {

    @GET("baking.json")
    Observable<List<Recipe>> getRecipe();

}
