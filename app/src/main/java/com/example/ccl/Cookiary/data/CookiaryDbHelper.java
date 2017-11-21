package com.example.ccl.Cookiary.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.ccl.Cookiary.Model.Direction;
import com.example.ccl.Cookiary.Model.Ingredient;
import com.example.ccl.Cookiary.Model.IngredientUsage;
import com.example.ccl.Cookiary.Model.Recipe;
import com.example.ccl.Cookiary.data.CookiaryContract.RecipeEntry;
import com.example.ccl.Cookiary.data.CookiaryContract.IngredientEntry;
import com.example.ccl.Cookiary.data.CookiaryContract.RecipeIngredientEntry;
import com.example.ccl.Cookiary.data.CookiaryContract.RecipeDirectionEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Chun-Chieh Liang
 * Last update: Nov 20, 2017
 * Database helper for Cookiary app.
 * Manages database creation and version management.
 */

public class CookiaryDbHelper extends SQLiteOpenHelper{

    public static final String LOG_TAG = CookiaryDbHelper.class.getSimpleName();

    // Database name
    private static final String DATABASE_NAME = "cookiary.db";

    // Database version
    private static final int DATABASE_VERSION = 1;

    // IDs for the recipe ingredient table
    private static final String RECIPE_ID = "recipe" + RecipeEntry._ID;
    private static final String INGREDIENT_ID = "ingredient" + IngredientEntry._ID;



    /**
     * Constructs a new instance of CookiaryDbHelper
     *
     * @param context of the app
     */
    public CookiaryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // create statement for recipes
        String SQL_CREATE_RECIPES_TABLE =  "CREATE TABLE " + RecipeEntry.TABLE_NAME + " ("
                + RecipeEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + RecipeEntry.COLUMN_RECIPE_TITLE + " TEXT NOT NULL, "
                + RecipeEntry.COLUMN_RECIPE_CATEGORY + " TEXT, "
                + RecipeEntry.COLUMN_RECIPE_DESCRIPTION + " TEXT, "
                + RecipeEntry.COLUMN_RECIPE_COOKINGTIME + " TEXT, "
                + RecipeEntry.COLUMN_RECIPE_YIELD + " INTEGER, "
                + RecipeEntry.COLUMN_RECIPE_DIFFICULTY + " TEXT, "
                + RecipeEntry.COLUMN_RECIPE_PHOTO + " INTEGER);";

        // create statement for ingredients
        String SQL_CREATE_INGREDIENTS_TABLE = "CREATE TABLE " + IngredientEntry.TABLE_NAME + " ("
                + IngredientEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + IngredientEntry.COLUMN_INGREDIENT_NAME + " TEXT NOT NULL);";

        // create statement for recipes_ingredients
        String SQL_CREATE_RECIPES_INGREDIENTS_TABLE = "CREATE TABLE " + RecipeIngredientEntry.TABLE_NAME + " ("
                + RecipeIngredientEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + RECIPE_ID + " INTEGER NOT NULL, "
                + INGREDIENT_ID + " INTEGER NOT NULL, "
                + RecipeIngredientEntry.COLUMN_RI_QUANTITY + " INTEGER, "
                + RecipeIngredientEntry.COLUMN_RI_MEASUREMENT + " TEXT);";


        // create statement for recipes_directions
        String SQL_CREATE_RECIPES_DIRECTIONS_TABLE = "CREATE TABLE " + RecipeDirectionEntry.TABLE_NAME + " ("
                + RecipeDirectionEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + RECIPE_ID + " INTEGER NOT NULL, "
                + RecipeDirectionEntry.COLUMN_RD_STEP + " INTEGER, "
                + RecipeDirectionEntry.COLUMN_RD_DESCRIPTION + " TEXT NOT NULL);";

        db.execSQL(SQL_CREATE_RECIPES_TABLE);
        db.execSQL(SQL_CREATE_INGREDIENTS_TABLE);
        db.execSQL(SQL_CREATE_RECIPES_INGREDIENTS_TABLE);
        db.execSQL(SQL_CREATE_RECIPES_DIRECTIONS_TABLE);
    }

    /**
     * This is called when the database needs to be upgraded.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // **
    }

    // ------------------------------------- Recipe table ------------------------------------- //
    /**
     * Wrap the result to a recipe object
     * @param cursor the db cursor
     * @return the recipe object
     */
    public Recipe getRecipeRecord(Cursor cursor) {
        Recipe result = new Recipe();
        result.setRecipe_id(cursor.getInt(cursor.getColumnIndex(RecipeEntry._ID)));
        result.setName(cursor.getString(cursor.getColumnIndex(RecipeEntry.COLUMN_RECIPE_TITLE)));
        result.setmCategory(cursor.getString(cursor.getColumnIndex(RecipeEntry.COLUMN_RECIPE_CATEGORY)));
        result.setDescription(cursor.getString(cursor.getColumnIndex(RecipeEntry.COLUMN_RECIPE_DESCRIPTION)));
        result.setCookingTime(cursor.getString(cursor.getColumnIndex(RecipeEntry.COLUMN_RECIPE_COOKINGTIME)));
        result.setYield(cursor.getInt(cursor.getColumnIndex(RecipeEntry.COLUMN_RECIPE_YIELD)));
        result.setDifficulty(cursor.getString(cursor.getColumnIndex(RecipeEntry.COLUMN_RECIPE_DIFFICULTY)));
        result.setmImageResourceId(cursor.getInt(cursor.getColumnIndex(RecipeEntry.COLUMN_RECIPE_PHOTO)));
        return result;
    }

    /**
     * Get all the recipes in the database
     * @return a list of recipes
     */
    public List<Recipe> getAllRecipes() {
        List<Recipe> recipeList = new ArrayList<>();
        // Select All Query
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(RecipeEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        while (cursor.moveToNext()) {
            recipeList.add(getRecipeRecord(cursor));
        }

        cursor.close();
        Log.v("CookiaryDbHelper", "get all recipes successfully!");
        return recipeList;
    }

    /**
     * Get the recipe information by ID
     * @param id
     * @return the recipe which matched the ID
     */
    public Recipe getRecipe(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(RecipeEntry.TABLE_NAME,
                null,
                RecipeEntry._ID+"=?",
                new String[] { String.valueOf(id) },
                null,
                null,
                null);
        cursor.moveToFirst();
        Recipe recipe = getRecipeRecord(cursor);
        cursor.close();
        Log.v("CookiaryDbHelper", "get the recipe successfully!");
        return recipe;
    }

    /**
     * insert the values of a recipe object to the recipe table
     * @param recipe a object of Recipe
     */
    public void addRecipe(Recipe recipe) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(RecipeEntry.COLUMN_RECIPE_TITLE, recipe.getName());
        values.put(RecipeEntry.COLUMN_RECIPE_CATEGORY, recipe.getCategory());
        values.put(RecipeEntry.COLUMN_RECIPE_DESCRIPTION, recipe.getDescription());
        values.put(RecipeEntry.COLUMN_RECIPE_PHOTO, recipe.getImageResourceId());
        values.put(RecipeEntry.COLUMN_RECIPE_YIELD, recipe.getYield());
        values.put(RecipeEntry.COLUMN_RECIPE_COOKINGTIME, recipe.getCookingTime());
        values.put(RecipeEntry.COLUMN_RECIPE_DIFFICULTY, recipe.getDifficulty());

        // insert the Row
        long newRowId = db.insert(RecipeEntry.TABLE_NAME, null, values);

        // close database connection
        db.close();

        Log.v("CookiaryDbHelper", "insert a recipe successfully!");
    }


    /**
     * Update the overall information of the recipe based on the ID
     * @param id recipe id
     * @param name title
     * @param cookingTime cooking time
     * @param yield servings
     * @param difficulty difficulty
     */
    public void updateRecipeOverall(int id, String name, String cookingTime, int yield, String difficulty ) {
        SQLiteDatabase db = this.getWritableDatabase();

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(RecipeEntry.COLUMN_RECIPE_TITLE, name);
        values.put(RecipeEntry.COLUMN_RECIPE_COOKINGTIME, cookingTime);
        values.put(RecipeEntry.COLUMN_RECIPE_YIELD, yield);
        values.put(RecipeEntry.COLUMN_RECIPE_DIFFICULTY, difficulty);

        // Which row to update, based on the ID
        String selection = RecipeEntry._ID + "=?";
        String[] selectionArgs = { String.valueOf(id) };

        int count = db.update(
                RecipeEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    // ----------------------------------- Ingredient table ----------------------------------- //

    /**
     * Get the ingredient information by ingredient ID
     * @param id
     * @return the ingredient which matched the ID
     */
    public Ingredient getIngredient(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(IngredientEntry.TABLE_NAME,
                null,
                IngredientEntry._ID+"=?",
                new String[] { String.valueOf(id) },
                null,
                null,
                null);
        cursor.moveToFirst();

        int ingredientId = cursor.getInt(cursor.getColumnIndex(IngredientEntry._ID));
        String ingredientName = cursor.getString(cursor.getColumnIndex(IngredientEntry.COLUMN_INGREDIENT_NAME));
        Ingredient ingredient = new Ingredient(ingredientId, ingredientName);

        cursor.close();
        return ingredient;
    }


    /**
     * Add new ingredient to the ingredient table
     * @param ingredient object
     */
    public long addIngredient(Ingredient ingredient) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(IngredientEntry.COLUMN_INGREDIENT_NAME, ingredient.getName());

        // insert the Row
        long newRowId = db.insert(IngredientEntry.TABLE_NAME, null, values);

        db.close();

        return newRowId;
    }

    /**
     * Add new ingredient to the ingredient table
     * @param ingredientName name of the ingredient
     */
    public void addIngredient(String ingredientName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(IngredientEntry.COLUMN_INGREDIENT_NAME, ingredientName);

        // insert the Row
        long newRowId = db.insert(IngredientEntry.TABLE_NAME, null, values);
    }

    // ------------------------------- Recipe Ingredient table -------------------------------- //

    /**
     * Get all the ingredients id of a recipe
     * @param recipe_id the recipe
     */
    public List<Integer> getRecipeIngredientsIds (int recipe_id){
        List<Integer> ingredientIdList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(RecipeIngredientEntry.TABLE_NAME,
                new String[]{INGREDIENT_ID},
                RECIPE_ID+"=?",
                new String[] { String.valueOf(recipe_id) },
                null,
                null,
                null);

        while (cursor.moveToNext()) {
            ingredientIdList.add(cursor.getInt(0));
        }

        cursor.close();
        db.close();
        return ingredientIdList;
    }

    public List<IngredientUsage> getRecipeIngredientsUsage (int recipe_id) {
        List<IngredientUsage> ingredientUsageList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(RecipeIngredientEntry.TABLE_NAME,
                null,
                RECIPE_ID+"=?",
                new String[] { String.valueOf(recipe_id) },
                null,
                null,
                null);

        // convert the ingredient id to the name in column 2
        while (cursor.moveToNext()) {
            IngredientUsage iu = new IngredientUsage(cursor.getInt(0),
                    cursor.getInt(1),
                    getIngredient(cursor.getInt(2)).getName(),
                    cursor.getInt(3),
                    cursor.getString(4));
            ingredientUsageList.add(iu);
        }
        cursor.close();

        db.close();
        return ingredientUsageList;
    }

    /**
     * Add the ingredients for a recipe
     * @param recipe_id
     * @param ingredient_id
     * @param quantity
     * @param measurement
     * @return
     */
    public long addRecipeIngredients(int recipe_id, long ingredient_id, int quantity, String measurement){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(RECIPE_ID, recipe_id);
        values.put(INGREDIENT_ID, ingredient_id);
        values.put(RecipeIngredientEntry.COLUMN_RI_QUANTITY, quantity);
        values.put(RecipeIngredientEntry.COLUMN_RI_MEASUREMENT, measurement);

        long insertion_id = db.insert(RecipeIngredientEntry.TABLE_NAME, null, values);

        db.close();
        return insertion_id;
    }

    /**
     * Update the ingredients of a recipe.
     * Add the ingredient, if the ingredient (name) is not in the ingredient table
     * @param recipe_id
     */
    public void updateRecipeIngredients(int recipe_id, String ingredientName, int quantity, String measurement) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(IngredientEntry.TABLE_NAME,
                    null,
                    IngredientEntry.COLUMN_INGREDIENT_NAME + "=?",
                    new String[]{ingredientName},
                    null,
                    null,
                    null);


        boolean notFound = cursor.getCount() == 0;
        long ingredientId;

        // insert the ingredient if it is not found in ingredient table
        if (notFound) {
            addIngredient(ingredientName);
            cursor = db.query(IngredientEntry.TABLE_NAME,
                    new String[]{IngredientEntry._ID},
                    IngredientEntry.COLUMN_INGREDIENT_NAME + "=?",
                    new String[]{ingredientName},
                    null,
                    null,
                    null);
            cursor.moveToNext(); // the default index is -1
            ingredientId = cursor.getInt(cursor.getColumnIndex(IngredientEntry._ID));

        } else {
            cursor.moveToNext(); // the default index is -1
            ingredientId = cursor.getInt(cursor.getColumnIndex(IngredientEntry._ID));
        }

        cursor.close();


        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(RecipeIngredientEntry.COLUMN_RI_QUANTITY, quantity);
        values.put(RecipeIngredientEntry.COLUMN_RI_MEASUREMENT, measurement);


        // update the ingredients quantity and measurements
        String selection = RECIPE_ID + "=? AND " + INGREDIENT_ID +"=?" ;
        String[] selectionArgs = { String.valueOf(recipe_id), String.valueOf(ingredientId)};

        int count = db.update(
                RecipeIngredientEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        if (count == 0) {
            addRecipeIngredients(recipe_id, ingredientId, quantity, measurement);
        }
        db.close();
    }

    // ----------------------------------- Direction table ------------------------------------ //

    public List<Direction> getRecipeDirections(int recipe_id) {
        List<Direction> directionList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(RecipeDirectionEntry.TABLE_NAME,
                null,
                RECIPE_ID + "=?",
                new String[]{String.valueOf(recipe_id)},
                null,
                null,
                null);

        while (cursor.moveToNext()) {
            Direction direction = new Direction(cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getInt(2),
                    cursor.getString(3));
            directionList.add(direction);
        }
        cursor.close();
        db.close();
        return directionList;
    }


    public long addRecipeDirection(int recipe_id, String directionDescription) {
        SQLiteDatabase db = this.getReadableDatabase();
        // search for the current amount of steps of a recipe
        Cursor cursor = db.query(RecipeDirectionEntry.TABLE_NAME,
                null,
                RECIPE_ID + "=?",
                new String[]{String.valueOf(recipe_id)},
                null,
                null,
                null);

        int existingSteps = cursor.getCount();
        cursor.close();

        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(RECIPE_ID, recipe_id);
        values.put(RecipeDirectionEntry.COLUMN_RD_STEP, existingSteps + 1);
        values.put(RecipeDirectionEntry.COLUMN_RD_DESCRIPTION, directionDescription);

        long insertion_id = db.insert(RecipeDirectionEntry.TABLE_NAME, null, values);

        db.close();
        return insertion_id;
    }
}
