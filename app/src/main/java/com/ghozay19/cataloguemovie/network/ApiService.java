package com.ghozay19.cataloguemovie.network;

import com.ghozay19.cataloguemovie.model.Response;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("3/discover/movie")
    Call<Response> getAllMovies(@Query("api_key") String api_key,
                                @Query("language") String language,
                                @Query("sort_by") String sort_by,
                                @Query("include_adult") String include_adult,
                                @Query("include_video") String include_video,
                                @Query("page") String page );
    @GET("/3/search/movie")
    Call<Response> searchMovie(@Query("api_key") String api_key,
                                     @Query("language") String language,
                                     @Query("query") String query);

    @GET("/3/movie/now_playing")
    Call<Response> getNowPlayingMovie(@Query("api_key") String api_key,
                                            @Query("language") String language);

    @GET("/3/movie/upcoming")
    Call<Response> getUpComingMovie(@Query("api_key") String api_key,
                                          @Query("language") String language);

}
