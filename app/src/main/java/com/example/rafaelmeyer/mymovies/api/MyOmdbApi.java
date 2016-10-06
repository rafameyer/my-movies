package com.example.rafaelmeyer.mymovies.api;

import com.example.rafaelmeyer.mymovies.model.MovieDetails;
import com.example.rafaelmeyer.mymovies.model.Search;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by rafael.meyer on 10/3/16.
 */
public interface MyOmdbApi {

    @GET("?s=house")
    Call<Search> getListCall();

    @GET("/")
    Call<MovieDetails> getMovieByID(@Query("i") String imdbID);

    @GET("/")
    Call<Search> getListSearchCall(@Query("s") String movie);

}
