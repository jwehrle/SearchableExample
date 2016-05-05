package com.jwehrle.assignment3101;

import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    public static String LOG_TAG = MainActivity.class.getName();
    ImageView stateAnimal;
    String animalURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stateAnimal = (ImageView)findViewById(R.id.image_state_animal);

        Intent searchIntent = getIntent();

        animalURL = searchIntent.getAction().equals(Intent.ACTION_VIEW) ?
                searchIntent.getDataString() :
                "https://upload.wikimedia.org/wikipedia/commons/3/36/Wall_drug_jackalope.jpg";

        Picasso.with(this)
                .load(animalURL)
                .into(stateAnimal);

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
                values[contentValueIndex].put(SearchManager.SUGGEST_COLUMN_INTENT_DATA, data.get(key));
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
