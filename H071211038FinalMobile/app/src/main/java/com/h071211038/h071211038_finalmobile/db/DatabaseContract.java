package com.h071211038.h071211038_finalmobile.db;

import android.provider.BaseColumns;
public class DatabaseContract {
    public static String TABLE_NAME = "favorites";

    public static final class FavoriteColumns implements BaseColumns {

        public static String DATA_ID = "data_id";
        public static String ICON = "icon";
        public static String BACKDROP_PATH = "backdrop_path";
        public static String POSTER_PATH = "poster_path";
        public static String TITLE = "title";
        public static String RELEASE_DATE = "release_date";
        public static String OVERVIEW = "overview";
        public static String VOTE_AVERAGE = "vote_average";
    }
}
