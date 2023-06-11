package com.h071211038.h071211038_finalmobile.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.h071211038.h071211038_finalmobile.R;
import com.h071211038.h071211038_finalmobile.fragments.FavoritesFragment;
import com.h071211038.h071211038_finalmobile.fragments.MoviesFragment;
import com.h071211038.h071211038_finalmobile.fragments.TvShowsFragment;

public class MainActivity extends AppCompatActivity {

    private TextView headerTextView;
    private LinearLayout activeButton;
    private LinearLayout btnHome, btnTvShows, btnFavorite;
    private MoviesFragment moviesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnHome = findViewById(R.id.movies_button);
        btnTvShows = findViewById(R.id.tv_shows_button);
        btnFavorite = findViewById(R.id.favorites_button);
        headerTextView = findViewById(R.id.header_text_view);

        FragmentManager fragmentManager = getSupportFragmentManager();

        moviesFragment = new MoviesFragment();

        fragmentManager.beginTransaction()
                .add(R.id.fragment_container, moviesFragment, MoviesFragment.class.getSimpleName())
                .commit();

        setActiveButton(btnHome);

        btnHome.setOnClickListener(view -> showFragment(new MoviesFragment(), "Movies", btnHome));

        btnTvShows.setOnClickListener(view -> showFragment(new TvShowsFragment(), "Tv Shows", btnTvShows));

        btnFavorite.setOnClickListener(view -> showFragment(new FavoritesFragment(), "Favorites", btnFavorite));

    }

    private void setActiveButton(LinearLayout button) {
        // Ubah latar belakang button yang aktif
        if (activeButton != null) {
            activeButton.setBackgroundColor(Color.TRANSPARENT);
        }
        button.setBackgroundColor(getResources().getColor(R.color.light_green));

        activeButton = button;
    }
    public void showFragment(Fragment fragment, String title, LinearLayout button) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
        headerTextView.setText(title);
        setActiveButton(button);
    }

}