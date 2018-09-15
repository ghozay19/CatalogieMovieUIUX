package com.ghozay19.cataloguemovie;


import android.content.Intent;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.ghozay19.cataloguemovie.Scheduler.AlarmDailyReceiver;
import com.ghozay19.cataloguemovie.Scheduler.AlarmReleaseReceiver;

public class SettingActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();

//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }




    public static class MyPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener, Preference.OnPreferenceClickListener {
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
                    // ini udah bener
                    alarmDailyReceiver.setDailyReminderAlarm(getActivity(), AlarmDailyReceiver.TYPE_REPEATING, "08:52", getString(R.string.label_alarm_daily_reminder));
                } else {
                    alarmDailyReceiver.cancelAlarm(getActivity(), AlarmDailyReceiver.TYPE_REPEATING);
                }
                Toast.makeText(getActivity(), getString(R.string.alarm_notif) + " " + (isOn ? getString(R.string.active) : getString(R.string.deactivated)), Toast.LENGTH_SHORT).show();
                return true;

            }
            if (key.equals(upComingReminder)) {
                if (isOn) {
                    //notif ga muncul
//                    alarmReleaseReceiver.setReleaseReminderAlarm(getActivity(), AlarmReleaseReceiver.TYPE_RELEASED, "08:52", getString(R.string.alarm_upcoming));

                } else
//                    alarmReleaseReceiver.cancelAlarm(getActivity(), AlarmReleaseReceiver.TYPE_RELEASED);

                Toast.makeText(getActivity(), getString(R.string.alarm_upcoming) + " " + (isOn ? getString(R.string.active) : getString(R.string.deactivated)), Toast.LENGTH_SHORT).show();
                return true;
            }


            return false;
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