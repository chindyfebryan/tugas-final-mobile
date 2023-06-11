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
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;

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

    private DataAdapter dataAdapter;
    private Spinner sortSpinner;
    private RecyclerView recyclerView;
    private LinearLayout linearLayout;
    private ProgressBar progressBar;
    private int currentPage = 1;
    private boolean isFetchingData = false;

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

        sortSpinner = view.findViewById(R.id.sort_spinner);
        recyclerView = view.findViewById(R.id.movie_recycler_view);
        linearLayout = view.findViewById(R.id.linear_layout);

        dataAdapter = new DataAdapter(getActivity(), false);

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setAdapter(dataAdapter);

        progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        loadData();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (!recyclerView.canScrollVertically(1) && !isFetchingData) {
                    loadNextPage();
                }
            }
        });
    }

    private void loadNextPage() {
        isFetchingData = true;
        currentPage++;

        Call<TvShowListResponse> client = ApiConfig.getApiService().getTvShows(MoviesFragment.API_KEY, "en", currentPage);
        client.enqueue(new Callback<TvShowListResponse>() {
            @Override
            public void onResponse(Call<TvShowListResponse> call, Response<TvShowListResponse>
                    response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        List<TvShowsResponse> tvShowsResponses = response.body().getResults();
                        linearLayout.setVisibility(View.VISIBLE);

                        dataAdapter.addTvShowResponses(tvShowsResponses);
                        sortBy(tvShowsResponses);

                        recyclerView.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (response.body() != null) {
                        Log.e("MainActivity", "onFailure: " + response.message());
                    }
                }
                progressBar.setVisibility(View.GONE);
                isFetchingData = false;
            }
            @Override
            public void onFailure(Call<TvShowListResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new NoConnectionFragment())
                        .commit();
                Log.e("MainActivity", "onFailure: " + t.getMessage());
                isFetchingData = false;
            }
        });
    }

    private void loadData() {
        Call<TvShowListResponse> client = ApiConfig.getApiService().getTvShows(MoviesFragment.API_KEY, "en", 1);
        client.enqueue(new Callback<TvShowListResponse>() {
            @Override
            public void onResponse(Call<TvShowListResponse> call, Response<TvShowListResponse>
                    response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        List<TvShowsResponse> tvShowsResponses = response.body().getResults();
                        linearLayout.setVisibility(View.VISIBLE);

                        dataAdapter.setTvShowsResponses(tvShowsResponses);
                        sortBy(tvShowsResponses);

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
                progressBar.setVisibility(View.GONE);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new NoConnectionFragment())
                        .commit();
                Log.e("MainActivity", "onFailure: " + t.getMessage());
            }
        });
    }
    
    private void sortBy(List<TvShowsResponse> tvShowsResponses) {
        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch(i) {
                    case 0:
                        dataAdapter.restoreOriginalOrderTvShows();
                        break;
                    case 1:
                        dataAdapter.sortTvShowsByTitle(tvShowsResponses, "ASC");
                        break;
                    case 2:
                        dataAdapter.sortTvShowsByTitle(tvShowsResponses, "DESC");
                        break;
                    case 3:
                        dataAdapter.sortTvShowsByRate(tvShowsResponses, "ASC");
                        break;
                    case 4:
                        dataAdapter.sortTvShowsByRate(tvShowsResponses, "DESC");
                        break;
                    case 5:
                        dataAdapter.sortTvShowsByReleaseDate(tvShowsResponses, "ASC");
                        break;
                    case 6:
                        dataAdapter.sortTvShowsByReleaseDate(tvShowsResponses, "DESC");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}