package com.ghozay19.cataloguemovie.adapter;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
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
import com.ghozay19.cataloguemovie.model.Movieitem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.ViewHolder> {


    private Cursor movieCursor;
    private Activity activity;

    public FavouriteAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setListMovie(Cursor movieCursor) {
        this.movieCursor = movieCursor;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Movieitem movieitem = getItem(position);
        Glide.with(activity).load("http://image.tmdb.org/t/p/w154/" + movieitem.getMov_poster()).into(holder.imageView);
        holder.tvTitle.setText(movieitem.getMov_title());
        holder.tvOverview.setText(movieitem.getMov_synopsis());

        String retrieveDate = movieitem.getMov_releasedate();
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date = date_format.parse(retrieveDate);
            SimpleDateFormat new_date_format = new SimpleDateFormat("EEEE, dd MMMM yyyy");
            String release_date = new_date_format.format(date);
            holder.tvRelease.setText(release_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, DetailActivity.class);
                //TODO tambahin id

                intent.putExtra("title", movieitem.getMov_title());
                intent.putExtra("overview", movieitem.getMov_synopsis());
                intent.putExtra("poster_path", movieitem.getMov_poster());
                intent.putExtra("backdrop_path", movieitem.getMov_poster_backdrop());
                intent.putExtra("release_date", movieitem.getMov_releasedate());
                activity.startActivity(intent);
            }
        });
    }


    private Movieitem getItem(int position){
        if (!movieCursor.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid");
        }
        return new Movieitem(movieCursor);
    }

    @Override
    public int getItemCount() {
        if (movieCursor == null) return 0;
        return movieCursor.getCount();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private Button btnDetail;
        private ImageView imageView;
        private TextView tvTitle, tvOverview, tvRelease;


        ViewHolder(View itemView) {
            super(itemView);
            btnDetail = itemView.findViewById(R.id.btn_detail);
            imageView = itemView.findViewById(R.id.iv_poster_cv);
            tvTitle = itemView.findViewById(R.id.title_detail_cv);
            tvOverview = itemView.findViewById(R.id.overview_detail_cv);
            tvRelease = itemView.findViewById(R.id.release_date_detail_cv);

        }
    }


}
