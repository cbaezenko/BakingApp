package com.example.baeza.bakingapp.ui.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.example.baeza.bakingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientActivity extends AppCompatActivity {

    @BindView(R.id.container)
    FrameLayout mFrameLayout;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);

        ButterKnife.bind(this);

        IngredientFragment ingredientFragment = new IngredientFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, ingredientFragment)
                .commit();

    }
}
