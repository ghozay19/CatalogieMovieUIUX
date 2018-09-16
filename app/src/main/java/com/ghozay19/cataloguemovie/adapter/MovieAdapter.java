package com.ghozay19.cataloguemovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ghozay19.cataloguemovie.DetailActivity;
import com.ghozay19.cataloguemovie.R;
import com.ghozay19.cataloguemovie.model.ResultMovie;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {


    ArrayList<ResultMovie> movieList;
    Context context;

    public MovieAdapter(Context context, ArrayList<ResultMovie> movieList) {
        this.context = context;
        this.movieList = movieList;

    }


    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_detail, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, final int position) {
        final ResultMovie resultMovie = movieList.get(position);

        holder.tvTitle.setText(resultMovie.getTitle());
        holder.tvOverview.setText(resultMovie.getOverview());

        Glide.with(context).load("http://image.tmdb.org/t/p/w154/" + movieList.get(position)
                .getPosterPath()).placeholder(context.getResources()
                .getDrawable(R.drawable.ic_launcher_background))
                .error(context.getResources().getDrawable(R.drawable.image)).into(holder.ivPoster);

        String retrieveDate = movieList.get(position).getReleaseDate();
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date = date_format.parse(retrieveDate);
            SimpleDateFormat new_date_format = new SimpleDateFormat("EEEE, dd MMMM yyyy");
            String release_date = new_date_format.format(date);
            holder.tvReleaseDate.setText(release_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.btn_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("id", resultMovie.getId());
                intent.putExtra("title", resultMovie.getTitle());
                intent.putExtra("overview", resultMovie.getOverview());
                intent.putExtra("poster_path", resultMovie.getPosterPath());
                intent.putExtra("backdrop_path", resultMovie.getBackdropPath());
                intent.putExtra("release_date", resultMovie.getReleaseDate());
                context.startActivity(intent);
            }
        });


        holder.btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TITLE,resultMovie.getTitle());
                intent.putExtra(Intent.EXTRA_TEXT,resultMovie.getTitle() + "\n\n" + resultMovie.getVoteAverage());
                intent.putExtra(Intent.EXTRA_TEXT,resultMovie.getVoteAverage() + "\n\n" +  resultMovie.getOverview());
                context.startActivity(Intent.createChooser(intent, context.getResources().getString(R.string.share)));
            }
        });

    }

    @Override
    public int getItemCount() {
        if (movieList == null) return 0;

        return movieList.size();

    }


    public class MovieViewHolder extends RecyclerView.ViewHolder {

        ImageView ivPoster;
        TextView tvTitle;
        TextView tvOverview;
        TextView tvReleaseDate;
        Button btn_detail;
        Button btn_share;

        public MovieViewHolder(View itemView) {
            super(itemView);
            ivPoster = (ImageView) itemView.findViewById(R.id.iv_poster_cv);
            tvTitle = (TextView) itemView.findViewById(R.id.title_detail_cv);
            tvOverview = (TextView) itemView.findViewById(R.id.overview_detail_cv);
            tvReleaseDate = (TextView) itemView.findViewById(R.id.release_date_detail_cv);
            btn_detail = (Button) itemView.findViewById(R.id.btn_detail);
            btn_share = (Button) itemView.findViewById(R.id.btn_share);
        }
    }
}
