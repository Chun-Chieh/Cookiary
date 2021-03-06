package com.example.ccl.Cookiary;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;

import com.example.ccl.Cookiary.adapter.RecipeAdapter;
import com.example.ccl.Cookiary.data.CookiaryDbHelper;
import com.example.ccl.Cookiary.model.Recipe;
import com.example.ccl.Cookiary.utils.RecipeItemClickListener;

import java.util.List;

import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity implements RecipeItemClickListener.OnItemClickListener{

    public static final String EXTRA_RECIPE_ID = "recipe_id";
    public static final String EXTRA_RECIPE_IMAGE_TRANSITION_NAME = "recipe_image_transition_name";

    private RecyclerView mContentRecyclerView;
    private RecipeAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private SearchView mSearchView;
    private List<Recipe> myRecipes;


    /**
     * initialize the layout (RecyclerView) and setup the adapter (RecipeAdapter)
     * read the database (Recipiary.db)
     * set up the intents (DetailActivity and CreateNewRecipe) for image and FAB
     *
     * @param savedInstanceState inherited from AppCompatActivity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContentRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mContentRecyclerView.setItemAnimator(new DefaultItemAnimator());

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mContentRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mContentRecyclerView.setLayoutManager(mLayoutManager);

        // load the recipes
        CookiaryDbHelper db = new CookiaryDbHelper(this);
        myRecipes = db.getAllRecipes();

        // srtup the adapter
        mAdapter = new RecipeAdapter(myRecipes, this);

        // prompt the message of creating a new recipe or reading the db successfully
        if (getIntent().hasExtra("New Recipe Prompt")) {
            String promptMsg = getIntent().getStringExtra("New Recipe Prompt");
            showMsg(promptMsg);
        } else if (getIntent().hasExtra("Delete Recipe Prompt")) {
            String promptMsg = getIntent().getStringExtra("Delete Recipe Prompt");
            showMsg(promptMsg);
        } else{
            showMsg("Initiate successfully!");
        }


        // set up the onClickListener for the fab
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.create_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, CreateNewRecipeActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }
        });

        runLayoutAnimation();
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayRecipe();
    }

    @Override
    public void onBackPressed() {
        // disable onBackPressed on MainActivity by not inheriting
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        mSearchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        mSearchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                mAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                CookiaryDbHelper db = new CookiaryDbHelper(this);
                db.addRecipe(new Recipe(getResources().getString(R.string.dummy_recipe_name),
                        getResources().getString(R.string.dummy_description),
                        "Main Dish",
                        R.raw.burger,
                        Integer.parseInt(getResources().getString(R.string.dummy_yield)),
                        getResources().getString(R.string.dummy_cooking_time),
                        getResources().getString(R.string.dummy_difficulty)));
                displayRecipe();
                return true;

            case R.id.action_search: {
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }



    /**
     * show a snackbar with custom text messages
     *
     * @param message the text to show
     */
    private void showMsg(String message) {
        Snackbar.make(findViewById(R.id.coordinator_layout_view), message, Snackbar.LENGTH_SHORT).show();
    }

    /**
     * show the cards of all recipes
     */
    private void displayRecipe() {
        CookiaryDbHelper db = new CookiaryDbHelper(this);
        myRecipes = db.getAllRecipes();
        // specify the adapter
        if (myRecipes.size() == 0) {
            findViewById(R.id.no_data_text_view).setVisibility(VISIBLE);
            Log.v("MainActivity", "No Data");
        } else {
            findViewById(R.id.no_data_text_view).setVisibility(View.GONE);
            mAdapter = new RecipeAdapter(myRecipes, this);
            mContentRecyclerView.setAdapter(mAdapter);
        }
    }

    private void runLayoutAnimation() {
        final Context context = mContentRecyclerView.getContext();
        final LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down);

        mContentRecyclerView.setLayoutAnimation(controller);
        mContentRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        mContentRecyclerView.getAdapter().notifyDataSetChanged();
        mContentRecyclerView.scheduleLayoutAnimation();
    }


    /**
     * On item click
     * @param pos
     * @param recipe
     * @param shareImageView
     */
    @Override
    public void onRecipeItemClick(int pos, Recipe recipe, ImageView shareImageView) {
        // Intent to DetailActivity
        Intent recipeDetail = new Intent(this, DetailActivity.class);

        // pass the id to Recipe Detail
        recipeDetail.putExtra(EXTRA_RECIPE_ID, recipe.getRecipe_id());
        recipeDetail.putExtra(EXTRA_RECIPE_IMAGE_TRANSITION_NAME, ViewCompat.getTransitionName(shareImageView));

        View statusBar = findViewById(android.R.id.statusBarBackground);
        View navigationBar = findViewById(android.R.id.navigationBarBackground);

//        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
//                this
//                , Pair.create((View)shareImageView, shareImageView.getTransitionName())
//                , Pair.create(statusBar, Window.STATUS_BAR_BACKGROUND_TRANSITION_NAME)
//                , Pair.create(navigationBar, Window.NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME));


//        startActivity(recipeDetail, options.toBundle());
        startActivity(recipeDetail);
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }


}
