package com.example.baeza.bakingapp.ui.utility;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.baeza.bakingapp.R;
import com.example.baeza.bakingapp.ui.data.Ingredient;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    private List<Ingredient> mIngredientList;

    public IngredientAdapter(List<Ingredient> ingredientList){
        this.mIngredientList = ingredientList;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layout = R.layout.item_ingredient;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layout, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        holder.tv_measure.setText(mIngredientList.get(position).getMeasure());
        holder.tv_quantity.setText(""+mIngredientList.get(position).getQuantity());
        holder.ingredient.setText(mIngredientList.get(position).getIngredient());
    }

    @Override
    public int getItemCount() {
        if(mIngredientList == null || mIngredientList.size() == 0) return 0;
        return mIngredientList.size();
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder {
        TextView tv_quantity, tv_measure, ingredient;
        public IngredientViewHolder(View itemView) {
            super(itemView);
            tv_quantity = itemView.findViewById(R.id.tv_quantity);
            tv_measure = itemView.findViewById(R.id.tv_measure);
            ingredient = itemView.findViewById(R.id.ingredient);
        }
    }
}
