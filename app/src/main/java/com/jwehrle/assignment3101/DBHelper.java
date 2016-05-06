package com.jwehrle.assignment3101;

import android.app.SearchManager;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jwehrle on 4/27/16.
 * Minimal SQLiteOpenHelper creates a state-animal table with SearchManager columns for SearchManager
 * integration.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static DBHelper sInstance = null;

    private static final String DATABASE_NAME = "state_database";
    private static final int DATABASE_VERSION = 6;

    private static final String CREATE_TABLE = "create table " +
            StateContract.StateEntry.TABLE + " (" +
            StateContract.StateEntry._ID + " integer primary key autoincrement, " +
            SearchManager.SUGGEST_COLUMN_TEXT_1 + " text, " +
            SearchManager.SUGGEST_COLUMN_INTENT_DATA + " text);";

    public static synchronized DBHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DBHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    private DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + StateContract.StateEntry.TABLE);
        onCreate(db);
    }
}
