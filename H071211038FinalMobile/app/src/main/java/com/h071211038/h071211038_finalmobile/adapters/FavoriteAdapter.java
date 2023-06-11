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

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {
    private ArrayList<Favorite> favorites;
    private Context context;

    public FavoriteAdapter(Context context) {
        this.context = context;
    }
    public void setFavorites(ArrayList<Favorite> favorites) {
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
