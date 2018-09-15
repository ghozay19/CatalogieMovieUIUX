package com.ghozay19.cataloguemovie.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import static android.provider.BaseColumns._ID;
import static com.ghozay19.cataloguemovie.db.DatabaseContract.MovieColumns.BACKDROP_POSTER;
import static com.ghozay19.cataloguemovie.db.DatabaseContract.MovieColumns.OVERVIEW;
import static com.ghozay19.cataloguemovie.db.DatabaseContract.MovieColumns.POSTER_JPG;
import static com.ghozay19.cataloguemovie.db.DatabaseContract.MovieColumns.RELEASE_DATE;
import static com.ghozay19.cataloguemovie.db.DatabaseContract.MovieColumns.TITLE_MOVIE;
import static com.ghozay19.cataloguemovie.db.DatabaseContract.getColumnInt;
import static com.ghozay19.cataloguemovie.db.DatabaseContract.getColumnString;

public class Movieitem implements Parcelable {

    private int id;
    private String mov_title;
    private String mov_synopsis;
    private String mov_releasedate;
    private String mov_poster;
    private String mov_rate_count;
    private String mov_rate;
    private String mov_poster_backdrop;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMov_title() {
        return mov_title;
    }

    public void setMov_title(String mov_title) {
        this.mov_title = mov_title;
    }

    public String getMov_synopsis() {
        return mov_synopsis;
    }

    public void setMov_synopsis(String mov_synopsis) {
        this.mov_synopsis = mov_synopsis;
    }

    public String getMov_releasedate() {
        return mov_releasedate;
    }

    public void setMov_releasedate(String mov_releasedate) {
        this.mov_releasedate = mov_releasedate;
    }

    public String getMov_poster() {
        return mov_poster;
    }

    public void setMov_poster(String mov_poster) {
        this.mov_poster = mov_poster;
    }

    public String getMov_rate_count() {
        return mov_rate_count;
    }

    public void setMov_rate_count(String mov_rate_count) {
        this.mov_rate_count = mov_rate_count;
    }

    public String getMov_rate() {
        return mov_rate;
    }

    public void setMov_rate(String mov_rate) {
        this.mov_rate = mov_rate;
    }

    public String getMov_poster_backdrop() {
        return mov_poster_backdrop;
    }

    public void setMov_poster_backdrop(String mov_poster_backdrop) {
        this.mov_poster_backdrop = mov_poster_backdrop;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.mov_title);
        dest.writeString(this.mov_synopsis);
        dest.writeString(this.mov_releasedate);
        dest.writeString(this.mov_poster);
        dest.writeString(this.mov_rate_count);
        dest.writeString(this.mov_rate);
        dest.writeString(this.mov_poster_backdrop);
    }

    public Movieitem(Cursor cursor) {
        this.id = getColumnInt(cursor, _ID);
        this.mov_title = getColumnString(cursor, TITLE_MOVIE);
        this.mov_synopsis = getColumnString(cursor, OVERVIEW);
        this.mov_releasedate = getColumnString(cursor, RELEASE_DATE);
        this.mov_poster = getColumnString(cursor, POSTER_JPG);
        this.mov_poster_backdrop = getColumnString(cursor, BACKDROP_POSTER);
    }

    public Movieitem() {
    }

    protected Movieitem(Parcel in) {
        this.id = in.readInt();
        this.mov_title = in.readString();
        this.mov_synopsis = in.readString();
        this.mov_releasedate = in.readString();
        this.mov_poster = in.readString();
        this.mov_rate_count = in.readString();
        this.mov_rate = in.readString();
        this.mov_poster_backdrop = in.readString();
    }

    public static final Creator<Movieitem> CREATOR = new Creator<Movieitem>() {
        @Override
        public Movieitem createFromParcel(Parcel source) {
            return new Movieitem(source);
        }

        @Override
        public Movieitem[] newArray(int size) {
            return new Movieitem[size];
        }
    };
}
