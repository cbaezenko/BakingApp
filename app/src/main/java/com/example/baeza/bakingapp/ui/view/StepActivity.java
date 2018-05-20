package com.example.baeza.bakingapp.ui.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.baeza.bakingapp.R;
import com.example.baeza.bakingapp.ui.data.Step;
import com.example.baeza.bakingapp.ui.manager.OnMediaCurrentPosition;
import com.example.baeza.bakingapp.ui.utility.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepActivity extends AppCompatActivity implements OnMediaCurrentPosition {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private String toolbarTitle = "";
    private Step mStep;

    private boolean mediaCurrentState;
    private long mediaCurrentPosition;

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_step);
        ButterKnife.bind(this);

        if (saveInstanceState == null) {
            if (getIntent().getExtras() != null) {
                Bundle bundle = getIntent().getExtras();
                mStep = bundle.getParcelable(Constants.STEP_CONTENT);
                toolbarTitle = bundle.getString(Constants.RECIPE_NAME);

                settingToolbar();

                StepFragment stepFragment = new StepFragment();

                stepFragment.setArguments(argumentsToFragment());
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.detail_container, stepFragment)
                        .commit();
            }
        }
    }

    public void settingToolbar() {
        mToolbar.setTitle(toolbarTitle);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private Bundle argumentsToFragment() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.STEP_CONTENT, mStep);
        bundle.putBoolean(Constants.MEDIA_CURRENT_STATE, mediaCurrentState);
        bundle.putLong(Constants.MEDIA_CURRENT_POSITION, mediaCurrentPosition);
        return bundle;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putLong(Constants.MEDIA_CURRENT_POSITION, mediaCurrentPosition);
        savedInstanceState.putBoolean(Constants.MEDIA_CURRENT_STATE, mediaCurrentState);
        savedInstanceState.putParcelable(Constants.STEP_CONTENT, mStep);
        savedInstanceState.putString(Constants.RECIPE_NAME, toolbarTitle);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mStep = savedInstanceState.getParcelable(Constants.STEP_CONTENT);
        toolbarTitle = savedInstanceState.getString(Constants.RECIPE_NAME);
        mediaCurrentState = savedInstanceState.getBoolean(Constants.MEDIA_CURRENT_STATE);
        mediaCurrentPosition = savedInstanceState.getLong(Constants.MEDIA_CURRENT_POSITION);

        settingToolbar();

        StepFragment stepFragment = new StepFragment();

        stepFragment.setArguments(argumentsToFragment());
        getSupportFragmentManager().beginTransaction()
                .add(R.id.detail_container, stepFragment)
                .commit();
    }

    @Override
    public void currentPosition(long currentPosition) {
        this.mediaCurrentPosition = currentPosition;
    }

    @Override
    public void currentMediaState(boolean mediaState) {
        this.mediaCurrentState = mediaState;
    }
}
