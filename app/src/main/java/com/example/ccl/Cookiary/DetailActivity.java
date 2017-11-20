package com.example.ccl.Cookiary;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.ccl.Cookiary.Model.Ingredient;
import com.example.ccl.Cookiary.Model.IngredientUsage;
import com.example.ccl.Cookiary.Model.Recipe;
import com.example.ccl.Cookiary.data.CookiaryDbHelper;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private TextView mCookingTimeTextView, mServingsTextView, mDifficultyTextView;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private RecyclerView mIngredientRecyclerView, mDirectionRecyclerView;
    private RecyclerView.Adapter mIngredientAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String mTitle;
    private int mId;

    // testing textView
    private TextView mNoIngredientTextView;


    /**
     * Initialize the layout and components
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        mIngredientRecyclerView = (RecyclerView) findViewById(R.id.ingredient_recycler_view);
        mDirectionRecyclerView = (RecyclerView) findViewById(R.id.direction_recycler_view);
        mCollapsingToolbarLayout = findViewById(R.id.toolbar_layout);
        mCookingTimeTextView = findViewById(R.id.cookingtime_text_view);
        mServingsTextView = findViewById(R.id.servings_text_view);
        mDifficultyTextView = findViewById(R.id.difficulty_text_view);

        mNoIngredientTextView = findViewById(R.id.no_ingredient_text_view);

        setSupportActionBar(toolbar);

        mId = getIntent().getIntExtra("Recipe ID", -1);
        if (mId == -1) {
            Log.e("DetailActivity", "Error! The recipe is not found");
        } else {
            // do nothing
        }


        mCollapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.BLACK);

        mIngredientRecyclerView.setHasFixedSize(true);
        mDirectionRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);

//        mDirectionRecyclerView.setLayoutManager(mLayoutManager);


    }

    @Override
    protected void onStart() {
        super.onStart();
        showRecipeDetail();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_recipe_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            case R.id.action_edit:
                Intent intentUpdateDetail = new Intent(DetailActivity.this, UpdateDetailActivity.class);
                intentUpdateDetail.putExtra("Recipe ID", mId);
                startActivity(intentUpdateDetail);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showRecipeDetail() {
        // get the overall information of the recipe
        Recipe myRecipe;
        CookiaryDbHelper db = new CookiaryDbHelper(this);
        myRecipe = db.getRecipe(mId);

        String servingsString = myRecipe.getYield()+ " Servings";

        mCollapsingToolbarLayout.setTitle(myRecipe.getName());
        mCookingTimeTextView.setText(myRecipe.getCookingTime());
        mServingsTextView.setText(servingsString);
        mDifficultyTextView.setText(myRecipe.getDifficulty());


        // convert the ingredient id to the name
        List<IngredientUsage> ingredientUsage;
        ingredientUsage = db.getRecipeIngredientsUsage(mId);
        if (ingredientUsage.size() == 0) {
            mNoIngredientTextView.setText("No Ingredients!");
            mNoIngredientTextView.setVisibility(View.VISIBLE);
        } else {
            mNoIngredientTextView.setVisibility(View.GONE);
            mIngredientRecyclerView.setLayoutManager(mLayoutManager);
            mIngredientAdapter = new IngredientAdapter(ingredientUsage);
            mIngredientRecyclerView.setAdapter(mIngredientAdapter);
        }
    }
}
