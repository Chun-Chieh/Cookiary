package com.example.ccl.Cookiary.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.ccl.Cookiary.data.CookiaryContract.RecipeEntry;

/**
 * ContentProvider for cookiary app (not used)
 * @author Chun-Chieh Liang
 * 11/18/17
 */

public class CookiaryProvider extends ContentProvider{

    /** Tag for the log messages */
    public static final String LOG_TAG = CookiaryProvider.class.getSimpleName();

    /** URI matcher code for the content URI for the recipes table */
    private static final int RECIPES = 100;

    /** URI matcher code for the content URI for a single recipe in the recipes table */
    private static final int RECIPE_ID = 101;

    /** UriMatcher object to match a content URI to a corresponding code */
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(CookiaryContract.CONTENT_AUTHORITY, CookiaryContract.PATH_RECIPES, RECIPES);
        sUriMatcher.addURI(CookiaryContract.CONTENT_AUTHORITY, CookiaryContract.PATH_INGREDIENTS + "/#", RECIPE_ID);
    }

    /** Database helper object*/
    private CookiaryDbHelper mDbHelper;

    /**
     * Initialize the provider and the database helper object.
     */
    @Override
    public boolean onCreate() {
        mDbHelper = new CookiaryDbHelper(getContext());
        return false;
    }

    /**
     * Perform the query for the given URI. Use the given projection, selection, selection arguments, and sort order.
     */
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Cursor cursor;

        int match = sUriMatcher.match(uri);
        switch (match){
            case RECIPES:
                cursor = db.query(RecipeEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case RECIPE_ID:
                selection = RecipeEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(RecipeEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Can't query unknown URI: " + uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }
}
