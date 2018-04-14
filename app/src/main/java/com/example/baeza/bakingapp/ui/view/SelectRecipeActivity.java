package com.example.baeza.bakingapp.ui.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.baeza.bakingapp.R;
import com.example.baeza.bakingapp.ui.data.Recipe;
import com.example.baeza.bakingapp.ui.manager.SelectRecipeManager;
import com.example.baeza.bakingapp.ui.presenter.SelectRecipePresenter;
import com.example.baeza.bakingapp.ui.utility.SelectRecipeAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class SelectRecipeActivity extends AppCompatActivity implements SelectRecipeManager.View {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    SelectRecipeAdapter mAdapter;
    SelectRecipePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        Timber.plant(new Timber.DebugTree());

        RecyclerView.LayoutManager layoutManager;
        if (findViewById(R.id.sw600dp) != null) {
            layoutManager = new GridLayoutManager(this, 3);
        } else {
            layoutManager = new LinearLayoutManager(this);
        }

        mPresenter = new SelectRecipePresenter(this);
        mPresenter.getRecipesInteractor();

        mAdapter = new SelectRecipeAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
    }

    @Override
    public void getRecipesView(ArrayList<Recipe> recipeArrayList) {

    }
}
