package com.example.baeza.bakingapp.ui.utility;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.baeza.bakingapp.R;
import com.example.baeza.bakingapp.ui.data.Recipe;
import com.example.baeza.bakingapp.ui.view.MainContentActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SelectRecipeAdapter extends RecyclerView.Adapter<SelectRecipeAdapter.ViewHolder> {

    private List<Recipe> recipeList;
    private Context context;
    private ArrayList<String> imageArrays = new ArrayList<>();


    public SelectRecipeAdapter(Context context, List<Recipe> recipeList) {
        this.recipeList = recipeList;
        this.context = context;

        imageArrays.add("https://images.pexels.com/photos/14107/pexels-photo-14107.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260");
        imageArrays.add("https://images.pexels.com/photos/14107/pexels-photo-14107.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260");
        imageArrays.add("https://images.pexels.com/photos/14107/pexels-photo-14107.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260");
        imageArrays.add("https://images.pexels.com/photos/14107/pexels-photo-14107.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layout = R.layout.item_recycler_select_recipe;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.cardTitle.setText(recipeList.get(position).getName());
        holder.cardImage.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));

        Picasso.with(context)
                .load(imageArrays.get(position))
                .placeholder(R.drawable.rectangle)
                .error(R.drawable.rectangle)
                .into(holder.cardImage);
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView cardView;
        ImageView cardImage;
        TextView cardTitle;
        Button buttonShow;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            cardImage = itemView.findViewById(R.id.card_image);
            cardTitle = itemView.findViewById(R.id.card_title);
            buttonShow = itemView.findViewById(R.id.button_ingredient);
            buttonShow.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            int clickedPosition = getAdapterPosition();

            Bundle bundle = new Bundle();
            bundle.putParcelable(Constants.RECIPE_KEY, recipeList.get(clickedPosition));
            bundle.putParcelableArrayList(Constants.STEP_LIST_KEY, (ArrayList<? extends Parcelable>) recipeList.get(clickedPosition).getSteps());
            bundle.putParcelableArrayList(Constants.INGREDIENT_LIST_KEY, (ArrayList<? extends Parcelable>) recipeList.get(clickedPosition).getIngredients());

            Intent intent = new Intent(context, MainContentActivity.class);
            intent.putExtra(Constants.RECIPE_KEY, bundle);
            context.startActivity(intent);
        }
    }
}
