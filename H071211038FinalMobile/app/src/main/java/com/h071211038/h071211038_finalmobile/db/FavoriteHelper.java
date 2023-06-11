package com.h071211038.h071211038_finalmobile.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FavoriteHelper {
    private static final String DATABASE_TABLE = DatabaseContract.TABLE_NAME;

    private static DatabaseHelper databaseHelper;

    private static SQLiteDatabase database;

    private static volatile FavoriteHelper INSTANCE;

    private FavoriteHelper(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public static FavoriteHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FavoriteHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        databaseHelper.close();
        if (database.isOpen()) {
            database.close();
        }
    }

    public Cursor queryAll() {
        return database.query(
                DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                DatabaseContract.FavoriteColumns._ID + " ASC"
        );
    }

    public long insert(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int deleteById(int id) {
        return database.delete(DATABASE_TABLE, DatabaseContract.FavoriteColumns.DATA_ID + " = " + id, null);
    }

    public Cursor search(int dataId) {
        SQLiteDatabase database = databaseHelper.getReadableDatabase();

        String[] projection = {
                DatabaseContract.FavoriteColumns._ID,
                DatabaseContract.FavoriteColumns.DATA_ID,
                DatabaseContract.FavoriteColumns.ICON,
                DatabaseContract.FavoriteColumns.BACKDROP_PATH,
                DatabaseContract.FavoriteColumns.POSTER_PATH,
                DatabaseContract.FavoriteColumns.TITLE,
                DatabaseContract.FavoriteColumns.RELEASE_DATE,
                DatabaseContract.FavoriteColumns.OVERVIEW,
                DatabaseContract.FavoriteColumns.VOTE_AVERAGE
        };

        String selection = DatabaseContract.FavoriteColumns.DATA_ID + " = ?";
        String[] selectionArgs = { String.valueOf(dataId) };

        Cursor cursor = database.query(
                DATABASE_TABLE,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        return cursor;
    }

}
