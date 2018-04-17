package com.example.baeza.bakingapp.ui.utility;

import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;

import com.example.baeza.bakingapp.R;
import com.example.baeza.bakingapp.ui.data.Recipe;
import com.example.baeza.bakingapp.ui.view.MainContentActivity;

import java.util.List;

public class SelectRecipeAdapter extends RecyclerView.Adapter<SelectRecipeAdapter.ViewHolder> {

    private List<Recipe> recipeList;
    private Context context;

    public SelectRecipeAdapter(Context context, List<Recipe> recipeList) {
        this.recipeList = recipeList;
        this.context = context;
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

            Toast.makeText(context, "clicked "+recipeList.get(clickedPosition).getName(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, MainContentActivity.class);
            intent.putExtra(Constants.RECIPE_KEY, recipeList.get(clickedPosition));
            context.startActivity(intent);
        }
    }
}
