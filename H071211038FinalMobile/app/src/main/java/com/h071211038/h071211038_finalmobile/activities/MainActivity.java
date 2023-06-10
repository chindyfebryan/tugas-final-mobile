package com.h071211038.h071211038_finalmobile.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.h071211038.h071211038_finalmobile.R;
import com.h071211038.h071211038_finalmobile.fragments.FavoritesFragment;
import com.h071211038.h071211038_finalmobile.fragments.MoviesFragment;
import com.h071211038.h071211038_finalmobile.fragments.TvShowsFragment;

public class MainActivity extends AppCompatActivity {

    private TextView headerTextView;
    private MoviesFragment moviesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout btnHome = findViewById(R.id.movies_button);
        LinearLayout btnTvShows = findViewById(R.id.tv_shows_button);
        LinearLayout btnFavorite = findViewById(R.id.favorites_button);
        headerTextView = findViewById(R.id.header_text_view);

        FragmentManager fragmentManager = getSupportFragmentManager();

        moviesFragment = new MoviesFragment();

        fragmentManager.beginTransaction()
                .add(R.id.fragment_container, moviesFragment, MoviesFragment.class.getSimpleName())
                .commit();

        btnHome.setOnClickListener(view -> showFragment(new MoviesFragment(), "Movies"));

        btnTvShows.setOnClickListener(view -> showFragment(new TvShowsFragment(), "Tv Shows"));

        btnFavorite.setOnClickListener(view -> showFragment(new FavoritesFragment(), "Favorites"));

    }

    public void showFragment(Fragment fragment, String title) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
        headerTextView.setText(title);
    }
}