package com.ghozay19.cataloguemovie;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.bumptech.glide.Glide;
import com.ghozay19.cataloguemovie.fragment.FavouritFragment;
import com.ghozay19.cataloguemovie.fragment.HomeFragment;
import com.ghozay19.cataloguemovie.fragment.SearchFragment;


import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    DrawerLayout drawer;
    Toolbar toolbar;
    ActionBarDrawerToggle toggle;

    CircleImageView profileCircleImageView;
    String profileImageUrl = "https://www.dicoding.com/images/small/avatar/20180826204655c7bdcbbc35a29526031d9284b1aa2ca2.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        profileCircleImageView = (CircleImageView) navigationView.getHeaderView(0).findViewById(R.id.imageView);

        Glide.with(MainActivity.this)
                .load(profileImageUrl)
                .into(profileCircleImageView);
        navigationView.setNavigationItemSelectedListener(this);

        /*
        Jika savedinstance masih null, maka redirect ke fragment home
        Berguna ketika aplikasi pertama dijalankan untuk mengisi halaman default
        dan berguna juga ketika config changes terjadi, karena fragment akan
        secara otomatis ditambahkan ke dalam activity,
        maka kita tidak perlu replace fragment kembali.
         */
        if (savedInstanceState == null) {
            Fragment currentFragment = new HomeFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_main, currentFragment)
                    .commit();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    protected void onPause() {
        super.onPause();
        drawer.removeDrawerListener(toggle);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            if (item.getItemId() == R.id.action_settings) {
//                Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
//                startActivity(intent);
//            }
        if (id == R.id.action_settings) {
            if (item.getItemId() == R.id.action_settings) {
                Intent intent = new Intent(this, SettingActivity.class);
                startActivity(intent);
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Bundle bundle = new Bundle();

        Fragment fragment = null;

        String title = "";
        if (id == R.id.nav_home) {

            title = "Home";
            fragment = new HomeFragment();

        } else if (id == R.id.nav_search) {

            title = "Search";
            fragment = new SearchFragment();
            fragment.setArguments(bundle);

        }else if (id == R.id.nav_favourite) {

            title = "Favourite";
            fragment = new FavouritFragment();
            fragment.setArguments(bundle);

        }else if(id == R.id.nav_language){
            title = "Catalogue Movie ";
            Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(intent);
        }

        /*
        Ganti halaman dengan memanggil fragment replace
         */

        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_main, fragment)
                    .commit();
        }

        getSupportActionBar().setTitle(title);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}