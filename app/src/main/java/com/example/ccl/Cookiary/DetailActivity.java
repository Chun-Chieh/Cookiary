package com.example.ccl.Cookiary;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ccl.Cookiary.adapter.DirectionAdapter;
import com.example.ccl.Cookiary.adapter.IngredientAdapter;
import com.example.ccl.Cookiary.model.Direction;
import com.example.ccl.Cookiary.model.IngredientUsage;
import com.example.ccl.Cookiary.model.Recipe;
import com.example.ccl.Cookiary.data.CookiaryDbHelper;

import java.util.List;

public class DetailActivity extends AppCompatActivity{

    private ImageView mPhotoImageView;
    private TextView mCookingTimeTextView, mServingsTextView, mDifficultyTextView;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private RecyclerView mIngredientRecyclerView, mDirectionRecyclerView;
    private RecyclerView.Adapter mIngredientAdapter, mDirectionAdapter;
    private RecyclerView.LayoutManager mIngredientLayoutManager, mDirectionLayoutManager;

    private String mTitle;
    private int mId;
    private String mImageTransitionName;
    private List<IngredientUsage> mIngredientUsageList;

    // textViews for no existing data
    private TextView mNoIngredientTextView, mNoDirectionTextView;


    /**
     * Initialize the layout and components
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

//        supportPostponeEnterTransition();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mIngredientRecyclerView = (RecyclerView) findViewById(R.id.ingredient_recycler_view);
        mDirectionRecyclerView = (RecyclerView) findViewById(R.id.direction_recycler_view);
        mCollapsingToolbarLayout = findViewById(R.id.toolbar_layout);
        mCookingTimeTextView = findViewById(R.id.cookingtime_text_view);
        mServingsTextView = findViewById(R.id.servings_text_view);
        mDifficultyTextView = findViewById(R.id.difficulty_text_view);
        mPhotoImageView = findViewById(R.id.photo_image_view);
        mNoIngredientTextView = findViewById(R.id.no_ingredient_text_view);
        mNoDirectionTextView = findViewById(R.id.no_direction_text_view);


        mImageTransitionName = getIntent().getStringExtra(MainActivity.EXTRA_RECIPE_IMAGE_TRANSITION_NAME);

        mId = getIntent().getIntExtra(MainActivity.EXTRA_RECIPE_ID, -1);
        if (mId == -1) {
            Log.e("DetailActivity", "Error! The recipe is not found");
        } else {
            // do nothing
        }


        mCollapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.BLACK);

        mIngredientRecyclerView.setHasFixedSize(true);
        mIngredientRecyclerView.setNestedScrollingEnabled(false);
        mIngredientRecyclerView.setItemAnimator(new DefaultItemAnimator());
        
        mDirectionRecyclerView.setHasFixedSize(true);
        mDirectionRecyclerView.setNestedScrollingEnabled(false);
        mDirectionRecyclerView.setItemAnimator(new DefaultItemAnimator());


        mIngredientLayoutManager = new LinearLayoutManager(this);
        mDirectionLayoutManager = new LinearLayoutManager(this);
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
        switch (item.getItemId()) {
            case R.id.action_edit:
                // action edit does three things:
                // 1. Edit the overall information
                // 2. Add a new ingredient for the recipe or edit a existing ingredient
                // 3. Add a new direction for the recipe
                Intent intentUpdateDetail = new Intent(DetailActivity.this, UpdateDetailActivity.class);
                intentUpdateDetail.putExtra("Recipe ID", mId);
                startActivity(intentUpdateDetail);
                return true;

            case R.id.action_insert_dummy_details:
                // insert a ingredient and a direction
                CookiaryDbHelper db = new CookiaryDbHelper(this);
                db.updateRecipeOverall(mId,
                        getResources().getString(R.string.dummy_recipe_name),
                        getResources().getString(R.string.dummy_cooking_time),
                        Integer.parseInt(getResources().getString(R.string.dummy_yield)),
                        getResources().getString(R.string.dummy_difficulty));
                db.updateRecipeIngredients(mId,
                        "Onion",
                        2,
                        "teaspoon");
                db.addRecipeDirection(mId, getResources().getString(R.string.dummy_description));
                db.close();
                showRecipeDetail();
                return true;

            case R.id.action_delete_recipe:
                deleteRecipe();

                Intent intentDeleteSuccessful = new Intent(DetailActivity.this, MainActivity.class);
                startActivity(intentDeleteSuccessful);
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
        mPhotoImageView.setImageResource(myRecipe.getImageResourceId());

        // set transition
//        mPhotoImageView.setTransitionName(mImageTransitionName);



        Log.v("Detail Activity", mId + ", " + mImageTransitionName);


        List<IngredientUsage> ingredientUsageList;

        // get ingredients used by recipe id
        ingredientUsageList = db.getRecipeIngredientsUsage(mId);

        // show "no ingredients" on view when the ingredient list is empty
        if (ingredientUsageList.size() == 0) {
            mNoIngredientTextView.setText(R.string.no_ingredient_text_view);
            mNoIngredientTextView.setVisibility(View.VISIBLE);
        } else {
            mNoIngredientTextView.setVisibility(View.GONE);
            mIngredientRecyclerView.setLayoutManager(mIngredientLayoutManager);
            mIngredientAdapter = new IngredientAdapter(ingredientUsageList);
            mIngredientRecyclerView.setAdapter(mIngredientAdapter);

        }

        List<Direction> directionList;

        // get directions by recipe id
        directionList = db.getRecipeDirections(mId);

        if (directionList.size() == 0) {
            mNoDirectionTextView.setText(R.string.no_direction_text_view);
            mNoDirectionTextView.setVisibility(View.VISIBLE);
        } else {
            mNoDirectionTextView.setVisibility(View.GONE);
            mDirectionRecyclerView.setLayoutManager(mDirectionLayoutManager);
            mDirectionAdapter = new DirectionAdapter(directionList);
            mDirectionRecyclerView.setAdapter(mDirectionAdapter);
        }
    }

    private void deleteRecipe(){
        CookiaryDbHelper db = new CookiaryDbHelper(this);
        db.deleteRecipe(mId);

    }
}
