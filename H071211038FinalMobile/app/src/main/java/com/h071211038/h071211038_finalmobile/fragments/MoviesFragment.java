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
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.h071211038.h071211038_finalmobile.R;
import com.h071211038.h071211038_finalmobile.adapters.DataAdapter;
import com.h071211038.h071211038_finalmobile.models.MovieResponse;
import com.h071211038.h071211038_finalmobile.models.MovieListResponse;
import com.h071211038.h071211038_finalmobile.networks.ApiConfig;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesFragment extends Fragment {

    public static final String API_KEY = "2502e830c9d2ad69aa6b78ea4122f7ef";
    private int currentPage = 1;
    private boolean isFetchingData = false;
    private ProgressBar progressBar;
    private Spinner sortSpinner;
    private LinearLayout linearLayout;
    private DataAdapter dataAdapter;
    private RecyclerView recyclerView;
    private List<MovieResponse> movieResponseList;
    public MoviesFragment() {
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
        progressBar = view.findViewById(R.id.progress_bar);
        recyclerView = view.findViewById(R.id.movie_recycler_view);
        linearLayout = view.findViewById(R.id.linear_layout);

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

    private void loadData() {
        progressBar.setVisibility(View.VISIBLE);

        Call<MovieListResponse> client = ApiConfig.getApiService().getMovies(API_KEY, "en", 1);
        client.enqueue(new Callback<MovieListResponse>() {
            @Override
            public void onResponse(Call<MovieListResponse> call, Response<MovieListResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        movieResponseList = response.body().getResults();
                        dataAdapter = new DataAdapter(getActivity(), true);
                        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                        recyclerView.setAdapter(dataAdapter);
                        linearLayout.setVisibility(View.VISIBLE);

                        dataAdapter.setMovieResponses(movieResponseList);

                        sortBy(movieResponseList);
                    }
                } else {
                    if (response.body() != null) {
                        Log.e("MainActivity", "onFailure: " + response.message());
                    }
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<MovieListResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new NoConnectionFragment())
                        .commit();
                Log.e("MainActivity", "onFailure: " + t.getMessage());
            }
        });
    }

    private void loadNextPage() {
        isFetchingData = true;
        currentPage++;

        progressBar.setVisibility(View.VISIBLE);

        Call<MovieListResponse> client = ApiConfig.getApiService().getMovies(API_KEY, "en", currentPage);
        client.enqueue(new Callback<MovieListResponse>() {
            @Override
            public void onResponse(Call<MovieListResponse> call, Response<MovieListResponse>
                    response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        linearLayout.setVisibility(View.VISIBLE);
                        movieResponseList = response.body().getResults();

                        dataAdapter.addMovieResponses(movieResponseList);

                        sortBy(movieResponseList);

                        recyclerView.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                    }
                } else {
                    if (response.body() != null) {
                        progressBar.setVisibility(View.GONE);
                        Log.e("MainActivity", "onFailure: " + response.message());
                    }
                }
                isFetchingData = false;
            }
            @Override
            public void onFailure(Call<MovieListResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new NoConnectionFragment())
                        .commit();
                Log.e("MainActivity", "onFailure: " + t.getMessage());
                isFetchingData = false;
            }
        });
    }

    private void sortBy(List<MovieResponse> movieResponseList){
        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch(i) {
                    case 0:
                        dataAdapter.restoreOriginalOrderMovie();
                        break;
                    case 1:
                        dataAdapter.sortMovieByTitle(movieResponseList, "ASC");
                        break;
                    case 2:
                        dataAdapter.sortMovieByTitle(movieResponseList, "DESC");
                        break;
                    case 3:
                        dataAdapter.sortMovieByRate(movieResponseList, "ASC");
                        break;
                    case 4:
                        dataAdapter.sortMovieByRate(movieResponseList, "DESC");
                        break;
                    case 5:
                        dataAdapter.sortMovieByReleaseDate(movieResponseList, "ASC");
                        break;
                    case 6:
                        dataAdapter.sortMovieByReleaseDate(movieResponseList, "DESC");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

}