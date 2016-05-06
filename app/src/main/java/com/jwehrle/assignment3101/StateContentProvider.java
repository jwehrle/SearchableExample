package com.jwehrle.assignment3101;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by jwehrle on 4/27/16.
 * Basic ContentProvider for the simple State table. This content provider is customized to work with
 * the Search Manager and doesn't do much else - in fact, delete() and update() are overkill for this
 * example.
 */
public class StateContentProvider extends ContentProvider {

    public static String LOG_TAG = StateContentProvider.class.getName();

    private static final int ENTRY_INSERTION = 5;
    private static final int DELETE_ID = 6;
    private static final int UPDATE_ID = 7;
    private static final int ALL_STATES = 8;
    private static final int BULK_INSERT = 9;
    private static final int SUGGESTION_QUERY = 10;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(StateContract.AUTHORITY, StateContract.STATE_PATH + "/entry", ENTRY_INSERTION);
        sUriMatcher.addURI(StateContract.AUTHORITY, StateContract.STATE_PATH + "/delete/#", DELETE_ID);
        sUriMatcher.addURI(StateContract.AUTHORITY, StateContract.STATE_PATH + "/update/#", UPDATE_ID);
        sUriMatcher.addURI(StateContract.AUTHORITY, StateContract.STATE_PATH + "/all_states", ALL_STATES);
        sUriMatcher.addURI(StateContract.AUTHORITY, StateContract.STATE_PATH + "/bulk_insert", BULK_INSERT);
        sUriMatcher.addURI(StateContract.AUTHORITY, StateContract.STATE_PATH + "/suggest/*", SUGGESTION_QUERY);
    }


    private DBHelper dbHelper;

    public StateContentProvider() {}

    @Override
    public boolean onCreate() {
        dbHelper = DBHelper.getInstance(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@Nullable Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if (uri == null) {
            throw new UnsupportedOperationException("Null uri not allowed.");
        }

        Log.d(LOG_TAG, "Just entered query() and uri = " + uri.toString());

        int match = sUriMatcher.match(uri);
        switch (match) {
            case SUGGESTION_QUERY:
                // Only provide suggestions when there is user input. Otherwise simple focus will
                // cause us to display the entire table as a suggestion.
                String input = selectionArgs[0].equals("") ? "" : selectionArgs[0] + "%";
                String [] suggestion = new String [] {input};
                Log.d(LOG_TAG, "Suggest Text is " + suggestion[0]);
                return dbHelper.getReadableDatabase().query(
                        StateContract.StateEntry.TABLE,
                        projection,
                        selection,
                        suggestion,
                        null,
                        null,
                        sortOrder);
            case ALL_STATES:
                Log.d(LOG_TAG, "All states query.");
                return dbHelper.getReadableDatabase().query(
                        StateContract.StateEntry.TABLE,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public String getType(@Nullable Uri uri) {
        if (uri == null) {
            throw new UnsupportedOperationException("Null uri not allowed.");
        }
        int match = sUriMatcher.match(uri);
        switch (match) {
            case ALL_STATES:
            case SUGGESTION_QUERY:
                return "vnd.android.cursor.dir/" + StateContract.STATE_PATH;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@Nullable Uri uri, ContentValues values) {
        if (uri == null) {
            throw new UnsupportedOperationException("Null uri not allowed.");
        }
        int match = sUriMatcher.match(uri);
        switch (match) {
            case ENTRY_INSERTION:
                long id = dbHelper.getWritableDatabase().insert(
                        StateContract.StateEntry.TABLE,
                        null,
                        values);
                return StateContract.StateEntry.buildStateEntryUri(id);
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public int bulkInsert(@Nullable Uri uri, @NonNull ContentValues[] values) {
        if (uri == null) {
            throw new UnsupportedOperationException("Null uri not allowed.");
        }
        int numberInserted = 0;
        int match = sUriMatcher.match(uri);
        Log.d(LOG_TAG, "Entered bulkInsert() with uri = " + uri.toString());
        switch (match) {
            case BULK_INSERT:
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                for (ContentValues cv: values) {
                    db.insert(StateContract.StateEntry.TABLE, null, cv);
                    Log.d(LOG_TAG, "Inserted " + cv.getAsString(SearchManager.SUGGEST_COLUMN_TEXT_1));
                    numberInserted++;
                }
                db.close();
        }
        return numberInserted;
    }

    @Override
    public int delete(@Nullable Uri uri, String selection, String[] selectionArgs) {
        if (uri == null) {
            throw new UnsupportedOperationException("Null uri not allowed.");
        }
        int match = sUriMatcher.match(uri);
        switch (match) {
            case DELETE_ID:
                long id = ContentUris.parseId(uri);
                return dbHelper.getWritableDatabase().delete(
                        StateContract.StateEntry.TABLE,
                        StateContract.StateEntry._ID + " = ?",
                        new String [] {String.valueOf(id)});
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public int update(@Nullable Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (uri == null) {
            throw new UnsupportedOperationException("Null uri not allowed.");
        }
        int match = sUriMatcher.match(uri);
        switch (match) {
            case UPDATE_ID:
                long id = ContentUris.parseId(uri);
                return dbHelper.getWritableDatabase().update(
                        StateContract.StateEntry.TABLE,
                        values,
                        StateContract.StateEntry._ID + " = ? ",
                        new String[] {String.valueOf(id)});
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }


}
