package com.ghozay19.cataloguemovie.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.ghozay19.cataloguemovie.R;
import com.ghozay19.cataloguemovie.adapter.MovieAdapter;
import com.ghozay19.cataloguemovie.model.Response;
import com.ghozay19.cataloguemovie.model.ResultMovie;
import com.ghozay19.cataloguemovie.network.ConfigRetrofit;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;

import static android.support.constraint.Constraints.TAG;
import static com.ghozay19.cataloguemovie.BuildConfig.MOVIE_API_KEY;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements View.OnClickListener {

    ImageView ivPoster;
    RecyclerView recyclerView;
    MovieAdapter adapter;
    EditText etTitle;
    Button btnSearch;
    Context context;
    private ArrayList<ResultMovie> listMovie;
    String query;
    private final String language = "en-US";
    private final String sort_by = "popularity.desc";
    private final String include_adult = "false";
    private final String include_video = "false";
    private final String page = "1";

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        btnSearch = view.findViewById(R.id.btn_cari_film);
        etTitle = view.findViewById(R.id.et_cari_film);
        ivPoster = view.findViewById(R.id.poster_mov);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        listMovie = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new MovieAdapter(getActivity(), listMovie);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        btnSearch.setOnClickListener(this);

//        adapter = new MovieAdapter(getActivity());
//        adapter.notifyDataSetChanged();
//
//        listView = (ListView) view.findViewById(R.id.listView);
//        listView.setAdapter(adapter);

        loadMovie();
        
        return view;
    }

    private void loadMovie() {
        ConfigRetrofit.service.getAllMovies(MOVIE_API_KEY,language,sort_by,include_adult,include_video,page)
                .enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(retrofit2.Call<Response> call, retrofit2.Response<Response> response) {
                        listMovie = (ArrayList<ResultMovie>) response.body().getResults();

                        recyclerView.setAdapter(new MovieAdapter(getActivity(), listMovie));
                        adapter.notifyDataSetChanged();

                        Log.d("Status", "status" + response.body().getResults());


                    }

                    @Override
                    public void onFailure(retrofit2.Call<Response> call, Throwable t) {
                        Log.d(" Error", t.getMessage());
                        Toast.makeText(getContext(), "Failed to get Data !", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public void onClick(View v) {
        if (v.getId() == R.id.btn_cari_film){
             query = etTitle.getText().toString();
            if (query.isEmpty()){
                Toast.makeText(getActivity(), "Judul Harus di isi", Toast.LENGTH_SHORT).show();

            }else {
                Log.e(TAG,"Data = "+query);
                getSearchMovie(query);
            }

        }
    }

    private void getSearchMovie(String query) {

        ConfigRetrofit.service.searchMovie(MOVIE_API_KEY, language, query)
                .enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(retrofit2.Call<Response> call, retrofit2.Response<Response> response) {
                        listMovie = (ArrayList<ResultMovie>) response.body().getResults();
                        recyclerView.setAdapter(new MovieAdapter(getActivity(), listMovie));
                        adapter.notifyDataSetChanged();
                        Log.d("Status", "status" + response.body().getResults());

                    }

                    @Override
                    public void onFailure(retrofit2.Call<Response> call, Throwable t) {
                        Log.d(" Error", t.getMessage());
                        Toast.makeText(getContext(), "Failed to get Data !", Toast.LENGTH_SHORT).show();
                    }
                });



    }


}