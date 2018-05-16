package com.example.baeza.bakingapp.ui.view;

import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.baeza.bakingapp.R;
import com.example.baeza.bakingapp.ui.data.Recipe;
import com.example.baeza.bakingapp.ui.manager.SelectRecipeManager;
import com.example.baeza.bakingapp.ui.presenter.SelectRecipePresenter;
import com.example.baeza.bakingapp.ui.utility.SelectRecipeAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class SelectRecipeActivity extends AppCompatActivity implements SelectRecipeManager.View {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.coordinator)
    CoordinatorLayout mCoordinatorLayout;
    @BindView(R.id.no_internet_connection)
    FrameLayout mFrameLayoutNoInternetConnection;
    @BindView(R.id.frameProgressBar)
    FrameLayout mFrameLayoutProgressBar;

    SelectRecipeAdapter mAdapter;
    SelectRecipePresenter mPresenter;
    RecyclerView.LayoutManager layoutManager;

    private boolean hasInternetConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_recipe);

        ButterKnife.bind(this);
        Timber.plant(new Timber.DebugTree());

        if (findViewById(R.id.sw600dp) != null) {
            layoutManager = new GridLayoutManager(this, 3);
        } else {
            layoutManager = new LinearLayoutManager(this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter = new SelectRecipePresenter(this);
        mPresenter.hasInternetConnectionInteractor(SelectRecipeActivity.this);

        if (hasInternetConnection) {
            mPresenter.getRecipesInteractor();
            mFrameLayoutProgressBar.setVisibility(View.VISIBLE);
        } else {
            mFrameLayoutNoInternetConnection.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void getRecipesView(List<Recipe> recipeList) {
        mFrameLayoutProgressBar.setVisibility(View.GONE);
        populateRecyclerView(recipeList);
    }

    @Override
    public void hasInternetConnectionView(boolean hasInternetConnection) {
        this.hasInternetConnection = hasInternetConnection;
    }

    private void populateRecyclerView(List<Recipe> recipeList) {
        mAdapter = new SelectRecipeAdapter(this, recipeList, mCoordinatorLayout);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
    }
}
