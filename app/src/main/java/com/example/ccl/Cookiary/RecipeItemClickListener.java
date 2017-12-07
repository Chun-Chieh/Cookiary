package com.example.ccl.Cookiary;

import android.widget.ImageView;

import com.example.ccl.Cookiary.model.Recipe;

/**
 * @author Chun-Chieh Liang
 * 11/23/17.
 */

public interface RecipeItemClickListener {
    void onRecipeItemClick(int pos, Recipe recipe, ImageView shareImageView);
}
