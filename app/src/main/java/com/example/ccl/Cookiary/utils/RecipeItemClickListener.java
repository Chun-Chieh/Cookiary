package com.example.ccl.Cookiary.utils;

import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.example.ccl.Cookiary.model.Recipe;

/**
 * @author Chun-Chieh Liang
 * Last edit: 12/7/17.
 */

public class RecipeItemClickListener implements RecyclerView.OnItemTouchListener{

    public static interface OnItemClickListener {
        public void onRecipeItemClick(int pos, Recipe recipe, ImageView shareImageView);
        public void onItemLongClick(View view, int position);
    }



    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
