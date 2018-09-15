package com.ghozay19.cataloguemovie;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ghozay19.cataloguemovie.db.MovieHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.provider.BaseColumns._ID;
import static com.ghozay19.cataloguemovie.db.DatabaseContract.MovieColumns.BACKDROP_POSTER;
import static com.ghozay19.cataloguemovie.db.DatabaseContract.MovieColumns.CONTENT_URI;
import static com.ghozay19.cataloguemovie.db.DatabaseContract.MovieColumns.OVERVIEW;
import static com.ghozay19.cataloguemovie.db.DatabaseContract.MovieColumns.POSTER_JPG;
import static com.ghozay19.cataloguemovie.db.DatabaseContract.MovieColumns.RELEASE_DATE;
import static com.ghozay19.cataloguemovie.db.DatabaseContract.MovieColumns.TITLE_MOVIE;

public class DetailActivity extends AppCompatActivity {


    @BindView(R.id.title_mov_detail)
    TextView tvTitle;

    @BindView(R.id.synopsis_mov_detail)
    TextView tvOverview;

    @BindView(R.id.release_date_detail)
    TextView tvReleaseDate;

    @BindView(R.id.poster_mov_detail)
    ImageView imgPoster;

    @BindView(R.id.fabFavourite)
    FloatingActionButton fabFavorit;

    String title, overview, poster_backdrop, poster_jpg, release_date;
    private int id_movie;
    private Boolean isFavourite = false;
    MovieHelper movieHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        setToolbar();
        setData();
        backButton();
        loadData();


        fabFavorit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFavourite) {
                    movieHelper.open();
                    movieHelper.deleteProvider(String.valueOf(id_movie));
                    movieHelper.close();
                    Toast.makeText(DetailActivity.this, title + " Sudah di hapus dari Favourite", Toast.LENGTH_SHORT).show();
                } else {
                    ContentValues cv = new ContentValues();
                    cv.put(_ID, id_movie);
                    cv.put(TITLE_MOVIE, title);
                    cv.put(RELEASE_DATE, release_date);
                    cv.put(OVERVIEW, overview);
                    cv.put(POSTER_JPG, poster_jpg);
                    cv.put(BACKDROP_POSTER, poster_backdrop);
                    getContentResolver().insert(CONTENT_URI, cv);
                    Toast.makeText(DetailActivity.this, title + " Berhasil di tambah ke Favourite", Toast.LENGTH_SHORT).show();
                    isFavourite = true;
                }

            }
        });

    }

    private void loadData() {
        movieHelper = new MovieHelper(this);
        movieHelper.open();
        isFavourite = movieHelper.getMoviebyId(String.valueOf(id_movie));
        movieHelper.close();
    }


    private void setData() {
        id_movie = getIntent().getIntExtra("id",0);
        title = getIntent().getStringExtra("title");
        overview = getIntent().getStringExtra("overview");
        poster_backdrop = getIntent().getStringExtra("backdrop_path");
        release_date = getIntent().getStringExtra("release_date");
        poster_jpg = getIntent().getStringExtra("poster_path");

        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = date_format.parse(release_date);

            SimpleDateFormat new_date_format = new SimpleDateFormat("EEEE, dd MMMM yyyy");
            String date_of_release = new_date_format.format(date);
            tvReleaseDate.setText(date_of_release);

        } catch (ParseException e) {
            e.printStackTrace();
        }

//        tvTitle.setText(title);
        tvOverview.setText(overview);
        Glide.with(getApplicationContext()).load("http://image.tmdb.org/t/p/w500/" + poster_backdrop).into(imgPoster);
    }


    private void setToolbar() {
        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);


        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(title);


        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);


    }

    //untuk menampilkan back button
    public void backButton() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));
    }
}


//    private boolean loadData() {
//        Uri uri = Uri.parse(CONTENT_URI+"");
//        boolean favourite = false;
//
//        Cursor cursor = getContentResolver().query(uri,null,null,null,null);
//
//        String getTitle;
//        if (cursor.moveToFirst()){
//            do {
//                id = cursor.getLong(0);
//                getTitle = cursor.getString(1);
//                if (getTitle.equals(getIntent().getStringExtra("title"))){
//                    fabFavorit.setImageResource(R.drawable.heart_red);
//                    favourite = true;
//                }
//            }while (cursor.moveToNext());
//        }
//        return favourite;
//    }
//
//

//
//    private void favourite(View view){
//        if (loadData()){
//            Uri uri = Uri.parse(CONTENT_URI+"/"+id);
//            getContentResolver().delete(uri,null,null);
//            fabFavorit.setImageResource(R.drawable.heart);
//        }else {
//            ContentValues cv = new ContentValues();
//            cv.put(_ID, id_movie);
//            cv.put(TITLE_MOVIE, title);
//            cv.put(RELEASE_DATE, release_date);
//            cv.put(OVERVIEW, overview);
//            cv.put(POSTER_JPG, poster_jpg);
//            cv.put(BACKDROP_POSTER, poster_backdrop);
//            getContentResolver().insert(CONTENT_URI, cv);
//            fabFavorit.setImageResource(R.drawable.heart_red);
//            Toast.makeText(DetailActivity.this,title+" Berhasil di tambah ke Favourite", Toast.LENGTH_SHORT).show();
//
//            setResult(101);
//        }
//    }
//
//
//}
