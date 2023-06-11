package com.h071211038.h071211038_finalmobile.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.h071211038.h071211038_finalmobile.R;
import com.h071211038.h071211038_finalmobile.adapters.FavoriteAdapter;
import com.h071211038.h071211038_finalmobile.db.FavoriteHelper;
import com.h071211038.h071211038_finalmobile.db.MappingHelper;
import com.h071211038.h071211038_finalmobile.entity.Favorite;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FavoritesFragment extends Fragment {
    private LoadFavoritesAsync loadFavoritesAsync;
    private LinearLayout linearLayout;
    private TextView textView;
    private FavoriteAdapter favoriteAdapter;
    private Activity activity;
    private SharedPreferences preferences;
    private int selectedSort;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("selectedSort", selectedSort);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null) {
            selectedSort = savedInstanceState.getInt("selectedSort", 0);
        }

        activity = getActivity();
        textView = view.findViewById(R.id.text_view);
        linearLayout = view.findViewById(R.id.linear_layout);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        Spinner sortSpinner = view.findViewById(R.id.sort_spinner);
        ProgressBar progressBar = view.findViewById(R.id.progress_bar);
        favoriteAdapter = new FavoriteAdapter(activity);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(favoriteAdapter);

        loadFavoritesAsync = new LoadFavoritesAsync(activity, favorites -> {
            if (favorites.size() > 0) {
                progressBar.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);
                textView.setVisibility(View.GONE);
                favoriteAdapter.setFavorites(favorites);
                sortSpinner.setSelection(selectedSort);

                sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        switch(i) {
                            case 0:
                                favoriteAdapter.restoreOriginalOrderFavorite();
                                break;
                            case 1:
                                favoriteAdapter.sortFavoriteByTitle(favorites, "ASC");
                                break;
                            case 2:
                                favoriteAdapter.sortFavoriteByTitle(favorites, "DESC");
                                break;
                            case 3:
                                favoriteAdapter.sortFavoriteByRate(favorites, "ASC");
                                break;
                            case 4:
                                favoriteAdapter.sortFavoriteByRate(favorites, "DESC");
                                break;
                            case 5:
                                favoriteAdapter.sortFavoriteByReleaseDate(favorites, "ASC");
                                break;
                            case 6:
                                favoriteAdapter.sortFavoriteByReleaseDate(favorites, "DESC");
                                break;
                        }

                        preferences = getActivity().getSharedPreferences("SortPreferences", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        selectedSort = i;
                        editor.putInt("selectedSort", i);
                        editor.apply();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

            } else {
                progressBar.setVisibility(View.GONE);
                linearLayout.setVisibility(View.GONE);
                textView.setVisibility(View.VISIBLE);
            }
        });

        loadFavoritesAsync.execute();
    }

    private static class LoadFavoritesAsync {

        private final WeakReference<Context> weakContext;
        private final LoadFavoritesCallback callback;
        private LoadFavoritesAsync(Context context, LoadFavoritesCallback callback) {
            weakContext = new WeakReference<>(context);
            this.callback = callback;
        }
        void execute() {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());

            executor.execute(() -> {
                Context context = weakContext.get();
                if (context != null) {
                    FavoriteHelper favoriteHelper = FavoriteHelper.getInstance(context);
                    favoriteHelper.open();

                    try {
                        Cursor favoritesCursor = favoriteHelper.queryAll();
                        ArrayList<Favorite> favorites = MappingHelper.mapCursorToArrayList(favoritesCursor);
                        handler.post(() -> callback.postExecute(favorites));
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        favoriteHelper.close();
                    }
                }
            });
        }
    }

    interface LoadFavoritesCallback {
        void postExecute(ArrayList<Favorite> favorites);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadFavoritesAsync.execute();
    }
}