package com.h071211038.h071211038_finalmobile.db;

import android.database.Cursor;

import com.h071211038.h071211038_finalmobile.entity.Favorite;

import java.util.ArrayList;

public class MappingHelper {

    public  static ArrayList<Favorite> mapCursorToArrayList(Cursor cursor) {
        ArrayList<Favorite> notes = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns._ID));
            int dataId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.DATA_ID));
            int icon = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.ICON));
            String backdropPath = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.BACKDROP_PATH));
            String posterPath = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.POSTER_PATH));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.TITLE));
            String releaseDate = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.RELEASE_DATE));
            String overview = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.OVERVIEW));
            Double voteAverage = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.VOTE_AVERAGE));
            notes.add(new Favorite(id, dataId, icon, backdropPath, posterPath, title, releaseDate, overview, voteAverage));
            System.out.println(title);
        }
        return notes;
    }
}