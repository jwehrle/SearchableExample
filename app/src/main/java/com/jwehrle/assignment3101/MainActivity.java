package com.jwehrle.assignment3101;

import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.SearchView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    public static String LOG_TAG = MainActivity.class.getName();
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Uri allStates = StateContract.StateEntry.STATE_URI.buildUpon().appendPath("all_states").build();
        Log.d(LOG_TAG, "Passing uri " + allStates.toString());
        Cursor cursor = getContentResolver().query(allStates, null, null, null, null, null);
        if (cursor == null || cursor.getCount() == 0) {
            if(cursor != null){
                Log.d(LOG_TAG, "Returned cursor is empty but not null.");
                cursor.close();
            } else {
                Log.d(LOG_TAG, "Returned cursor null.");
            }
            HashMap<String, String> data = StateData.getStateAnimalsMap();
            ContentValues[] values = new ContentValues[data.size()];
            int contentValueIndex = 0;
            for(String key : data.keySet()) {
                values[contentValueIndex] = new ContentValues();
                values[contentValueIndex].put(SearchManager.SUGGEST_COLUMN_TEXT_1, key);
                values[contentValueIndex].put(StateContract.StateEntry.ANIMAL, data.get(key));
                contentValueIndex++;
            }
            Uri insertStates = StateContract.StateEntry.STATE_URI.buildUpon().appendPath("bulk_insert").build();
            Log.d(LOG_TAG, "Attempting bulkInsert with uri = " + insertStates.toString());
            getContentResolver().bulkInsert(insertStates, values);
        } else {
            Log.d(LOG_TAG, "Table of states is already filled.");
            cursor.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }


}
