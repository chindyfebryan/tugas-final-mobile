package com.h071211038.h071211038_finalmobile.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "Favorite.db";

    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_FAVORITES =
            String.format(
                    "CREATE TABLE %s"
                            + " (%s INTEGER PRIMARY KEY AUTOINCREMENT,"
                            + " %s INTEGER NOT NULL,"
                            + " %s INTEGER NOT NULL,"
                            + " %s TEXT,"
                            + " %s TEXT NOT NULL,"
                            + " %s TEXT NOT NULL,"
                            + " %s TEXT NOT NULL,"
                            + " %s TEXT NOT NULL,"
                            + " %s REAL NOT NULL)",
                    DatabaseContract.TABLE_NAME,
                    DatabaseContract.FavoriteColumns._ID,
                    DatabaseContract.FavoriteColumns.DATA_ID,
                    DatabaseContract.FavoriteColumns.ICON,
                    DatabaseContract.FavoriteColumns.BACKDROP_PATH,
                    DatabaseContract.FavoriteColumns.POSTER_PATH,
                    DatabaseContract.FavoriteColumns.TITLE,
                    DatabaseContract.FavoriteColumns.RELEASE_DATE,
                    DatabaseContract.FavoriteColumns.OVERVIEW,
                    DatabaseContract.FavoriteColumns.VOTE_AVERAGE
            );

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_FAVORITES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    private static DatabaseHelper instance;

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }
}
