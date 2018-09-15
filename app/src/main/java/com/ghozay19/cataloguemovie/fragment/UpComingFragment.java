package com.ghozay19.cataloguemovie.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ghozay19.cataloguemovie.R;
import com.ghozay19.cataloguemovie.adapter.MovieAdapter;
import com.ghozay19.cataloguemovie.model.Response;
import com.ghozay19.cataloguemovie.model.ResultMovie;
import com.ghozay19.cataloguemovie.network.ConfigRetrofit;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

import static com.ghozay19.cataloguemovie.BuildConfig.MOVIE_API_KEY;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpComingFragment extends Fragment {

    MovieAdapter adapter;
    Context context;
    RecyclerView recyclerView;

    private ArrayList<ResultMovie> listMovie;
    private final String language = "en-US";


    public UpComingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_now_playing, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.rv_now_playing);
        listMovie = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new MovieAdapter(getActivity(), listMovie);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (savedInstanceState != null) {
            ArrayList<ResultMovie> list;
            list = savedInstanceState.getParcelableArrayList("up_coming");
            adapter = new MovieAdapter(getActivity(), list);
            recyclerView.setAdapter(adapter);
        } else {
            loadMovie();
        }

        return view;
    }

    private void loadMovie() {

        final ProgressDialog dialog =
                ProgressDialog.show(getContext(), "", "Loading", false);



        ConfigRetrofit.service.getUpComingMovie(MOVIE_API_KEY, language)
                .enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        if (response.isSuccessful()) {
                            dialog.dismiss();
                            listMovie = (ArrayList<ResultMovie>) response.body().getResults();

                            recyclerView.setAdapter(new MovieAdapter(getActivity(), listMovie));
                            adapter.notifyDataSetChanged();

                            Log.d("Status", "status" + response.body().getResults());
                        } else {
                            dialog.dismiss();
                            Toast.makeText(getContext(), "Failed to get Data !", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {
                        dialog.dismiss();
                        Toast.makeText(getContext(), "No Internet  !", Toast.LENGTH_SHORT).show();
                        Log.d(" Error", t.getMessage());
                    }
                });

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("up_coming", new ArrayList<>(listMovie));
    }



}
