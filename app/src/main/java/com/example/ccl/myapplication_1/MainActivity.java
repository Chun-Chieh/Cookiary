package com.example.ccl.myapplication_1;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

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


        // Create an ArrayList of words
        List<Recipe> myRecipes;
        DBHelper db = new DBHelper(this);
        myRecipes = db.getAllRecipes();

        // specify the adapter
        mAdapter = new RecipeAdapter(myRecipes);
        mContentRecyclerView.setAdapter(mAdapter);

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
                Intent i = new Intent(MainActivity.this, CreateNewRecipe.class);
                startActivity(i);
            }
        });

    }

    /**
     * show a snackbar with custom text messages
     * @param message the text to show
     */
    private void showMsg(String message) {
        Snackbar.make(findViewById(R.id.coordinator_layout_view), message, Snackbar.LENGTH_SHORT).show();
    }
}
