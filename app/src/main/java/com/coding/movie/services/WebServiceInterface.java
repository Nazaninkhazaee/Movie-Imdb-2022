package com.coding.movie.services;

import com.coding.movie.model.search.SearchMoviesWebModels;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WebServiceInterface {

    //http://www.omdbapi.com/?t=Harry+potter&apikey=d0135a7f
    @GET("/")
    Call<SearchMoviesWebModels> searchMovies(
            @Query("t") String movie ,
            @Query("apikey") String apikey);

}
