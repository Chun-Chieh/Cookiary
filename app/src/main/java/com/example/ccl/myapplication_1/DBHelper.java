package com.example.ccl.myapplication_1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by maple on 10/22/17.
 */

public class DBHelper extends SQLiteOpenHelper {


    public static final String GRADE_TABLE_NAME = "grades";
    public static final String PRIMARY_KEY_NAME = "_id";
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Recipiary.db";

    public static final String TABLE_SPECIFICATIONS = "CREATE TABLE ";


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
    }
}
