package com.jwehrle.assignment3101;

import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.HashMap;

/**
 * Simple application that functions as an example of how to implement a custom search suggestion.
 * I had fun setting this app to display an image from Wikipedia of one of each state's animals.
 */
public class MainActivity extends AppCompatActivity {

    public static String LOG_TAG = MainActivity.class.getName();
    ImageView stateAnimal;
    String animalURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stateAnimal = (ImageView)findViewById(R.id.image_state_animal);

        // Since this activity has the intent filter for searches we must check to see if this
        // activity is being created as the result of a search or not. If it is, then we get the
        // url from the intent. Otherwise, we use our default jackalope url.
        Intent searchIntent = getIntent();
        animalURL = searchIntent.getAction().equals(Intent.ACTION_VIEW) ?
                searchIntent.getDataString() :
                "https://upload.wikimedia.org/wikipedia/commons/3/36/Wall_drug_jackalope.jpg";

        Picasso.with(this)
                .load(animalURL)
                .into(stateAnimal);

        // Check if the table has been filled yet.
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

            // The table is empty. Let's fill it with our state data.
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
        android.support.v7.widget.SearchView searchView =
                (android.support.v7.widget.SearchView) menu.findItem(R.id.menu_search).getActionView();

        // Sets up this SearchView to receive our custom search suggestions.
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }
}
