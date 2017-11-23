package com.example.ccl.Cookiary.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ccl.Cookiary.R;
import com.example.ccl.Cookiary.model.Direction;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Chun-Chieh Liang
 * 11/20/17.
 */

public class DirectionAdapter extends RecyclerView.Adapter<DirectionAdapter.DirectionViewHolder> {

    private List<Direction> mDirectionList = new ArrayList<>();

    public DirectionAdapter(List<Direction> directionList) {
        mDirectionList = directionList;
    }

    // Provide a reference to the views for each data item
    public static class DirectionViewHolder extends RecyclerView.ViewHolder {
        int mId;
        Context mContext;
        CardView mCardView;
        TextView mStep;
        TextView mDescription;

        DirectionViewHolder(View itemView) {
            super(itemView);
            mContext = itemView.getContext();
            mCardView = itemView.findViewById(R.id.direction_card_view);
            mStep = itemView.findViewById(R.id.direction_step_text_view);
            mDescription = itemView.findViewById(R.id.direction_description_text_view);
        }
    }

    @Override
    public DirectionAdapter.DirectionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_direction, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new DirectionAdapter.DirectionViewHolder(v);
    }

    @Override
    public void onBindViewHolder(DirectionAdapter.DirectionViewHolder holder, int position) {
        holder.mId = mDirectionList.get(position).getId();
        holder.mStep.setText(String.valueOf(mDirectionList.get(position).getStep()));
        holder.mDescription.setText(mDirectionList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return mDirectionList.size();
    }


    /**
     * Notify the item removed by position to perform recycler view delete animations
     * @param position
     */
    public void removeDirection(int position) {
        mDirectionList.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Direction directionItem, int position) {
        mDirectionList.add(position, directionItem);
        notifyItemInserted(position);
    }
}
