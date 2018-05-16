package com.example.baeza.bakingapp.ui.view;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import android.support.test.espresso.IdlingResource;

import com.example.baeza.bakingapp.IdlingResource.SimpleIdlingResource;
import com.example.baeza.bakingapp.R;
import com.example.baeza.bakingapp.ui.data.Recipe;
import com.example.baeza.bakingapp.ui.manager.SelectRecipeManager;
import com.example.baeza.bakingapp.ui.presenter.SelectRecipePresenter;
import com.example.baeza.bakingapp.ui.utility.SelectRecipeAdapter;

import java.util.ArrayList;
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
    @BindView(R.id.tv_no_internet_connection)
    TextView tv_no_internet_connection;

    List<Recipe> mRecipeList;
    SelectRecipeAdapter mAdapter;
    SelectRecipePresenter mPresenter;
    RecyclerView.LayoutManager layoutManager;

    private boolean hasInternetConnection;
    private static final String INFO_TO_KEEP = "INFO_TO_KEEP";

    @Nullable
    private SimpleIdlingResource mIdlingResource;

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource(){
        if(mIdlingResource == null){
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

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
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState != null) {
            mRecipeList = savedInstanceState.getParcelableArrayList(INFO_TO_KEEP);
            populateRecyclerView(mRecipeList);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mFrameLayoutNoInternetConnection.getVisibility() == View.VISIBLE) {
            mFrameLayoutNoInternetConnection.setVisibility(View.GONE);
        }

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
        if (recipeList != null) {
            mFrameLayoutProgressBar.setVisibility(View.GONE);

            //for testing purposes
            mIdlingResource.setIdleState(true);

            mRecipeList = recipeList;
            populateRecyclerView(mRecipeList);
        } else {
            mFrameLayoutNoInternetConnection.setVisibility(View.VISIBLE);
            tv_no_internet_connection.setText(getResources().getString(R.string.data_error));
        }
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(INFO_TO_KEEP, (ArrayList<? extends Parcelable>) mRecipeList);
        super.onSaveInstanceState(outState);
    }
}
