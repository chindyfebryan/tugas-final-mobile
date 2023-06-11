package com.h071211038.h071211038_finalmobile.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.h071211038.h071211038_finalmobile.R;
import com.h071211038.h071211038_finalmobile.db.DatabaseContract;
import com.h071211038.h071211038_finalmobile.db.FavoriteHelper;
import com.h071211038.h071211038_finalmobile.entity.Favorite;
import com.h071211038.h071211038_finalmobile.fragments.CastFragment;
import com.h071211038.h071211038_finalmobile.fragments.MoviesFragment;
import com.h071211038.h071211038_finalmobile.models.GenreResponse;
import com.h071211038.h071211038_finalmobile.models.MovieCastListResponse;
import com.h071211038.h071211038_finalmobile.models.MovieCastResponse;
import com.h071211038.h071211038_finalmobile.models.GenreListResponse;
import com.h071211038.h071211038_finalmobile.models.MovieResponse;
import com.h071211038.h071211038_finalmobile.models.TvShowsResponse;
import com.h071211038.h071211038_finalmobile.networks.ApiConfig;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    public static String KEY_MOVIE= "KEY_MOVIE";
    public static String KEY_TV_SHOW= "KEY_TV_SHOW";
    private String title, backdropPath, posterPath, releaseDate, overview;
    private int icon, dataId;
    private Double voteAverage;
    private TextView tvGenres;
    private FavoriteHelper favoriteHelper;
    private MovieResponse movieResponse;
    private TvShowsResponse tvShowsResponse;
    private boolean isFavorite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        favoriteHelper = FavoriteHelper.getInstance(getApplicationContext());

        TextView tvTitle = findViewById(R.id.title);
        TextView tvRate = findViewById(R.id.rate);
        TextView tvReleaseDate = findViewById(R.id.release_date);
        TextView tvOverview = findViewById(R.id.overview);
        tvGenres = findViewById(R.id.genres);
        ImageView ivBackdrop = findViewById(R.id.backdrop);
        ImageView ivPoster = findViewById(R.id.poster);
        ImageView ivIcon = findViewById(R.id.icon);
        ImageView btnBack = findViewById(R.id.back_button);
        ImageView btnAddToFavorite = findViewById(R.id.add_to_favorite_button);

        movieResponse = getIntent().getParcelableExtra(KEY_MOVIE);
        tvShowsResponse = getIntent().getParcelableExtra(KEY_TV_SHOW);

        if (movieResponse != null) {
            icon = R.drawable.baseline_movie_24_dark_blue;

            dataId = movieResponse.getId();
            backdropPath = movieResponse.getBackdropPath();
            posterPath = movieResponse.getPosterPath();
            title = movieResponse.getTitle();
            releaseDate = movieResponse.getReleaseDate();
            voteAverage = movieResponse.getVoteAverage();
            overview = movieResponse.getOverview();

        } else if (tvShowsResponse != null){

            icon = R.drawable.baseline_tv_24_dark_blue;

            dataId = tvShowsResponse.getId();
            backdropPath = tvShowsResponse.getBackdropPath();
            posterPath = tvShowsResponse.getPosterPath();
            title = tvShowsResponse.getName();
            releaseDate = tvShowsResponse.getFirstAirDate();
            voteAverage = tvShowsResponse.getVoteAverage();
            overview = tvShowsResponse.getOverview();

        } else {
            Favorite favorite = getIntent().getParcelableExtra("favorite");
            icon = favorite.getIcon();
            dataId = favorite.getDataId();
            backdropPath = favorite.getBackdropPath();
            posterPath = favorite.getPosterPath();
            title = favorite.getTitle();
            releaseDate = favorite.getReleaseDate();
            voteAverage = favorite.getVoteAverage();
            overview = favorite.getOverview();

        }

        Glide.with(this)
                .load("https://image.tmdb.org/t/p/original" + backdropPath)
                .placeholder(R.drawable.baseline_broken_image_24)
                .into(ivBackdrop);
        Glide.with(this)
                .load("https://image.tmdb.org/t/p/original" + posterPath)
                .into(ivPoster);
        ivIcon.setImageResource(icon);
        tvTitle.setText(title);
        tvReleaseDate.setText(convertDateFormat(releaseDate));
        tvRate.setText(String.valueOf(voteAverage));
        tvOverview.setText(overview);

        String type = icon == R.drawable.baseline_movie_24_dark_blue ? "movie" : "tv";
        showGenres(dataId, type);
        showMovieCasts(dataId, type);

        btnBack.setOnClickListener(view -> {
            setResult(0);
            finish();
        });

        isFavorite = isFavoriteMovie(dataId);

        if (isFavorite) {
            btnAddToFavorite.setImageResource(R.drawable.baseline_favorite_24);
        } else {
            btnAddToFavorite.setImageResource(R.drawable.baseline_favorite_border_24_dark_blue);
        }

        btnAddToFavorite.setOnClickListener(view -> {
            favoriteHelper.open();
            if (!isFavorite) {
                btnAddToFavorite.setImageResource(R.drawable.baseline_favorite_24);

                ContentValues values = new ContentValues();
                values.put(DatabaseContract.FavoriteColumns.DATA_ID, dataId);
                values.put(DatabaseContract.FavoriteColumns.ICON, icon);
                values.put(DatabaseContract.FavoriteColumns.BACKDROP_PATH, backdropPath);
                values.put(DatabaseContract.FavoriteColumns.POSTER_PATH, posterPath);
                values.put(DatabaseContract.FavoriteColumns.TITLE, title);
                values.put(DatabaseContract.FavoriteColumns.OVERVIEW, overview);
                values.put(DatabaseContract.FavoriteColumns.RELEASE_DATE, releaseDate);
                values.put(DatabaseContract.FavoriteColumns.VOTE_AVERAGE, voteAverage);
                favoriteHelper.insert(values);

                Toast.makeText(this, title + " added to favorites successfully", Toast.LENGTH_SHORT).show();

            } else {
                favoriteHelper.deleteById(dataId);
                btnAddToFavorite.setImageResource(R.drawable.baseline_favorite_border_24_dark_blue);
                Toast.makeText(this, title + " removed from favorites successfully", Toast.LENGTH_SHORT).show();
            }
            favoriteHelper.close();

            isFavorite = isFavoriteMovie(dataId);

        });
    }

    private boolean isFavoriteMovie(int dataId) {
        favoriteHelper.open();

        Cursor cursor = favoriteHelper.search(dataId);

        boolean isFavorite = cursor.getCount() > 0;
        favoriteHelper.close();

        return isFavorite;
    }

    public static String convertDateFormat(String dateString) {
        try {
            SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

            Date date = sourceFormat.parse(dateString);

            SimpleDateFormat targetFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH);

            return targetFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    private void showGenres(int dataId, String data) {
        Call<GenreListResponse> client = ApiConfig.getApiService().getGenres(data, dataId, MoviesFragment.API_KEY, "en-US");
        client.enqueue(new Callback<GenreListResponse>() {
            @Override
            public void onResponse(Call<GenreListResponse> call, Response<GenreListResponse>
                    response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        List<GenreResponse> genreResponses = response.body().getGenres();
                        StringBuilder genresStringBuilder = new StringBuilder();

                        for (GenreResponse genreResponse : genreResponses) {
                            String genreName = genreResponse.getName();
                            genresStringBuilder.append(genreName);
                            genresStringBuilder.append(", ");
                        }

                        if (genresStringBuilder.length() > 0) {
                            genresStringBuilder.setLength(genresStringBuilder.length() - 2);
                        }

                        String concatenatedGenres = genresStringBuilder.toString();
                        tvGenres.setText(concatenatedGenres);

                    }
                } else {
                    if (response.body() != null) {
                        Log.e("MainActivity", "onFailure: " + response.message());
                    }
                }

            }
            @Override
            public void onFailure(Call<GenreListResponse> call, Throwable t) {
                tvGenres.setText("unavailable");
                Log.e("MainActivity", "onFailure: " + t.getMessage());
            }
        });
    }
    private void showMovieCasts(int dataId, String data) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        CastFragment castFragment = new CastFragment(dataId, data);

        fragmentManager.beginTransaction()
                .add(R.id.fragment_container, castFragment, MoviesFragment.class.getSimpleName())
                .commit();
    }

}