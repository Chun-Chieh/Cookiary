package com.example.ccl.Cookiary.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * @author Chun-Chieh Liang
 * Last update: Nov 7, 2017
 * RecipeContract defines the data model of recipe and event related information.
 * This data is stored in the following tables:
 *
 * Recipes: _ID, title, category, description, photo
 * Ingredients: _ID,
 */

public final class CookiaryContract {
    // The empty constructor prevents this class being accidentally initiated.
    private CookiaryContract(){}


    public static final String CONTENT_AUTHORITY = "com.example.ccl.Cookiary";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_RECIPES = "recipes";

    public static final String PATH_INGREDIENTS = "ingredients";

    /**
     * Inner class that defines the table contents of the recipe table
     * Each entry in the table represents a single recipe.
     */
    public static final class RecipeEntry implements BaseColumns {
        /** The content URI to access the recipe data in the provider */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_RECIPES);

        /** Name of database table for recipes */
        public final static String TABLE_NAME = "recipes";

        /**
         * Unique ID number for the recipe.
         *
         * Type: INTEGER
         */
        public final static String _ID = BaseColumns._ID;

        /**
         * Title of the recipe.
         *
         * Type: TEXT
         */
        public final static String COLUMN_RECIPE_TITLE ="title";

        /**
         * Category of the recipe.
         *
         * Type: TEXT
         */
        public final static String COLUMN_RECIPE_CATEGORY = "category";

        /**
         * Description of the recipe.
         *
         * Type: TEXT
         */
        public final static String COLUMN_RECIPE_DESCRIPTION = "description";

        /**
         * Photo of the recipe.
         *
         * Type: INTEGER
         */
        public final static String COLUMN_RECIPE_PHOTO = "photo";

        /**
         * Cooking_time of the recipe.
         *
         * Type: TEXT
         */
        public final static String COLUMN_RECIPE_COOKINGTIME = "cooking_time";

        /**
         * Servings of the recipe.
         *
         * Type: TEXT
         */
        public final static String COLUMN_RECIPE_YIELD = "yield";

        /**
         * Difficulty of the recipe.
         *
         * Type: TEXT
         */
        public final static String COLUMN_RECIPE_DIFFICULTY = "difficulty";
    }

    /**
     * Inner class that defines the table contents of the ingredient table
     * Each entry in the table represents a single ingredient.
     */
    public static final class IngredientEntry implements BaseColumns {
        /** The content URI to access the ingredient data in the provider */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_INGREDIENTS);

        /** Name of database table for recipes */
        public final static String TABLE_NAME = "ingredients";

        /**
         * Unique ID number for the ingredient.
         *
         * Type: INTEGER
         */
        public final static String _ID = BaseColumns._ID;

        /**
         * Name of the ingredient.
         *
         * Type: TEXT
         */
        public final static String COLUMN_INGREDIENT_NAME ="name";

    }

    /**
     * Inner class that defines the table contents of the recipe_ingredient table
     * Each entry in the table represents a pair of a recipe and a ingredient.
     */
    public static final class RecipeIngredientEntry implements BaseColumns {
        /** Name of database table for recipes */
        public final static String TABLE_NAME = "recipes_ingredients";

        /**
         * Unique ID number for the pair.
         *
         * Type: INTEGER
         */
        public final static String _ID = BaseColumns._ID;

        /**
         * Quantity of the ingredient used in the recipe.
         *
         * Type: INTEGER
         */
        public final static String COLUMN_RI_QUANTITY = "quantity";

        /**
         *  Measurements of the ingredient used in the recipe.
         *
         * Type: TEXT
         */
        public final static String COLUMN_RI_MEASUREMENT = "measurement";
    }
}
