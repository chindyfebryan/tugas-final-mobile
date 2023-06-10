package com.h071211038.h071211038_finalmobile.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TvShowListResponse {
    @SerializedName("results")
    private List<TvShowsResponse> results;

    public List<TvShowsResponse> getResults() {
        return results;
    }
}
