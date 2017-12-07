package com.example.ccl.Cookiary.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ccl.Cookiary.DetailActivity;
import com.example.ccl.Cookiary.R;
import com.example.ccl.Cookiary.RecipeItemClickListener;
import com.example.ccl.Cookiary.data.CookiaryDbHelper;
import com.example.ccl.Cookiary.model.IngredientUsage;
import com.example.ccl.Cookiary.model.Recipe;

import java.util.ArrayList;
import java.util.List;


public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private List<Recipe> mRecipeList = new ArrayList<>();
    private final RecipeItemClickListener mRecipeItemClickListener;
    boolean hasDescShown = false;

    // Provide a suitable constructor (depends on the kind of dataset)
    public RecipeAdapter(List<Recipe> recipeList, RecipeItemClickListener recipeItemClickListener) {
        mRecipeList = recipeList;
        mRecipeItemClickListener = recipeItemClickListener;
    }

    // Provide a reference to the views for each data item
    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
        int mId;
        Context mContext;
        CardView mCardView;
        TextView mName;
        TextView mDescription;
        TextView mCategory;
        ImageView mDishPhoto;


        RecipeViewHolder(View itemView) {
            super(itemView);
            mContext = itemView.getContext();
            mCardView = itemView.findViewById(R.id.recipe_card_view);
            mName = itemView.findViewById(R.id.name_text_view);
            mDescription = itemView.findViewById(R.id.description_text_view);
            mCategory = itemView.findViewById(R.id.dish_category_text_view);
            mDishPhoto = itemView.findViewById(R.id.thumb_nail);
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new RecipeViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final RecipeViewHolder holder, final int position) {
        // get element from recipeList at this position and replace the contents of the view with that element
        final Recipe recipe = mRecipeList.get(position);
        holder.mId = mRecipeList.get(position).getRecipe_id();
        holder.mName.setText(mRecipeList.get(position).getName());
        holder.mDescription.setText(mRecipeList.get(position).getDescription());
        holder.mCategory.setText(mRecipeList.get(position).getCategory());
        holder.mDishPhoto.setImageResource(mRecipeList.get(position).getImageResourceId());

        ViewCompat.setTransitionName(holder.mDishPhoto, String.valueOf(recipe.getImageResourceId()));

        // set the onclick listener for dish thumbnail
        holder.mDishPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecipeItemClickListener.onRecipeItemClick(holder.getAdapterPosition(), recipe, holder.mDishPhoto);
            }
        });

        // show description if the photo is long-clicked
        holder.mDishPhoto.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (!hasDescShown) {
                    holder.mDescription.setVisibility(View.VISIBLE);
                    hasDescShown = true;
                }
                else {
                    holder.mDescription.setVisibility(View.GONE);
                    hasDescShown = false;
                }
                return true;
            }
        });
    }

    // Return the size of the recipeList
    @Override
    public int getItemCount() {
        return mRecipeList.size();
    }


    public void removeRecipe(int position) {
        mRecipeList.remove(position);
        Log.v("RecipeAdapter", "List Position: "
                + ", Adapter Position: " + position);
        notifyItemRemoved(position);
    }
}
