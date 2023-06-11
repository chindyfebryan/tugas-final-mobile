package com.h071211038.h071211038_finalmobile.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieCastListResponse {
    @SerializedName("cast")
    private List<MovieCastResponse> casts;

    public List<MovieCastResponse> getCasts() {
        return casts;
    }
}
