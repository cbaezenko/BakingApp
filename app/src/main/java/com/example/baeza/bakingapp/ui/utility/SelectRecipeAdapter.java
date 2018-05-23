package com.example.baeza.bakingapp.ui.utility;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
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
//    private FavoriteRecipe mFavoriteRecipe;
    private CoordinatorLayout mCoordinatorLayout;

    public SelectRecipeAdapter(Context context, List<Recipe> recipeList, CoordinatorLayout coordinatorLayout) {
        this.recipeList = recipeList;
        this.context = context;
        this.mCoordinatorLayout = coordinatorLayout;

//        mFavoriteRecipe = new FavoriteRecipe(context);

        //images url for the recipes, Nutella Pie, Brownies, Yellow Cake, Cheesecake.
        imageArrays.add("https://images.pexels.com/photos/14107/pexels-photo-14107.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260");
        imageArrays.add("https://images.pexels.com/photos/45202/brownie-dessert-cake-sweet-45202.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260");
        imageArrays.add("https://images.pexels.com/photos/6600/food-plate-coffee-cup.jpg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260");
        imageArrays.add("https://images.pexels.com/photos/557662/pexels-photo-557662.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260");
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
        holder.tvPortion.setText(String.format("%d", recipeList.get(position).getServings()));

//        if ((mFavoriteRecipe.getRecipeIdFromPref()+1) == recipeList.get(position).getId()) {
//            holder.imageButton_favorite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_yellow_24dp));
//        } else if ((mFavoriteRecipe.getRecipeIdFromPref() +1) != recipeList.get(position).getId()) {
//            holder.imageButton_favorite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_border_yellow_24dp));
//        }

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
        TextView cardTitle, tvPortion;
        Button buttonShow;
        ImageButton imageButton_favorite;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            cardImage = itemView.findViewById(R.id.card_image);
            cardTitle = itemView.findViewById(R.id.card_title);
            buttonShow = itemView.findViewById(R.id.button_ingredient);
            tvPortion = itemView.findViewById(R.id.tv_portion);
            itemView.setOnClickListener(this);
//            imageButton_favorite = itemView.findViewById(R.id.imageButton_favorite);
//
//            imageButton_favorite.setOnClickListener(this);
//            buttonShow.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

//            if (view.getId() == R.id.imageButton_favorite) {
//                mFavoriteRecipe.saveRecipeIdToPref(getAdapterPosition());
//                mFavoriteRecipe.saveRecipeNameToPref(recipeList.get(getAdapterPosition()).getName());
//                showSnack();
//                notifyDataSetChanged();
//            } else {

                int clickedPosition = getAdapterPosition();

                Bundle bundle = new Bundle();
                bundle.putParcelable(Constants.RECIPE_KEY, recipeList.get(clickedPosition));
                bundle.putParcelableArrayList(Constants.STEP_LIST_KEY, (ArrayList<? extends Parcelable>) recipeList.get(clickedPosition).getSteps());
                bundle.putParcelableArrayList(Constants.INGREDIENT_LIST_KEY, (ArrayList<? extends Parcelable>) recipeList.get(clickedPosition).getIngredients());

                Intent intent = new Intent(context, MainContentActivity.class);
                intent.putExtra(Constants.RECIPE_KEY, bundle);
                context.startActivity(intent);
//            }
        }
    }

//    private void showSnack(){
//        Snackbar snackbar = Snackbar
//                .make(mCoordinatorLayout, context.getString(R.string.saved_to_widget), Snackbar.LENGTH_LONG);
//        snackbar.show();
//    }
}
