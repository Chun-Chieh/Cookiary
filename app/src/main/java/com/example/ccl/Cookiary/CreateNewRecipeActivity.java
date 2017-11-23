package com.example.ccl.Cookiary;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.example.ccl.Cookiary.model.Recipe;
import com.example.ccl.Cookiary.data.CookiaryDbHelper;

/**
 * @author Chun-Chieh Liang
 *         Last update: Nov, 7, 2017
 */

public class CreateNewRecipeActivity extends AppCompatActivity {
    private TextInputLayout mNameTextInputLayout, mDescriptionTextInputLayout;
    private EditText mNameEditText, mDescriptionEditText;
    private AutoCompleteTextView mDishCategory;
    private Button mCreateButton;
    private String name, description, category;
    private int photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_recipe);

        mNameEditText = (EditText) findViewById(R.id.name_edit_text);
        mDescriptionEditText = (EditText) findViewById(R.id.description_edit_text);
        mNameTextInputLayout = (TextInputLayout) findViewById(R.id.name_til);
        mDescriptionTextInputLayout = (TextInputLayout) findViewById(R.id.description_til);
        mDishCategory = (AutoCompleteTextView) findViewById(R.id.category_autocomplete);

        String[] dish_categories = getResources().getStringArray(R.array.dish_category);

        // set up the autocomplete
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dish_categories);

        mDishCategory.setAdapter(adapter);
        mNameEditText.setOnFocusChangeListener(new MyOnFocusChangeListener(mNameEditText));
        mDescriptionEditText.setOnFocusChangeListener(new MyOnFocusChangeListener(mDescriptionEditText));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

        // animated transition
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new_recipe, menu);
        return true;
    }

    /**
     * do actions whether the name and description is valid or not
     *
     * @param item menu item
     * @return true if the item is clicked
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_create:
                if (isNameValid() && isDescValid()) {
                    // insert the recipe to the database and go to MainActivity with a prompt message
                    name = mNameEditText.getText().toString();
                    description = mDescriptionEditText.getText().toString();
                    category = mDishCategory.getText().toString();
                    insertRecipe();
                    Intent i = new Intent(CreateNewRecipeActivity.this, MainActivity.class);
                    i.putExtra("New Recipe Prompt", name + " has created successfully!");
                    startActivity(i);

                    // animated transition
                    overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                } else {
                    Snackbar.make(findViewById(R.id.create_new_recipe_form), "Check the error messages!", Snackbar.LENGTH_SHORT).show();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * check the name if it's valid (not null)
     *
     * @return false if the name is empty. Otherwise, return false.
     */
    public boolean isNameValid() {
        String name = mNameEditText.getText().toString();
        if (name.isEmpty()) {
            mNameTextInputLayout.setErrorEnabled(true);
            mNameTextInputLayout.setError("This field is required.");
            return false;
        } else {
            mNameTextInputLayout.setErrorEnabled(false);
            return true;
        }
    }

    /**
     * check the description if it is valid (less than 50 words)
     *
     * @return false if the string length is longer than 50. Otherwise, return true.
     */
    public boolean isDescValid() {
        String desc = mDescriptionEditText.getText().toString();
        if (desc.length() > 500) {
            mDescriptionTextInputLayout.setErrorEnabled(true);
            mDescriptionTextInputLayout.setError("The description must be less than 500 words.");
            return false;
        } else {
            mDescriptionTextInputLayout.setError(null);
            mDescriptionTextInputLayout.setErrorEnabled(false);
            return true;
        }
    }

    /**
     * Insert a recipe into database
     */
    private void insertRecipe() {
        CookiaryDbHelper db = new CookiaryDbHelper(this);
        // if the user doesn't provide photos, use the default photos based on the category
        if (photo == 0) {
            switch (category){
                case "Appetizer":
                    photo = R.raw.default_appetizer;
                    break;
                case "Beverage":
                    photo = R.raw.default_baverage;
                    break;
                case "Breakfast":
                    photo = R.raw.default_breakfast;
                    break;
                case "Dessert":
                    photo = R.raw.default_dessert;
                    break;
                case "Main Dish":
                    photo = R.raw.default_main_dish;
                    break;
                case "Salad":
                    photo = R.raw.default_salad;
                    break;
                case "Snack":
                    photo = R.raw.default_snack;
                    break;
                case "Soup":
                    photo = R.raw.default_soup;
                    break;
                default:
                    photo = R.raw.default_uncategorized;
            }
        }

        db.addRecipe(new Recipe(name, description, category, photo));
        db.close();
    }


    /**
     * Check the textView real-time if it's changed
     */
    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            switch (view.getId()) {
                case R.id.name_edit_text:
                    isNameValid();
                    break;
                case R.id.description_edit_text:
                    isDescValid();
                    break;
            }
        }
    }

    private class MyOnFocusChangeListener implements View.OnFocusChangeListener {
        private EditText EditTextView;

        private MyOnFocusChangeListener(View view) {
            this.EditTextView = (EditText) view;
        }

        @Override
        public void onFocusChange(View view, boolean hasFocus) {
            if (!hasFocus) {
                isNameValid(); // only the name is required
            } else {
                EditTextView.addTextChangedListener(new MyTextWatcher(EditTextView));
            }
        }
    }
}
