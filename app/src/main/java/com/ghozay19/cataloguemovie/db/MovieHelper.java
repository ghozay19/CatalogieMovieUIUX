package com.ghozay19.cataloguemovie.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.ghozay19.cataloguemovie.model.Movieitem;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.ghozay19.cataloguemovie.db.DatabaseContract.MovieColumns.BACKDROP_POSTER;
import static com.ghozay19.cataloguemovie.db.DatabaseContract.MovieColumns.OVERVIEW;
import static com.ghozay19.cataloguemovie.db.DatabaseContract.MovieColumns.POSTER_JPG;
import static com.ghozay19.cataloguemovie.db.DatabaseContract.MovieColumns.RELEASE_DATE;
import static com.ghozay19.cataloguemovie.db.DatabaseContract.MovieColumns.TITLE_MOVIE;
import static com.ghozay19.cataloguemovie.db.DatabaseContract.TABLE_NAME;

public class MovieHelper {

    private static String DATABASE_TABLE = TABLE_NAME;
    private Context context;
    private DatabaseHelper databaseHelper;

    private SQLiteDatabase database;

    public MovieHelper(Context context) {
        this.context = context;
    }

    public MovieHelper open() throws SQLException {
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        databaseHelper.close();
    }

    public ArrayList<Movieitem> query() {
        ArrayList<Movieitem> arrayList = new ArrayList<Movieitem>();
        Cursor cursor = database.query(DATABASE_TABLE
                , null
                , null
                , null
                , null
                , null, _ID + " DESC"
                , null);
        cursor.moveToFirst();
        Movieitem movieList;
        if (cursor.getCount() > 0) {
            do {
                movieList = new Movieitem();
                movieList.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                movieList.setMov_title(cursor.getString(cursor.getColumnIndexOrThrow(TITLE_MOVIE)));
                movieList.setMov_releasedate(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE)));
                movieList.setMov_synopsis(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                movieList.setMov_poster(cursor.getString(cursor.getColumnIndexOrThrow(POSTER_JPG)));
                movieList.setMov_poster_backdrop(cursor.getString(cursor.getColumnIndexOrThrow(BACKDROP_POSTER)));
                arrayList.add(movieList);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(Movieitem movieitem) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(_ID, movieitem.getId());
        contentValues.put(TITLE_MOVIE, movieitem.getMov_title());
        contentValues.put(RELEASE_DATE, movieitem.getMov_releasedate());
        contentValues.put(OVERVIEW, movieitem.getMov_synopsis());
        contentValues.put(POSTER_JPG, movieitem.getMov_poster());
        contentValues.put(BACKDROP_POSTER, movieitem.getMov_poster_backdrop());
        return database.insert(DATABASE_TABLE, null, contentValues);
    }

    public int update(Movieitem movieitem) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(_ID, movieitem.getId());
        initialValues.put(TITLE_MOVIE, movieitem.getMov_title());
        initialValues.put(RELEASE_DATE, movieitem.getMov_releasedate());
        initialValues.put(OVERVIEW, movieitem.getMov_synopsis());
        initialValues.put(POSTER_JPG, movieitem.getMov_poster());
        initialValues.put(BACKDROP_POSTER, movieitem.getMov_poster_backdrop());
        return database.update(DATABASE_TABLE, initialValues, _ID + " = '" + movieitem.getId() + "'", null);
    }

    public int delete(int id) {
        return database.delete(TABLE_NAME, _ID + " ='" + id + "'", null);
    }

    //cari berdasarkan id

    public boolean getMoviebyId(String id) {
        boolean result = false;
        Cursor cursor = database.query(DATABASE_TABLE,
                null,
                _ID + " = '"+id+"'",
                null,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();
        ArrayList<Movieitem> arrayList = new ArrayList<>();
        Movieitem movieitem;
        if (cursor.getCount() > 0) {
            result = true;
        }
        cursor.close();
        return result;
    }

    //cari berdasar judul

    public boolean getMoviebyName(String title) {
        boolean result = false;
        Cursor cursor = database.query(DATABASE_TABLE,
                null,
                TITLE_MOVIE + " = '"+title+"'",
                null,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();
        ArrayList<Movieitem> arrayList = new ArrayList<>();
        Movieitem movieitem;
        if (cursor.getCount() > 0) {
            result = true;
        }
        cursor.close();
        return result;
    }


    /**
     * Ambil data dari note berdasarakan parameter id
     * Gunakan method ini untuk ambil data di dalam provider
     * @param id id note yang dicari
     * @return cursor hasil query
     */
    public Cursor queryByIdProvider(String id) {
        return database.query(DATABASE_TABLE, null
                , _ID + "=?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    /**
     * Ambil data dari semua note yang ada di dalam database
     * Gunakan method ini untuk ambil data di dalam provider
     * @return cursor hasil query
     */
    public Cursor queryProvider() {
        return database.query(DATABASE_TABLE
                , null
                , null
                , null
                , null
                , null
                , _ID + " DESC");
    }

    /**
     * Simpan data ke dalam database
     * Gunakan method ini untuk query insert di dalam provider
     * @param values nilai data yang akan di simpan
     * @return long id dari data yang baru saja di masukkan
     */
    public long insertProvider(ContentValues values){
        return database.insert(DATABASE_TABLE,null,values);
    }

    /**
     * Update data dalam database
     * @param id data dengan id berapa yang akan di update
     * @param values nilai data baru
     * @return int jumlah data yang ter-update
     */
    public int updateProvider(String id,ContentValues values){
        return database.update(DATABASE_TABLE,values,_ID +" = ?",new String[]{id} );
    }

    /**
     * Delete data dalam database
     * @param id data dengan id berapa yang akan di delete
     * @return int jumlah data yang ter-delete
     */
    public int deleteProvider(String id){
        return database.delete(DATABASE_TABLE,_ID + " = ?", new String[]{id});
    }
}
