package com.example.moviesorshows.network;

import com.example.moviesorshows.commons.MovieOrShow;
import com.example.moviesorshows.commons.MovieOrShowList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServiceMoviesOrShows {

    @GET("/")
    Call<MovieOrShowList> listRepos(@Query("s") String s, @Query("apikey") String apikey);
}
