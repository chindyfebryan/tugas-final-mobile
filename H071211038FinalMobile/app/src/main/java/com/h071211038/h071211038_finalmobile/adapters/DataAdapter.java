package com.h071211038.h071211038_finalmobile.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.h071211038.h071211038_finalmobile.R;
import com.h071211038.h071211038_finalmobile.activities.DetailActivity;
import com.h071211038.h071211038_finalmobile.models.MovieResponse;
import com.h071211038.h071211038_finalmobile.models.TvShowsResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.MovieViewHolder> {

    private Context context;
    private List<MovieResponse> movieResponses;
    private List<TvShowsResponse> tvShowsResponses;
    private List<MovieResponse> originalMovieResponses;
    private List<TvShowsResponse> originalTvShowsResponses;
    private boolean isMovie;
    private String firstAirDate;

    public DataAdapter(Context context, boolean isMovie) {
        this.context = context;
        this.isMovie = isMovie;
    }

    public void setMovieResponses(List<MovieResponse> movieResponses) {
        this.originalMovieResponses = new ArrayList<>(movieResponses);
        this.movieResponses = movieResponses;
        notifyDataSetChanged();
    }

    public void setTvShowsResponses(List<TvShowsResponse> tvShowsResponses) {
        this.originalTvShowsResponses = new ArrayList<>(tvShowsResponses);
        this.tvShowsResponses= tvShowsResponses;
        notifyDataSetChanged();
    }

    public void restoreOriginalOrderMovie() {
        movieResponses.clear();
        movieResponses.addAll(originalMovieResponses);

        notifyDataSetChanged();
    }

    public void restoreOriginalOrderTvShows() {
        tvShowsResponses.clear();
        tvShowsResponses.addAll(originalTvShowsResponses);

        notifyDataSetChanged();
    }

    public void sortMovieByTitle(List<MovieResponse> movieResponses, String selectedSort) {
        if (selectedSort.equals("ASC")) {
            Collections.sort(movieResponses, (movie1, movie2) -> movie1.getTitle().compareToIgnoreCase(movie2.getTitle()));
        } else {
            Collections.sort(movieResponses, (movie1, movie2) -> movie2.getTitle().compareToIgnoreCase(movie1.getTitle()));
        }

        this.movieResponses = movieResponses;
        notifyDataSetChanged();
    }

    public void sortMovieByRate(List<MovieResponse> movieResponses, String selectedSort) {
        if (selectedSort.equals("ASC")) {
            Collections.sort(movieResponses, (movie1, movie2) -> Double.compare(movie1.getVoteAverage(), movie2.getVoteAverage()));
        } else {
            Collections.sort(movieResponses, (movie1, movie2) -> Double.compare(movie2.getVoteAverage(), movie1.getVoteAverage()));
        }

        this.movieResponses = movieResponses;
        notifyDataSetChanged();
    }

    public void sortMovieByReleaseDate(List<MovieResponse> movieResponses, String selectedSort) {
        if (selectedSort.equals("ASC")) {
            Collections.sort(movieResponses, (movie1, movie2) -> compareDates(movie1.getReleaseDate(), movie2.getReleaseDate()));
        } else {
            Collections.sort(movieResponses, (movie1, movie2) -> compareDates(movie2.getReleaseDate(), movie1.getReleaseDate()));
        }

        this.movieResponses = movieResponses;
        notifyDataSetChanged();
    }

    public void sortTvShowsByTitle(List<TvShowsResponse> tvShowsResponses, String selectedSort) {
        if (selectedSort.equals("ASC")) {
            Collections.sort(DataAdapter.this.tvShowsResponses, (tvShows1, tvShows2) -> tvShows1.getName().compareToIgnoreCase(tvShows2.getName()));
        } else {
            Collections.sort(DataAdapter.this.tvShowsResponses, (tvShows1, tvShows2) -> tvShows2.getName().compareToIgnoreCase(tvShows1.getName()));
        }

        this.tvShowsResponses = tvShowsResponses;
        notifyDataSetChanged();
    }

    public void sortTvShowsByRate(List<TvShowsResponse> tvShowsResponses, String selectedSort) {
        if (selectedSort.equals("ASC")) {
            Collections.sort(DataAdapter.this.tvShowsResponses, (tvShows1, tvShows2) -> Double.compare(tvShows1.getVoteAverage(), tvShows2.getVoteAverage()));
        } else {
            Collections.sort(DataAdapter.this.tvShowsResponses, (tvShows1, tvShows2) -> Double.compare(tvShows2.getVoteAverage(), tvShows1.getVoteAverage()));
        }

        this.tvShowsResponses = tvShowsResponses;
        notifyDataSetChanged();
    }

    public void sortTvShowsByReleaseDate(List<TvShowsResponse> tvShowsResponses, String selectedSort) {
        if (selectedSort.equals("ASC")) {
            Collections.sort(tvShowsResponses, (tvShows1, tvShows2) -> compareDates(tvShows1.getFirstAirDate(), tvShows2.getFirstAirDate()));
        } else {
            Collections.sort(tvShowsResponses, (tvShows1, tvShows2) -> compareDates(tvShows2.getFirstAirDate(), tvShows2.getFirstAirDate()));
        }

        this.tvShowsResponses = tvShowsResponses;
        notifyDataSetChanged();
    }

    public static int compareDates(String date1, String date2) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Date d1 = dateFormat.parse(date1);
            Date d2 = dateFormat.parse(date2);
            return d1.compareTo(d2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @NonNull
    @Override
    public DataAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataAdapter.MovieViewHolder holder, int position) {
        if (isMovie) {
            MovieResponse movieResponse = movieResponses.get(position);

            Glide.with(context)
                    .load("https://image.tmdb.org/t/p/original" + movieResponse.getPosterPath())
                    .into(holder.ivPoster);
            holder.tvTitle.setText(movieResponse.getTitle());
            holder.tvReleaseYear.setText(movieResponse.getReleaseDate().substring(0, 4));

            holder.itemView.setOnClickListener(view -> {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(DetailActivity.KEY_MOVIE, movieResponse);
                context.startActivity(intent);
            });
        } else {
            TvShowsResponse tvShowsResponse = tvShowsResponses.get(position);

            Glide.with(context)
                    .load("https://image.tmdb.org/t/p/original" + tvShowsResponse.getPosterPath())
                    .into(holder.ivPoster);
            holder.tvTitle.setText(tvShowsResponse.getName());

            if (tvShowsResponse.getFirstAirDate().length() > 3) {
                firstAirDate = tvShowsResponse.getFirstAirDate().substring(0, 4);
            } else {
                firstAirDate = "-";
            }

            holder.tvReleaseYear.setText(firstAirDate);

            holder.itemView.setOnClickListener(view -> {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(DetailActivity.KEY_TV_SHOW, tvShowsResponse);
                context.startActivity(intent);
            });
        }
    }


    @Override
    public int getItemCount() {
        if (movieResponses != null && isMovie) {
            return movieResponses.size();
        } else if (tvShowsResponses != null && !isMovie) {
            return tvShowsResponses.size();
        } else {
            return 0;
        }
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPoster;
        TextView tvTitle, tvReleaseYear;
        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);

            ivPoster = itemView.findViewById(R.id.poster);
            tvTitle = itemView.findViewById(R.id.title);
            tvReleaseYear = itemView.findViewById(R.id.release_year);
        }
    }
}
