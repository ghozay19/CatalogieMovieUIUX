package com.ghozay19.myfavouritemovie;


import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.ghozay19.myfavouritemovie.DatabaseContract.MovieColumns.OVERVIEW;
import static com.ghozay19.myfavouritemovie.DatabaseContract.MovieColumns.POSTER_JPG;
import static com.ghozay19.myfavouritemovie.DatabaseContract.MovieColumns.RELEASE_DATE;
import static com.ghozay19.myfavouritemovie.DatabaseContract.MovieColumns.TITLE_MOVIE;
import static com.ghozay19.myfavouritemovie.DatabaseContract.getColumnString;

public class MyFavAdapter extends CursorAdapter {

    public MyFavAdapter(Context context, Cursor cursor, boolean autoRequery) {
        super(context, cursor, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_fav, viewGroup, false);
        return view;
    }

    @Override
    public Cursor getCursor() {
        return super.getCursor();
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        if (cursor != null) {

            ImageView ivPoster;
            TextView tvTitle, tvSynopsis, tvReleaseDate;
            CardView cardView;


            tvTitle = view.findViewById(R.id.title_mov);
            tvSynopsis = view.findViewById(R.id.synopsis_mov);
            tvReleaseDate = view.findViewById(R.id.release_date);
            cardView = view.findViewById(R.id.card_view);
            ivPoster = view.findViewById(R.id.poster_jpg);

            tvTitle.setText(getColumnString(cursor, TITLE_MOVIE));
            tvSynopsis.setText(getColumnString(cursor,OVERVIEW));
            tvReleaseDate.setText(getColumnString(cursor, RELEASE_DATE));
            final  String poster = "http://image.tmdb.org/t/p/w185" + getColumnString(cursor, POSTER_JPG);
            Glide.with(context)
                    .load(poster)
                    .into(ivPoster);

            String retrieveDate =getColumnString(cursor, RELEASE_DATE);
            SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");

            try {
                Date date = date_format.parse(retrieveDate);
                SimpleDateFormat new_date_format = new SimpleDateFormat("EEEE, dd MMMM yyyy");
                String release_date = new_date_format.format(date);
                tvReleaseDate.setText(release_date);
            } catch (ParseException e) {
                e.printStackTrace();
            }


        }
    }


}
