package com.h071211038.h071211038_finalmobile.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.h071211038.h071211038_finalmobile.R;
import com.h071211038.h071211038_finalmobile.adapters.DataAdapter;
import com.h071211038.h071211038_finalmobile.models.TvShowsResponse;
import com.h071211038.h071211038_finalmobile.models.TvShowListResponse;
import com.h071211038.h071211038_finalmobile.networks.ApiConfig;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvShowsFragment extends Fragment {

    public static final String API_KEY = "2502e830c9d2ad69aa6b78ea4122f7ef";
    public TvShowsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.movie_recycler_view);

        DataAdapter dataAdapter = new DataAdapter(getActivity(), false);

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setAdapter(dataAdapter);

        ProgressBar progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        Call<TvShowListResponse> client = ApiConfig.getApiService().getTvShows(API_KEY, "en", 1);
        client.enqueue(new Callback<TvShowListResponse>() {
            @Override
            public void onResponse(Call<TvShowListResponse> call, Response<TvShowListResponse>
                    response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        List<TvShowsResponse> tvShowsResponses = response.body().getResults();

                        dataAdapter.setTvShowsResponses(tvShowsResponses);

                        recyclerView.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                    }
                } else {
                    if (response.body() != null) {
                        Log.e("MainActivity", "onFailure: " + response.message());
                    }
                }
            }
            @Override
            public void onFailure(Call<TvShowListResponse> call, Throwable t) {
                Log.e("MainActivity", "onFailure: " + t.getMessage());
            }
        });
    }
}