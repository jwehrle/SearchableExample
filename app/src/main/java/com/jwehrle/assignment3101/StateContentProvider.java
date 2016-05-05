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
 * Basic ContentProvider for the simple State table.
 */
public class StateContentProvider extends ContentProvider {

    public static String LOG_TAG = StateContentProvider.class.getName();

    public static String SUGGEST_URI =  StateContract.AUTHORITY + StateContract.STATE_PATH + "/suggest";

    private static final int STATE_SEARCH = 1;
    private static final int ANIMAL_SEARCH = 2;
    private static final int STATE_QUERY = 3;
    private static final int ANIMAL_QUERY = 4;
    private static final int ENTRY_INSERTION = 5;
    private static final int DELETE_ID = 6;
    private static final int UPDATE_ID = 7;
    private static final int ALL_STATES = 8;
    private static final int BULK_INSERT = 9;
    private static final int SUGGESTION_QUERY = 10;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(StateContract.AUTHORITY, StateContract.STATE_PATH + "/stateSearch/*", STATE_SEARCH);
        sUriMatcher.addURI(StateContract.AUTHORITY, StateContract.STATE_PATH + "/animalSearch/*", ANIMAL_SEARCH);
        sUriMatcher.addURI(StateContract.AUTHORITY, StateContract.STATE_PATH + "/state/*", STATE_QUERY);
        sUriMatcher.addURI(StateContract.AUTHORITY, StateContract.STATE_PATH + "/animal/*", ANIMAL_QUERY);
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
                String input = selectionArgs[0].equals("") ? "" : selectionArgs[0] + "%";
                String [] suggestion = new String [] {input};
                Log.d(LOG_TAG, "Suggest Text is " + suggestion[0]);
                return dbHelper.getReadableDatabase().query(
                        StateContract.StateEntry.TABLE,
                        projection,
                        selection, //SearchManager.SUGGEST_COLUMN_TEXT_1 + " LIKE ?",
                        suggestion,//selectionArgs,//new String[] {suggestion + "%"},
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
            case STATE_SEARCH:
                String stateSearch = uri.getLastPathSegment();
                Log.d(LOG_TAG, "Search Text is " + stateSearch);
                return dbHelper.getReadableDatabase().query(
                        StateContract.StateEntry.TABLE,
                        projection,
                        SearchManager.SUGGEST_COLUMN_TEXT_1 + " LIKE ?",
                        new String[] {"%" + stateSearch + "%"},
                        null,
                        null,
                        sortOrder);
            case ANIMAL_SEARCH:
                String animalSearch = uri.getLastPathSegment();
                Log.d(LOG_TAG, "Search Text is " + animalSearch);
                return dbHelper.getReadableDatabase().query(
                        StateContract.StateEntry.TABLE,
                        projection,
                        SearchManager.SUGGEST_COLUMN_INTENT_DATA + " LIKE ?",
                        new String[] {"%" + animalSearch + "%"},
                        null,
                        null,
                        sortOrder);
            case STATE_QUERY:
                String state = uri.getLastPathSegment();
                Log.d(LOG_TAG, "Search Text is " + state);
                return dbHelper.getReadableDatabase().query(
                        StateContract.StateEntry.TABLE,
                        projection,
                        SearchManager.SUGGEST_COLUMN_TEXT_1 + " = ?",
                        new String[] {state},
                        null,
                        null,
                        sortOrder);
            case ANIMAL_QUERY:
                String animal = uri.getLastPathSegment();
                Log.d(LOG_TAG, "Search Text is " + animal);
                return dbHelper.getReadableDatabase().query(
                        StateContract.StateEntry.TABLE,
                        projection,
                        SearchManager.SUGGEST_COLUMN_INTENT_DATA + " = ?",
                        new String[] {animal},
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
            case STATE_SEARCH:
            case ANIMAL_SEARCH:
            case ALL_STATES:
            case SUGGESTION_QUERY:
                return "vnd.android.cursor.dir/" + StateContract.STATE_PATH;
            case STATE_QUERY:
            case ANIMAL_QUERY:
                return "vnd.android.cursor.item/" + StateContract.STATE_PATH;
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
//        if (uri == null) {
//            throw new UnsupportedOperationException("Null uri not allowed.");
//        }
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
