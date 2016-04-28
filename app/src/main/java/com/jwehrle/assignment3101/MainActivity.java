package com.jwehrle.assignment3101;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Uri allStates = Uri.parse(StateContract.STATE_PATH);
        allStates.buildUpon().appendPath("all_states");
        Cursor cursor = getContentResolver().query(allStates, null, null, null, null, null);
        if (cursor == null || cursor.getCount() == 0) {
            if(cursor != null){
                cursor.close();
            }
            HashMap<String, String> data = StateData.getStateAnimalsMap();
            ContentValues[] values = new ContentValues[data.size()];
            int contentValueIndex = 0;
            for(String key : data.keySet()) {
                values[contentValueIndex] = new ContentValues();
                values[contentValueIndex].put(StateContract.StateEntry.NAME, key);
                values[contentValueIndex].put(StateContract.StateEntry.ANIMAL, data.get(key));
                contentValueIndex++;
            }
            Uri insertStates = Uri.parse(StateContract.STATE_PATH);
            insertStates.buildUpon().appendPath("bulk_insert");
            getContentResolver().bulkInsert(insertStates, values);
        }
    }
}
