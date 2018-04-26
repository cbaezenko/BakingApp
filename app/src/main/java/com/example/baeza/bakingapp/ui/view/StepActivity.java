package com.example.baeza.bakingapp.ui.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.baeza.bakingapp.R;
import com.example.baeza.bakingapp.ui.data.Step;
import com.example.baeza.bakingapp.ui.utility.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private String toolbarTitle = "";
    private Step mStep;

    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_step);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        mStep = bundle.getParcelable(Constants.STEP_CONTENT);
        toolbarTitle = bundle.getString(Constants.RECIPE_NAME);

        settingToolbar();


        StepFragment stepFragment =  new StepFragment();

        stepFragment.setArguments(argumentsToFragment());
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, stepFragment)
                .commit();
    }

    public void settingToolbar(){
        mToolbar.setTitle(toolbarTitle);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private Bundle argumentsToFragment(){
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.STEP_CONTENT, mStep);
        return bundle;
    }
}