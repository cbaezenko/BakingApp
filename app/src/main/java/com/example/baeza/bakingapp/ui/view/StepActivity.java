package com.example.baeza.bakingapp.ui.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.baeza.bakingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_step);
        ButterKnife.bind(this);
        settingToolbar();

        StepFragment stepFragment =  new StepFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, stepFragment)
                .commit();
    }

    public void settingToolbar(){
        mToolbar.setTitle("TITLE");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
