package com.example.ccl.Cookiary.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ccl.Cookiary.R;
import com.example.ccl.Cookiary.model.Ingredient;
import com.example.ccl.Cookiary.model.IngredientUsage;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Chun-Chieh Liang
 *         11/19/17.
 */

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {
    final int VIEW_TYPE_INGREDIENT = 0;
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
        TextView mName;
        TextView mQuantity;
        TextView mMeasurement;
        public RelativeLayout viewBackground;
        public LinearLayout viewForeground;

        public IngredientViewHolder(View itemView) {
            super(itemView);
            mContext = itemView.getContext();
            mName = itemView.findViewById(R.id.ingredient_name_text_view);
            mQuantity = itemView.findViewById(R.id.ingredient_quantity_text_view);
            mMeasurement = itemView.findViewById(R.id.ingredient_measurement_text_view);
            viewBackground = itemView.findViewById(R.id.view_background);
            viewForeground = itemView.findViewById(R.id.view_foreground);
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


    /**
     * Notify the item removed by position to perform recycler view delete animations
     * @param position
     */
    public void removeIngredient(int position) {
        mIngredientUsageList.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreIngredient(IngredientUsage ingredienUsagetItem, int position) {
        mIngredientUsageList.add(position, ingredienUsagetItem);
        notifyItemInserted(position);
    }
}
