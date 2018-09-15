package com.ghozay19.cataloguemovie.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.ghozay19.cataloguemovie.db.DatabaseContract.MovieColumns.BACKDROP_POSTER;
import static com.ghozay19.cataloguemovie.db.DatabaseContract.MovieColumns.OVERVIEW;
import static com.ghozay19.cataloguemovie.db.DatabaseContract.MovieColumns.POSTER_JPG;
import static com.ghozay19.cataloguemovie.db.DatabaseContract.MovieColumns.RELEASE_DATE;
import static com.ghozay19.cataloguemovie.db.DatabaseContract.MovieColumns.TITLE_MOVIE;
import static com.ghozay19.cataloguemovie.db.DatabaseContract.TABLE_NAME;

public class DatabaseHelper extends SQLiteOpenHelper {


    private static String DATABASE_NAME = "dbmovie";

    private static final int DATABASE_VERSION = 1;


    private static final String SQL_CREATE_TABLE_MOVIE = "CREATE TABLE " + TABLE_NAME +
            "(" + _ID + " integer primary key," +
            TITLE_MOVIE + " text not null, " +
            RELEASE_DATE + " text not null, " +
            OVERVIEW + " text not null, " +
            POSTER_JPG + " text not null, " +
            BACKDROP_POSTER + " text not null);";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_MOVIE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

}