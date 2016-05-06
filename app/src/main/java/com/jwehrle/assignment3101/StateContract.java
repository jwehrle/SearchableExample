package com.jwehrle.assignment3101;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by jwehrle on 4/27/16.
 * Database contract for a table of state names to be associated with official state animals. This
 * contract is not really necessary in our case but I've gotten used to using them and it seems a
 * good practice to keep up. It certainly is a nice place to store authority and uro information.
 * Note: I don't need to declare any columns here because all of our columns are SearchManager
 * static constants which facilitates using the automatic features of the SearchManager.
 */
public class StateContract {

    public static final String AUTHORITY = "com.jwehrle.state.provider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String STATE_PATH = "state";

    public static final class StateEntry implements BaseColumns {

        public static final Uri STATE_URI = BASE_CONTENT_URI.buildUpon().appendPath(STATE_PATH).build();

        public static final String TABLE = "state_table";

        public static Uri buildStateEntryUri(long id) {
            return ContentUris.withAppendedId(STATE_URI, id);
        }

    }

}
