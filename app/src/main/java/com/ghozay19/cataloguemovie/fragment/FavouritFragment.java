package com.ghozay19.cataloguemovie.fragment;


import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ghozay19.cataloguemovie.R;
import com.ghozay19.cataloguemovie.adapter.FavouriteAdapter;

import static com.ghozay19.cataloguemovie.db.DatabaseContract.MovieColumns.CONTENT_URI;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavouritFragment extends Fragment {



    RecyclerView recyclerView;

    private Cursor cursor;
    private FavouriteAdapter favouriteAdapter;

    public FavouritFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favourit, container, false);

        recyclerView = view.findViewById(R.id.rv_favourite);
        favouriteAdapter = new FavouriteAdapter(getActivity());
        favouriteAdapter.notifyDataSetChanged();
        favouriteAdapter.setListMovie(cursor);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(favouriteAdapter);
        new loadMovieAsyncTask().execute();

        return view;

    }

    @Override
    public void onResume(){
        super.onResume();
        new loadMovieAsyncTask().execute();
    }

    private class loadMovieAsyncTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return getActivity().getContentResolver().query(
                    CONTENT_URI,
                    null,
                    null,
                    null,
                    null
            );
        }

        @Override
        protected void onPostExecute(Cursor cursor){
            super.onPostExecute(cursor);

            cursor = cursor;
            favouriteAdapter.setListMovie(cursor);
            favouriteAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public  void onDestroy(){
        super.onDestroy();
    }


}

