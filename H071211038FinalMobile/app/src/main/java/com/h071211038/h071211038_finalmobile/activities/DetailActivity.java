package com.h071211038.h071211038_finalmobile.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.h071211038.h071211038_finalmobile.R;
import com.h071211038.h071211038_finalmobile.models.MovieResponse;
import com.h071211038.h071211038_finalmobile.models.TvShowsResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {

    public static String KEY_MOVIE= "KEY_MOVIE";
    public static String KEY_TV_SHOW= "KEY_TV_SHOW";
    public static String KEY_IS_MOVIE = "KEY_IS_MOVIE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView tvTitle = findViewById(R.id.title);
        TextView tvRate = findViewById(R.id.rate);
        TextView tvReleaseDate = findViewById(R.id.release_date);
        TextView tvOverview = findViewById(R.id.overview);
        ImageView ivBackdrop = findViewById(R.id.backdrop);
        ImageView ivPoster = findViewById(R.id.poster);
        ImageView ivIcon = findViewById(R.id.icon);
        ImageView btnBack = findViewById(R.id.back_button);
        ImageView btnAddToFavorite = findViewById(R.id.add_to_favorite_button);

        boolean isMovie = getIntent().getBooleanExtra(KEY_IS_MOVIE, true);

        if (isMovie) {
            MovieResponse movieResponse = getIntent().getParcelableExtra(KEY_MOVIE);

            Glide.with(this)
                    .load("https://image.tmdb.org/t/p/original" + movieResponse.getBackdropPath())
                    .into(ivBackdrop);
            Glide.with(this)
                    .load("https://image.tmdb.org/t/p/original" + movieResponse.getPosterPath())
                    .into(ivPoster);
            ivIcon.setImageResource(R.drawable.baseline_movie_24_dark_blue);
            tvTitle.setText(movieResponse.getTitle());
            tvReleaseDate.setText(convertDateFormat(movieResponse.getReleaseDate()));
            tvRate.setText(String.valueOf(movieResponse.getVoteAverage()));
            tvOverview.setText(movieResponse.getOverview());
        } else {
            TvShowsResponse tvShowsResponse = getIntent().getParcelableExtra(KEY_TV_SHOW);

            Glide.with(this)
                    .load("https://image.tmdb.org/t/p/original" + tvShowsResponse.getBackdropPath())
                    .into(ivBackdrop);
            Glide.with(this)
                    .load("https://image.tmdb.org/t/p/original" + tvShowsResponse.getPosterPath())
                    .into(ivPoster);
            ivIcon.setImageResource(R.drawable.baseline_tv_24_dark_blue);
            tvTitle.setText(tvShowsResponse.getName());
            tvReleaseDate.setText(convertDateFormat(tvShowsResponse.getFirstAirDate()));
            tvRate.setText(String.valueOf(tvShowsResponse.getVoteAverage()));
            tvOverview.setText(tvShowsResponse.getOverview());
        }

        btnBack.setOnClickListener(view -> finish());

        btnAddToFavorite.setOnClickListener(view -> {

        });
    }


    public static String convertDateFormat(String dateString) {
        try {
            SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

            Date date = sourceFormat.parse(dateString);

            SimpleDateFormat targetFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH);

            return targetFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return ""; // Kembalikan string kosong jika terjadi kesalahan konversi
        }
    }
}