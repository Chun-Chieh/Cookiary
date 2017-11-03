package com.example.ccl.myapplication_1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Recipiary.db";
    private static final String TABLE_RECIPE = "table_recipe";
    private static final String KEY_RECIPE_ID = "recipe_id";
    private static final String KEY_NAME = "recipe_name";
    private static final String KEY_DESCRIPTION = "recipe_description";
    private static final String KEY_CATEGORY = "recipe_category";
    private static final String KEY_DISH_PHOTO = "recipe_photo";
//    private static final String KEY_INGREDIENT_1 = "recipe_ingredient_1";
//    private static final String KEY_INGREDIENT_2 = "recipe_ingredient_2";

    // form: "CREATE TABLE grades(id INTEGER PRIMARY KEY, grade1 INTEGER)"
    private static final String TABLE_SPECIFICATIONS = "CREATE TABLE " + TABLE_RECIPE + "("
            + KEY_RECIPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_NAME + " INTEGER, "
            + KEY_DESCRIPTION + " TEXT, "
            + KEY_CATEGORY + " TEXT, "
            + KEY_DISH_PHOTO + " INTEGER)";

    public DBHelper(Context context) {
        // A database exists, named DATABASE_NAME, with TABLE_SPECIFICATIONS
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_SPECIFICATIONS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPE);
    }


    /**
     * wrap the result to a recipe object
     * @param cursor the db cursor
     * @return the recipe object
     */
    public Recipe getRecord(Cursor cursor) {
        Recipe result = new Recipe();
        result.setRecipe_id(cursor.getInt(0));
        result.setName(cursor.getString(1));
        result.setDescription(cursor.getString(2));
        result.setmCategory(cursor.getString(3));
        result.setmImageResourceId(cursor.getInt(4));
        return result;
    }


    public void addRecipe(Recipe recipe) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_NAME, recipe.getName());
        values.put(KEY_DESCRIPTION, recipe.getDescription());
        values.put(KEY_CATEGORY, recipe.getCategory());
        values.put(KEY_DISH_PHOTO, recipe.getImageResourceId());

        // Inserting Row
        db.insert(TABLE_RECIPE, null, values);
        db.close(); // Closing database connection
        Log.v("DBHelper", "insert a recipe successful!");
    }


    /**
     * get all the recipes in the database
     * @return a list of recipes
     */
    public List<Recipe> getAllRecipes() {
        List<Recipe> recipeList = new ArrayList<Recipe>();
        // Select All Query
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_RECIPE, null, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            recipeList.add(getRecord(cursor));
        }

        cursor.close();
        Log.v("DBHelper", "get all recipes successful!");
        return recipeList;
    }
}
