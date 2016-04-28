package com.jwehrle.assignment3101;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by jwehrle on 4/27/16.
 * Database contract for a table of state names to be associated with official state animals.
 */
public class StateContract {

    public static final String AUTHORITY = "com.jwehrle.state.provider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String STATE_PATH = "state";

    public static final class StateEntry implements BaseColumns {

        public static final Uri STATE_URI = BASE_CONTENT_URI.buildUpon().appendPath(STATE_PATH).build();

        public static final String TABLE = "state_table";
        public static final String NAME = "name";
        public static final String ANIMAL = "animal";

        public static Uri buildStateEntryUri(long id) {
            return ContentUris.withAppendedId(STATE_URI, id);
        }

    }

}
