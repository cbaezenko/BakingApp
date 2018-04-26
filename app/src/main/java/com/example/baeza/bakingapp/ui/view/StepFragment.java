package com.example.baeza.bakingapp.ui.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.baeza.bakingapp.R;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepFragment extends Fragment {

    @BindView(R.id.exo_player)
    SimpleExoPlayerView mSimpleExoPlayerView;
    @BindView(R.id.tv_short_description)
    TextView tvShortDescription;
    @BindView(R.id.tv_current_step)
    TextView tvCurrentStep;
    @BindView(R.id.tv_description)
    TextView tvDescription;

    public StepFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        int layout = R.layout.fragment_step;
        View rootView = inflater.inflate(layout, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
