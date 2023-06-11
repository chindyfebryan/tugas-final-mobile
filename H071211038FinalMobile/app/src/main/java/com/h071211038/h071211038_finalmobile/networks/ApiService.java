package com.h071211038.h071211038_finalmobile.networks;

import com.h071211038.h071211038_finalmobile.models.MovieCastListResponse;
import com.h071211038.h071211038_finalmobile.models.GenreListResponse;
import com.h071211038.h071211038_finalmobile.models.MovieListResponse;
import com.h071211038.h071211038_finalmobile.models.TvShowListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("movie/now_playing")
    Call<MovieListResponse> getMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );

    @GET("{data}/{id}")
    Call<GenreListResponse> getGenres(
            @Path("data") String data,
            @Path("id") int id,
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    @GET("{data}/{id}/credits")
    Call<MovieCastListResponse> getMovieCasts(
            @Path("data") String data,
            @Path("id") int id,
            @Query("api_key") String apiKey,
            @Query("language") String language
    );
    @GET("tv/airing_today")
    Call<TvShowListResponse> getTvShows(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );
}
