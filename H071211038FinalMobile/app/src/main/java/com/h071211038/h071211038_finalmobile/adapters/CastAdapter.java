package com.h071211038.h071211038_finalmobile.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.h071211038.h071211038_finalmobile.R;
import com.h071211038.h071211038_finalmobile.models.MovieCastResponse;

import java.util.List;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.CastViewHolder> {
    private List<MovieCastResponse> castResponses;
    private Context context;

    public CastAdapter() {
    }

    public void setCastResponses(List<MovieCastResponse> castResponses) {
        this.castResponses = castResponses;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CastAdapter.CastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cast, parent, false);
        return new CastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CastAdapter.CastViewHolder holder, int position) {
        MovieCastResponse movieCastResponse = castResponses.get(position);

        Glide.with(context)
                .load("https://image.tmdb.org/t/p/original" + movieCastResponse.getProfilePath())
                .into(holder.ivProfile);
        holder.tvName.setText(movieCastResponse.getName());
        holder.tvCharacter.setText(movieCastResponse.getCharacter());
    }

    @Override
    public int getItemCount() {
        if (castResponses != null) {
            return castResponses.size();
        } else {
            return 0;
        }
    }

    public class CastViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProfile;
        TextView tvName, tvCharacter;
        public CastViewHolder(@NonNull View itemView) {
            super(itemView);

            ivProfile = itemView.findViewById(R.id.profile);
            tvName = itemView.findViewById(R.id.name);
            tvCharacter = itemView.findViewById(R.id.character);
        }
    }
}
