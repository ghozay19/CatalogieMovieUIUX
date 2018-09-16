package com.ghozay19.cataloguemovie;


import android.content.Intent;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.ghozay19.cataloguemovie.Scheduler.AlarmDailyReceiver;
import com.ghozay19.cataloguemovie.Scheduler.AlarmReleaseReceiver;
import com.ghozay19.cataloguemovie.model.Response;
import com.ghozay19.cataloguemovie.model.ResultMovie;
import com.ghozay19.cataloguemovie.network.ConfigRetrofit;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;

import static com.ghozay19.cataloguemovie.BuildConfig.MOVIE_API_KEY;

public class SettingActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }


    public static class MyPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener, Preference.OnPreferenceClickListener {


        ArrayList<ResultMovie> listMovie;
        String language;
        AlarmDailyReceiver alarmDailyReceiver = new AlarmDailyReceiver();
        AlarmReleaseReceiver alarmReleaseReceiver = new AlarmReleaseReceiver();

        String dailyReminder, upComingReminder, settingLocale;

        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);

            dailyReminder = getResources().getString(R.string.key_daily_reminder);
            upComingReminder = getResources().getString(R.string.key_upcoming_reminder);
            settingLocale = getResources().getString(R.string.key_setting_locale);

            findPreference(dailyReminder).setOnPreferenceChangeListener(this);
            findPreference(upComingReminder).setOnPreferenceChangeListener(this);
            findPreference(settingLocale).setOnPreferenceChangeListener(this);

        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object object) {
            String key = preference.getKey();
            boolean isOn = (boolean) object;


            if (key.equals(dailyReminder)) {
                if (isOn) {
                    alarmDailyReceiver.setDailyReminderAlarm(getActivity());
                } else {
                    alarmDailyReceiver.cancelAlarm(getActivity());
                }
                Toast.makeText(getActivity(), getString(R.string.alarm_notif) + " " + (isOn ? getString(R.string.active) : getString(R.string.deactivated)), Toast.LENGTH_SHORT).show();
                return true;

            }
            if (key.equals(upComingReminder)) {
                if (isOn) {
                    setReleaseAlarm();

                } else
                    alarmReleaseReceiver.cancelAlarm(getActivity());

                Toast.makeText(getActivity(), getString(R.string.alarm_upcoming) + " " + (isOn ? getString(R.string.active) : getString(R.string.deactivated)), Toast.LENGTH_SHORT).show();
                return true;
            }


            return false;
        }

        private void setReleaseAlarm() {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = new Date();
            final String now = dateFormat.format(date);


            ConfigRetrofit.service.getUpComingMovie(MOVIE_API_KEY, language)
                    .enqueue(new Callback<Response>() {
                        @Override
                        public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                            listMovie = (ArrayList<ResultMovie>) response.body().getResults();
                            for (ResultMovie resultMovie : listMovie) {
                                if (resultMovie.getReleaseDate().equals(now)) {
                                    listMovie.add(resultMovie);
                                    Log.d("onSuccess", "" + listMovie.size());
                                }
                            }
                            alarmReleaseReceiver.setReleaseReminderAlarm(getActivity(), listMovie);
                        }


                        @Override
                        public void onFailure(Call<Response> call, Throwable t) {
                            Toast.makeText(getActivity(), "EROR"
                                    , Toast.LENGTH_SHORT).show();
                            Log.d(" Error", t.getMessage());
                        }
                    });


        }

        @Override
        public boolean onPreferenceClick(Preference preference) {
            String key = preference.getKey();

            if (key.equals(settingLocale)) {
                Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(intent);
                return true;
            }

            return false;
        }
    }
}