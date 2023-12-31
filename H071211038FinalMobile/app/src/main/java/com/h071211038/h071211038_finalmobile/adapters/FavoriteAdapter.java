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
import com.h071211038.h071211038_finalmobile.entity.Favorite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {
    private ArrayList<Favorite> favorites;
    private ArrayList<Favorite> originalFavorites;
    private Context context;

    public FavoriteAdapter(Context context) {
        this.context = context;
    }
    public void setFavorites(ArrayList<Favorite> favorites) {
        this.originalFavorites = new ArrayList<>(favorites);
        this.favorites = favorites;
        notifyDataSetChanged();
    }

    public void restoreOriginalOrderFavorite() {
        favorites.clear();
        favorites.addAll(originalFavorites);

        notifyDataSetChanged();
    }

    public void sortFavoriteByTitle(ArrayList<Favorite> favorites, String selectedSort) {
        if (selectedSort.equals("ASC")) {
            Collections.sort(favorites, (favorite1, favorite2) -> favorite1.getTitle().compareToIgnoreCase(favorite2.getTitle()));
        } else {
            Collections.sort(favorites, (favorite1, favorite2) -> favorite2.getTitle().compareToIgnoreCase(favorite1.getTitle()));
        }

        this.favorites = favorites;
        notifyDataSetChanged();
    }

    public void sortFavoriteByRate(ArrayList<Favorite> favorites, String selectedSort) {
        if (selectedSort.equals("ASC")) {
            Collections.sort(favorites, (favorite1, favorite2) -> Double.compare(favorite1.getVoteAverage(), favorite2.getVoteAverage()));
        } else {
            Collections.sort(favorites, (favorite1, favorite2) -> Double.compare(favorite2.getVoteAverage(), favorite1.getVoteAverage()));
        }

        this.favorites = favorites;
        notifyDataSetChanged();
    }

    public void sortFavoriteByReleaseDate(ArrayList<Favorite> favorites, String selectedSort) {
        if (selectedSort.equals("ASC")) {
            Collections.sort(favorites, (favorite1, favorite2) -> DataAdapter.compareDates(favorite1.getReleaseDate(), favorite2.getReleaseDate()));
        } else {
            Collections.sort(favorites, (favorite1, favorite2) -> DataAdapter.compareDates(favorite2.getReleaseDate(), favorite1.getReleaseDate()));
        }

        this.favorites = favorites;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoriteAdapter.FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite, parent, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.FavoriteViewHolder holder, int position) {
        Favorite favorite = favorites.get(position);

        Glide.with(context)
                .load("https://image.tmdb.org/t/p/original" + favorite.getPosterPath())
                .into(holder.ivPoster);
        holder.tvTitle.setText(favorite.getTitle());
        String releaseYear = favorite.getReleaseDate().length() > 3 ? favorite.getReleaseDate().substring(0,4) : "-";
        holder.tvReleaseYear.setText(releaseYear);
        holder.ivIcon.setImageResource(favorite.getIcon());

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("favorite", favorite);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return favorites.size();
    }

    public class FavoriteViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvReleaseYear;
        ImageView ivPoster, ivIcon;
        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.title);
            tvReleaseYear = itemView.findViewById(R.id.release_year);
            ivPoster = itemView.findViewById(R.id.poster);
            ivIcon = itemView.findViewById(R.id.icon);
        }
    }
}
