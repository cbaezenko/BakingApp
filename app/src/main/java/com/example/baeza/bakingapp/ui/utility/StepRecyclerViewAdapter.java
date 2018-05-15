package com.example.baeza.bakingapp.ui.utility;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.baeza.bakingapp.R;
import com.example.baeza.bakingapp.ui.data.Step;
import com.example.baeza.bakingapp.ui.view.StepActivity;

import java.util.List;

public class StepRecyclerViewAdapter extends RecyclerView.Adapter<StepRecyclerViewAdapter.ViewHolder> {

    private List <Step> stepList;
    private  Context context;
    private String recipeName;
    private boolean mTwoPane;

    private ListenStep mListenStep;

    public interface ListenStep{
        void onRecipeClicked(int item);
    }

    public StepRecyclerViewAdapter(Context context,
                                   List<Step> stepList,
                                   String recipeName,
                                   boolean twoPane,
                                   ListenStep listenStep ){
        this.context = context;
        this.stepList = stepList;
        this.recipeName = recipeName;
        this.mTwoPane = twoPane;

        mListenStep = listenStep;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout = R.layout.item_rv_steps;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
         holder.texStep.setText(context.getString(R.string.step) +" "+ position+ " "+stepList.get(position).getShortDescription());
    }

    @Override
    public int getItemCount() {
        return stepList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView texStep;

        public ViewHolder(View itemView) {
            super(itemView);
            texStep = itemView.findViewById(R.id.text_step);
            texStep.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();

            Bundle bundle = new Bundle();
            bundle.putParcelable(Constants.STEP_CONTENT, stepList.get(clickedPosition));
            bundle.putString(Constants.RECIPE_NAME, recipeName);

            Intent intent;
            if(!mTwoPane){
                intent = new Intent(context, StepActivity.class);
                intent.putExtras(bundle);
                context.startActivity(intent);}
            else{
                mListenStep.onRecipeClicked(clickedPosition);
            }
        }
    }
}
