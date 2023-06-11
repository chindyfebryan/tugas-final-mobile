package com.h071211038.h071211038_finalmobile.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GenreListResponse {
    @SerializedName("genres")
    private List<GenreResponse> genres;

    public List<GenreResponse> getGenres() {
        return genres;
    }
}
