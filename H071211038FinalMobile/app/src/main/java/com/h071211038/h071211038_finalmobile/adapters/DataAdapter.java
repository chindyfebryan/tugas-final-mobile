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

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.MovieViewHolder> {

    private Context context;
    private List<MovieResponse> movieResponses;
    private List<TvShowsResponse> tvShowsResponses;
    private boolean isMovie;

    public DataAdapter(Context context, boolean isMovie) {
        this.context = context;
        this.isMovie = isMovie;
    }

    public void setMovieResponses(List<MovieResponse> movieResponses) {
        this.movieResponses = movieResponses;
        notifyDataSetChanged();
    }

    public void setTvShowsResponses(List<TvShowsResponse> tvShowsResponses) {
        this.tvShowsResponses= tvShowsResponses;
        notifyDataSetChanged();
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
            holder.tvReleaseYear.setText(tvShowsResponse.getFirstAirDate().substring(0, 4));

            holder.itemView.setOnClickListener(view -> {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(DetailActivity.KEY_TV_SHOW, tvShowsResponse);
                intent.putExtra(DetailActivity.KEY_IS_MOVIE, this.isMovie);
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
