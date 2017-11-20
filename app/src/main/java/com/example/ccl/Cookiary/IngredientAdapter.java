package com.example.ccl.Cookiary;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ccl.Cookiary.Model.Ingredient;
import com.example.ccl.Cookiary.Model.IngredientUsage;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Chun-Chieh Liang
 * 11/19/17.
 */

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {
    final int VIEW_TYPE_INGREDIENT= 0;
    final int VIEW_TYPE_INGREDIENT_USAGE = 1;
    Context mContext;

    private List<IngredientUsage> mIngredientUsageList = new ArrayList<>();
    boolean hasDescShown = false;

    public IngredientAdapter(List<IngredientUsage> ingredientUsageList) {
        mIngredientUsageList = ingredientUsageList;
    }


    // Provide a reference to the views for each data item
    public static class IngredientViewHolder extends RecyclerView.ViewHolder {
        int mId;
        Context mContext;
        CardView mCardView;
        TextView mName;
        TextView mQuantity;
        TextView mMeasurement;

        IngredientViewHolder(View itemView) {
            super(itemView);
            mContext = itemView.getContext();
            mCardView = itemView.findViewById(R.id.ingredient_card_view);
            mName = itemView.findViewById(R.id.ingredient_name_text_view);
            mQuantity = itemView.findViewById(R.id.ingredient_quantity_text_view);
            mMeasurement = itemView.findViewById(R.id.ingredient_measurement_text_view);
        }
    }

    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_ingredient, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new IngredientAdapter.IngredientViewHolder(v);
    }

    @Override
    public void onBindViewHolder(IngredientViewHolder holder, int position) {
        holder.mId = mIngredientUsageList.get(position).getId();
        holder.mName.setText(mIngredientUsageList.get(position).getIngredientName());
        holder.mQuantity.setText(String.valueOf(mIngredientUsageList.get(position).getQuantity()));
        holder.mMeasurement.setText(mIngredientUsageList.get(position).getMeasurement());
    }

    @Override
    public int getItemCount() {
        return mIngredientUsageList.size();
    }
}
