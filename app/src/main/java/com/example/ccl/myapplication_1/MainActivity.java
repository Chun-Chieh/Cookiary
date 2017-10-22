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

public class MainActivity extends AppCompatActivity {
    private RecyclerView mContentRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

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
        ArrayList<Recipe> myRecipes = new ArrayList<>();

        myRecipes.add(new Recipe("Burger", "Lorem ipsum dolor sit amet, consectetuer adiLorem ipsum dolor sit amet, consectetuer", R.drawable.burger));
        myRecipes.add(new Recipe("French Fries", "They are batonnet or allumette-cut deep-fried potatoes", R.drawable.burger));

        // specify the adapter
        mAdapter = new RecipeAdapter(myRecipes);
        mContentRecyclerView.setAdapter(mAdapter);



        FloatingActionButton fab =  (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //showMsg("Click!!");
                Intent i = new Intent(MainActivity.this, CreateNewRecipe.class);
                startActivity(i);
            }
        });

    }

    private void showMsg(String message) {
        Snackbar.make(findViewById(R.id.coordinator_layout_view), message, Snackbar.LENGTH_SHORT).show();
    }
}
