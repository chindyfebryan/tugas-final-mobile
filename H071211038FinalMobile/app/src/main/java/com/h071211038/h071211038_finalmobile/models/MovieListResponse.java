package com.h071211038.h071211038_finalmobile.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieListResponse {
    @SerializedName("results")
    private List<MovieResponse> results;

    public List<MovieResponse> getResults() {
        return results;
    }
}