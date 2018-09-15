package com.ghozay19.cataloguemovie.Scheduler;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ghozay19.cataloguemovie.DetailActivity;
import com.ghozay19.cataloguemovie.R;
import com.ghozay19.cataloguemovie.model.ResultMovie;

public class AlarmReleaseReceiver extends BroadcastReceiver {

    
    
    @Override
    public void onReceive(Context context, Intent intent) {


        String title = intent.getStringExtra("movietitle");
        int id = intent.getIntExtra("id", 0);
        long id_movie = intent.getLongExtra("movieid", 0);
        String poster_jpg = intent.getStringExtra("movieposter");
        String poster_backdrop = intent.getStringExtra("movieback");
        String release_date = intent.getStringExtra("moviedate");
//        Double movie_rate = intent.getDoubleExtra("movierating", 0);
//        String overview = intent.getStringExtra("movieover");
//        ResultMovie resultMovie = new ResultMovie(id_movie, title, poster_jpg, poster_backdrop, release_date, overview);
//        String desc = String.valueOf(String.format(context.getString(R.string.release_today_reminder), title));
//        sendNotification(context, context.getString(R.string.app_name), desc, id, resultMovie);
    }

    private void sendNotification(Context context, String string, String desc, int id, ResultMovie resultMovie) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(
                Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra("resultMovie", resultMovie);
    }
}
