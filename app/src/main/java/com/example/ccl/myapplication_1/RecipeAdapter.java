package com.example.ccl.myapplication_1;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private Context mContext;
    private ArrayList<Recipe> mRecipeList = new ArrayList<>();

    // Provide a reference to the views for each data item
    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
        CardView mCardView;
        TextView mName;
        TextView mDescription;
        ImageView mDishPhoto;

        RecipeViewHolder(View itemView) {
            super(itemView);
            mCardView = itemView.findViewById(R.id.recipe_card_view);
            mName = itemView.findViewById(R.id.name_text_view);
            mDescription = itemView.findViewById(R.id.description_text_view);
            mDishPhoto = itemView.findViewById(R.id.thumb_nail);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RecipeAdapter(ArrayList<Recipe> recipeList) {
        mRecipeList = recipeList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        RecipeViewHolder rvh = new RecipeViewHolder(v);
        return rvh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        // get element from recipeList at this position and replace the contents of the view with that element
        holder.mName.setText(mRecipeList.get(position).getName());
        holder.mDescription.setText(mRecipeList.get(position).getDescription());
        holder.mDishPhoto.setImageResource(mRecipeList.get(position).getImageResourceId());

    }

    // Return the size of the recipeList
    @Override
    public int getItemCount() {
        return mRecipeList.size();
    }
}
