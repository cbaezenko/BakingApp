package com.example.baeza.bakingapp.ui.view;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.baeza.bakingapp.R;
import com.example.baeza.bakingapp.ui.data.Ingredient;
import com.example.baeza.bakingapp.ui.utility.Constants;
import com.example.baeza.bakingapp.ui.utility.IngredientAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientFragment extends Fragment {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    List<Ingredient> ingredientList;

    public IngredientFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Context context = getContext();
        View rootView = inflater.inflate(R.layout.recyclerview, container, false);
        ButterKnife.bind(this, rootView);


        if (savedInstanceState != null) {
            ingredientList = savedInstanceState.getParcelableArrayList(Constants.INGREDIENT_LIST_KEY);
        } else {
            Bundle bundle = this.getArguments();
            assert bundle != null;
            ingredientList = bundle.getParcelableArrayList(Constants.INGREDIENT_LIST_KEY);
        }
        IngredientAdapter ingredientAdapter = new IngredientAdapter(ingredientList);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(ingredientAdapter);
        mRecyclerView.setHasFixedSize(true);

        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle saveInstanceState) {
        saveInstanceState.putParcelableArrayList(Constants.INGREDIENT_LIST_KEY, (ArrayList<? extends Parcelable>) ingredientList);
    }
}
