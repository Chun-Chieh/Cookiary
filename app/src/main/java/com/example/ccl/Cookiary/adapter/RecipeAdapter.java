package com.example.ccl.Cookiary.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ccl.Cookiary.R;
import com.example.ccl.Cookiary.utils.RecipeItemClickListener;
import com.example.ccl.Cookiary.model.Recipe;

import java.util.ArrayList;
import java.util.List;


public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> implements Filterable {

    private List<Recipe> mRecipeList = new ArrayList<>();
    private List<Recipe> mRecipeListFiltered;
    private final RecipeItemClickListener.OnItemClickListener mRecipeItemClickListener;
    private boolean hasDescShown = false;

    // Provide a suitable constructor (depends on the kind of dataset)
    public RecipeAdapter(List<Recipe> recipeList, RecipeItemClickListener.OnItemClickListener recipeItemClickListener) {
        mRecipeList = recipeList;
        mRecipeListFiltered = recipeList;
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
        ImageView mExpand;


        RecipeViewHolder(View itemView) {
            super(itemView);
            mContext = itemView.getContext();
            mCardView = itemView.findViewById(R.id.recipe_card_view);
            mName = itemView.findViewById(R.id.name_text_view);
            mDescription = itemView.findViewById(R.id.description_text_view);
            mCategory = itemView.findViewById(R.id.dish_category_text_view);
            mDishPhoto = itemView.findViewById(R.id.thumb_nail);
            mExpand = itemView.findViewById(R.id.expand_image_view);
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
        final Recipe recipe = mRecipeListFiltered.get(position);
        holder.mId = mRecipeListFiltered.get(position).getRecipe_id();
        holder.mName.setText(mRecipeListFiltered.get(position).getName());
        holder.mDescription.setText(mRecipeListFiltered.get(position).getDescription());
        holder.mCategory.setText(mRecipeListFiltered.get(position).getCategory());
        holder.mDishPhoto.setImageResource(mRecipeListFiltered.get(position).getImageResourceId());
        holder.mExpand.setImageResource(R.drawable.ic_expand_description);

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

        holder.mExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.mDescription.getVisibility() == View.GONE){
                    holder.mDescription.setVisibility(View.VISIBLE);
                    holder.mExpand.setImageResource(R.drawable.ic_collpase_description);
                } else {
                    holder.mDescription.setVisibility(View.GONE);
                    holder.mExpand.setImageResource(R.drawable.ic_expand_description);
                }
                ObjectAnimator animation = ObjectAnimator.ofInt(holder.mDescription, "maxLines", holder.mDescription.getMaxLines());
                animation.setDuration(200).start();
            }
        });
    }

    // Return the size of the recipeList
    @Override
    public int getItemCount() {
        return mRecipeListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mRecipeListFiltered = mRecipeList;
                } else {
                    List<Recipe> filteredList = new ArrayList<>();
                    for (Recipe row : mRecipeList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getName().toLowerCase().contains(charString.toLowerCase()) || row.getCategory().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    mRecipeListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mRecipeListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mRecipeListFiltered = (ArrayList<Recipe>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public void removeRecipe(int position) {
        mRecipeList.remove(position);
        Log.v("RecipeAdapter", "List Position: "
                + ", Adapter Position: " + position);
        notifyItemRemoved(position);
    }

}
