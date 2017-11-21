package com.example.ccl.Cookiary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ccl.Cookiary.Model.Ingredient;
import com.example.ccl.Cookiary.Model.Recipe;
import com.example.ccl.Cookiary.data.CookiaryDbHelper;

public class UpdateDetailActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private EditText mNameEditText, mTimeEditText,  mServingsEditText, mDifficultyEditText;
    private EditText mIngredientNameEditText, mIngredientQuantityEditText;
    private Spinner mIngredientMeasurementSpinner;
    private EditText mDirection;


    private int mRecipeId;
    private String mMeasurement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_detail);

        mNameEditText = findViewById(R.id.edit_recipe_name);
        mTimeEditText = findViewById(R.id.edit_recipe_time);
        mServingsEditText = findViewById(R.id.edit_recipe_servings);
        mDifficultyEditText = findViewById(R.id.edit_recipe_difficulty);
        mIngredientNameEditText = findViewById(R.id.edit_ingredient_name);
        mIngredientQuantityEditText = findViewById(R.id.edit_ingredient_quantity);
        mIngredientMeasurementSpinner = findViewById(R.id.spinner_ingredient_unit);
        mDirection= findViewById(R.id.edit_recipe_direction);

        // get recipe id from detail activity
        mRecipeId = getIntent().getIntExtra("Recipe ID", -1);

        // setup ingredient measurement spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.measurement,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mIngredientMeasurementSpinner.setAdapter(adapter);
        mIngredientMeasurementSpinner.setOnItemSelectedListener(this);

        loadRecipeInformation();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    // --------------------------------------- spinner --------------------------------------- //
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mMeasurement = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_update_detail, menu);
        return true;
    }

    // ----------------------------------------- menu ----------------------------------------- //
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                saveUpdate();
                Toast.makeText(this, "Save", Toast.LENGTH_SHORT).show();
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadRecipeInformation(){
        Recipe myRecipe;
        CookiaryDbHelper db = new CookiaryDbHelper(this);
        myRecipe = db.getRecipe(mRecipeId);
        mNameEditText.setText(myRecipe.getName());
        mTimeEditText.setText(myRecipe.getCookingTime());
        mServingsEditText.setText(String.valueOf(myRecipe.getYield()));
        mDifficultyEditText.setText(myRecipe.getDifficulty());
        db.close();
    }

    private void saveUpdate(){
        // overall information
        String name = mNameEditText.getText().toString();
        String cookingTime = mTimeEditText.getText().toString();
        String servings = mServingsEditText.getText().toString();
        String difficulty = mDifficultyEditText.getText().toString();
        CookiaryDbHelper db = new CookiaryDbHelper(this);
        db.updateRecipeOverall(mRecipeId, name, cookingTime, Integer.parseInt(servings), difficulty);


        // ingredient information
        String ingredientName = mIngredientNameEditText.getText().toString();
        // if the name or quantity of the ingredient is empty, make toast;
        // Otherwise, call updateRecipeIngredients()
        if (mIngredientNameEditText.getText().toString().equals("") || mIngredientQuantityEditText.getText().toString().equals("")){
            Toast.makeText(UpdateDetailActivity.this, "No ingredient has changed", Toast.LENGTH_SHORT).show();
        } else {
            int ingredientQuantity = Integer.parseInt(mIngredientQuantityEditText.getText().toString());
            db.updateRecipeIngredients(mRecipeId, ingredientName, ingredientQuantity, mMeasurement);
        }

        // direction information
        String direction = mDirection.getText().toString();
        // if the name or quantity of the ingredient is empty, make toast;
        // Otherwise, call updateRecipeDirection()
        if (mDirection.getText().toString().equals("") || mDirection.getText().toString().equals("")){
            Toast.makeText(UpdateDetailActivity.this, "No direction has changed", Toast.LENGTH_SHORT).show();
        } else {
            db.addRecipeDirection(mRecipeId, direction);
        }

        db.close();
    }


}
