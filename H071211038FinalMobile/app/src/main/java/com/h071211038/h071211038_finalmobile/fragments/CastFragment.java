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
import android.widget.TextView;

import com.h071211038.h071211038_finalmobile.R;
import com.h071211038.h071211038_finalmobile.adapters.CastAdapter;
import com.h071211038.h071211038_finalmobile.adapters.DataAdapter;
import com.h071211038.h071211038_finalmobile.models.MovieCastListResponse;
import com.h071211038.h071211038_finalmobile.models.MovieCastResponse;
import com.h071211038.h071211038_finalmobile.networks.ApiConfig;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CastFragment extends Fragment {
    private int dataId;
    private String data;
    public CastFragment() {
        // Required empty public constructor
    }

    public CastFragment(int dataId, String data) {
        this.dataId = dataId;
        this.data = data;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cast, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.cast_recycler_view);
        ProgressBar progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        CastAdapter castAdapter = new CastAdapter();

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setAdapter(castAdapter);

        Call<MovieCastListResponse> client = ApiConfig.getApiService().getMovieCasts(data, dataId, MoviesFragment.API_KEY, "en-US");
        client.enqueue(new Callback<MovieCastListResponse>() {
            @Override
            public void onResponse(Call<MovieCastListResponse> call, Response<MovieCastListResponse>
                    response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        List<MovieCastResponse> movieCastResponses= response.body().getCasts();
                        castAdapter.setCastResponses(movieCastResponses);
                    }
                } else {
                    if (response.body() != null) {
                        Log.e("MainActivity", "onFailure: " + response.message());
                    }
                }

            }
            @Override
            public void onFailure(Call<MovieCastListResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new NoConnectionFragment())
                        .commit();
                Log.e("MainActivity", "onFailure: " + t.getMessage());
            }
        });

    }
}