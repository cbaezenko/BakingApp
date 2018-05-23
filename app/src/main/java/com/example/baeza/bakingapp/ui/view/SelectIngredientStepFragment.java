package com.example.baeza.bakingapp.ui.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;

import com.example.baeza.bakingapp.R;
import com.example.baeza.bakingapp.ui.data.Ingredient;
import com.example.baeza.bakingapp.ui.data.Recipe;
import com.example.baeza.bakingapp.ui.data.Step;
import com.example.baeza.bakingapp.ui.manager.IngredientStepManager;
import com.example.baeza.bakingapp.ui.manager.OnFragmentSelectedListener;
import com.example.baeza.bakingapp.ui.manager.OnIngredientListener;
import com.example.baeza.bakingapp.ui.utility.Constants;
import com.example.baeza.bakingapp.ui.utility.FavoriteRecipe;
import com.example.baeza.bakingapp.ui.utility.StepRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectIngredientStepFragment extends Fragment implements IngredientStepManager.View,
        StepRecyclerViewAdapter.ListenStep {

    @BindView(R.id.button_ingredient)
    Button buttonIngredient;
    @BindView(R.id.recyclerView_steps)
    RecyclerView recyclerViewSteps;
    @BindView(R.id.switch_favorite)
    Switch switchFavorite;

    private Recipe mRecipe;
    private List<Step> mStepList;
    private List<Ingredient> mIngredientList;
    private StepRecyclerViewAdapter mStepRecyclerViewAdapter;
    private boolean twoPane;

    private OnIngredientListener mOnIngredientListener;
    private OnFragmentSelectedListener mCallback;
    private OnFavoriteSignal mOnFavoriteSignal;

    private static final String RECIPE_KEY = "RECIPE_KE";
    private static final String STEP_LIST_KEY = "STEP_LIST_KEY";
    private static final String INGREDIENT_LIST_KEY = "INGREDIENT_LIST_KEY";
    private static final String TWO_PANE_KEY = "TWO_PANE_KEY";

    private FavoriteRecipe mFavoriteRecipe;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //This makes sure that the host activity has implemented the callback interface
        //if not, it throws an exception
        try {
            mCallback = (OnFragmentSelectedListener) context;
            mOnIngredientListener = (OnIngredientListener) context;
            mOnFavoriteSignal = (OnFavoriteSignal) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement OnFragmentSelectedListener");
        }
    }

    public SelectIngredientStepFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ingredients_steps, container, false);
        Bundle bundle = getArguments();

        mFavoriteRecipe = new FavoriteRecipe(getContext());
        ButterKnife.bind(this, rootView);
        if (savedInstanceState != null) {
            mRecipe = savedInstanceState.getParcelable(RECIPE_KEY);
            mStepList = savedInstanceState.getParcelableArrayList(STEP_LIST_KEY);
            mIngredientList = savedInstanceState.getParcelableArrayList(INGREDIENT_LIST_KEY);
            twoPane = savedInstanceState.getBoolean(TWO_PANE_KEY);
        } else {
            if (bundle != null) {
                mRecipe = bundle.getParcelable(Constants.RECIPE_KEY);
                mStepList = bundle.getParcelableArrayList(Constants.STEP_LIST_KEY);
                mIngredientList = bundle.getParcelableArrayList(Constants.INGREDIENT_LIST_KEY);
                twoPane = bundle.getBoolean(Constants.SCREEN_PANES);
            }

            initSwitch();
            LinearLayoutManager layoutManager = new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.VERTICAL, false);

            mStepRecyclerViewAdapter = new StepRecyclerViewAdapter(getContext(), mStepList, mRecipe.getName(), twoPane, this);

            recyclerViewSteps.setAdapter(mStepRecyclerViewAdapter);
            recyclerViewSteps.setHasFixedSize(true);
            recyclerViewSteps.setLayoutManager(layoutManager);

            buttonIngredient.setText(String.format("%s %s", getContext().getString(R.string.ingredients), mRecipe.getName()));
        }
        return rootView;
    }

    @OnClick(R.id.button_ingredient)
    void onClick() {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Constants.INGREDIENT_LIST_KEY, (ArrayList<? extends Parcelable>) mIngredientList);
        bundle.putString(Constants.RECIPE_KEY, mRecipe.getName());
        if (!twoPane) {
            Intent intent = new Intent(getContext(), IngredientActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        } else {
            mOnIngredientListener.onIngredientClicked(bundle);
        }
    }

    @OnClick(R.id.switch_favorite)
    void onClickSwitch() {
        if (mFavoriteRecipe.getRecipeNameFromPref().equals(mRecipe.getName())) {
            String defaultRecipe = getResources().getString(R.string.default_recipe);
            int defaultId = Integer.parseInt(getResources().getString(R.string.default_id));

            mFavoriteRecipe.saveRecipeNameToPref(defaultRecipe);
            mFavoriteRecipe.saveRecipeIdToPref(defaultId);

            mOnFavoriteSignal.showSignal("Widget to default value " + getContext()
                    .getResources()
                    .getString(R.string.default_recipe));
        } else {
            mFavoriteRecipe.saveRecipeNameToPref(mRecipe.getName());
            mFavoriteRecipe.saveRecipeIdToPref(mRecipe.getId());
            mOnFavoriteSignal.showSignal("save " + mRecipe.getName() + " to Widget");
        }
        initSwitch();
    }

    private void initSwitch() {
        if (mFavoriteRecipe.getRecipeNameFromPref().equals(mRecipe.getName())) {
            switchFavorite.setChecked(true);
        } else {
            switchFavorite.setChecked(false);
        }
    }


    @Override
    public void showRecipeTitleView(String recipeTitle) {
        buttonIngredient.setText(recipeTitle);
    }

    @Override
    public void onRecipeClicked(int item) {
        mCallback.onRecipeClicked(item);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean(TWO_PANE_KEY, twoPane);
        outState.putParcelable(RECIPE_KEY, mRecipe);
        outState.putParcelableArrayList(STEP_LIST_KEY, (ArrayList<? extends Parcelable>) mStepList);
        outState.putParcelableArrayList(INGREDIENT_LIST_KEY, (ArrayList<? extends Parcelable>) mIngredientList);
        super.onSaveInstanceState(outState);
    }

    public interface OnFavoriteSignal {
        void showSignal(String text);
    }
}
