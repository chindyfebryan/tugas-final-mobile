package com.h071211038.h071211038_finalmobile.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.h071211038.h071211038_finalmobile.R;
import com.h071211038.h071211038_finalmobile.models.MovieResponse;
import com.h071211038.h071211038_finalmobile.models.TvShowsResponse;

public class DetailActivity extends AppCompatActivity {

    public static String KEY_MOVIE= "KEY_MOVIE";
    public static String KEY_TV_SHOW= "KEY_TV_SHOW";
    public static String KEY_IS_MOVIE = "KEY_IS_MOVIE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView tvTitle = findViewById(R.id.title);
        TextView tvOverview = findViewById(R.id.overview);
        ImageView ivBackdrop = findViewById(R.id.backdrop);
        ImageView ivPoster = findViewById(R.id.poster);

        boolean isMovie = getIntent().getBooleanExtra(KEY_IS_MOVIE, true);

        if (isMovie) {
            MovieResponse movieResponse = getIntent().getParcelableExtra(KEY_MOVIE);

            Glide.with(this)
                    .load("https://image.tmdb.org/t/p/original" + movieResponse.getBackdropPath())
                    .into(ivBackdrop);
            Glide.with(this)
                    .load("https://image.tmdb.org/t/p/original" + movieResponse.getPosterPath())
                    .into(ivPoster);
            tvTitle.setText(movieResponse.getTitle());
            tvOverview.setText(movieResponse.getOverview());
        } else {
            TvShowsResponse tvShowsResponse = getIntent().getParcelableExtra(KEY_TV_SHOW);

            Glide.with(this)
                    .load("https://image.tmdb.org/t/p/original" + tvShowsResponse.getBackdropPath())
                    .into(ivBackdrop);
            Glide.with(this)
                    .load("https://image.tmdb.org/t/p/original" + tvShowsResponse.getPosterPath())
                    .into(ivPoster);
            tvTitle.setText(tvShowsResponse.getName());
            tvOverview.setText(tvShowsResponse.getOverview());
        }

    }
}