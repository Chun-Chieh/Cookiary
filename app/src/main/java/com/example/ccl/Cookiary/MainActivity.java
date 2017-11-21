package com.example.ccl.Cookiary;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.example.ccl.Cookiary.Model.Recipe;
import com.example.ccl.Cookiary.data.CookiaryDbHelper;

import java.util.List;

import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mContentRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    /**
     * initialize the layout (RecyclerView) and setup the adapter (RecipeAdapter)
     * read the database (Recipiary.db)
     * set up the intents (DetailActivity and CreateNewRecipe) for image and FAB
     * @param savedInstanceState inherited from AppCompatActivity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContentRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mContentRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mContentRecyclerView.setLayoutManager(mLayoutManager);

        // prompt the message of creating a new recipe or reading the db successfully
        if (getIntent().hasExtra("New Recipe Prompt")) {
            String promptMsg = getIntent().getStringExtra("New Recipe Prompt");
            showMsg(promptMsg);
        } else {
            showMsg("Initiate successfully!");
        }

        // set up the onClickListener for the fab
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.create_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showMsg("Click!!");
                Intent i = new Intent(MainActivity.this, CreateNewRecipeActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayRecipe();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                CookiaryDbHelper db = new CookiaryDbHelper(this);
                db.addRecipe(new Recipe("Burger",
                        "Tasty",
                        "Main Dish",
                        R.raw.burger,
                        2,
                        "1 hour",
                        "Easy"));
                displayRecipe();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    /**
     * show a snackbar with custom text messages
     * @param message the text to show
     */
    private void showMsg(String message) {
        Snackbar.make(findViewById(R.id.coordinator_layout_view), message, Snackbar.LENGTH_SHORT).show();
    }

    /**
     * show the cards of all recipes
     */
    private void displayRecipe() {
        // specify the adapter
        CookiaryDbHelper db = new CookiaryDbHelper(this);
        List<Recipe> myRecipes = db.getAllRecipes();

        if (myRecipes.size()==0) {
            findViewById(R.id.no_data_text_view).setVisibility(VISIBLE);
            Log.v("MainActivity", "No Data");
        } else {
            findViewById(R.id.no_data_text_view).setVisibility(View.GONE);
            mAdapter = new RecipeAdapter(myRecipes);
            mContentRecyclerView.setAdapter(mAdapter);
            runLayoutAnimation();
        }
    }

    private void runLayoutAnimation() {
        final Context context = mContentRecyclerView.getContext();
        final LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down);

        mContentRecyclerView.setLayoutAnimation(controller);
        mContentRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mContentRecyclerView.getAdapter().notifyDataSetChanged();
        mContentRecyclerView.scheduleLayoutAnimation();
    }
}
