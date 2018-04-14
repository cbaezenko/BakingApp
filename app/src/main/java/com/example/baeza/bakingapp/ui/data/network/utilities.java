package com.example.baeza.bakingapp.ui.data.network;

import com.example.baeza.bakingapp.ui.interactor.SelectRecipeInteractor;

public class utilities {

    private static final String BASE_URL_INGREDIENT =
            "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";


    public static IngredientService getIngredientService(){
        return RetrofitClient.getClient(BASE_URL_INGREDIENT)
                .create(IngredientService.class);
    }

    public static String getBaseUrlIngredient() {
        return BASE_URL_INGREDIENT;
    }
}
