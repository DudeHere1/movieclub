package com.michaelvescovo.movieclub.controller;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.michaelvescovo.movieclub.R;
import com.michaelvescovo.movieclub.model.Movie;

import java.util.ArrayList;

public class ViewMoviesActivity extends AppCompatActivity implements MovieListFragment.OnMovieSelectedListener {
    private static final String DEBUG_TAG = "ViewMoviesActivity";
    private ArrayList<Movie> movies = new ArrayList<Movie>();
    private String[] movieTitleList;
    private Movie selectedMovie = null;

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() != 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_movies_layout);

        // trying to add all the movies into memory
        movieTitleList = getResources().getStringArray(R.array.movienames_array);

        for (int i = 0; i < movieTitleList.length; i++) {
            movies.add(new Movie(movieTitleList[i]));
        }

        // Check that the activity is using the layout version with the fragment_container_1 FrameLayout
        if (findViewById(R.id.fragment_container_movie_list) != null) {
            // if restoring from a previous state simply return to avoid overlapping fragments
            if (savedInstanceState != null) {
                return;
            }

            // create a new fragment to be placed in the activity layout
            MovieListFragment movieListFragment = new MovieListFragment();

            // in case this activity was started with special instructions from an intent,
            // pass the intent's extras to the fragment as arguments
            movieListFragment.setArguments(getIntent().getExtras());

            // add the fragment to fragment_container_1 framelayout
            getFragmentManager().beginTransaction().add(R.id.fragment_container_movie_list, movieListFragment).commit();
        }

        // Check that the activity is using the layout version with the fragment_container_2 FrameLayout
        if (findViewById(R.id.fragment_container_movie_detail) != null) {
            // if restoring from a previous state simply return to avoid overlapping fragments
            if (savedInstanceState != null) {
                return;
            }

            // create a new fragment to be placed in the activity layout
            MovieDetailFragment movieDetailFragment = new MovieDetailFragment();

            // in case this activity was started with special instructions from an intent,
            // pass the intent's extras to the fragment as arguments
            movieDetailFragment.setArguments(getIntent().getExtras());

            // add the fragment to fragment_container_1 framelayout
            getFragmentManager().beginTransaction().add(R.id.fragment_container_movie_detail, movieDetailFragment).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movie_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMovieSelected(String movieName) {
        for (int i = 0; i < movieTitleList.length; i++) {
            if (movies.get(i).getTitle().toString() == movieName) {
                selectedMovie = movies.get(i);
                Log.i(DEBUG_TAG, movieName);
                break;
            }
        }

        if (findViewById(R.id.fragment_container_movie_detail) != null) {
            // if this fragment is available then we're in the two-pane layout
            MovieDetailFragment movieDetailFragment = (MovieDetailFragment) getFragmentManager().findFragmentById(R.id.fragment_container_movie_detail);
            movieDetailFragment.displayMovie(movieName);


        } else {
            // we're in the one-pane layout and have to swap fragments
            MovieDetailFragment newFragment = new MovieDetailFragment();
            Bundle args = new Bundle();
            final String KEY_NAME = getResources().getString(R.string.movie_title);

            args.putString(KEY_NAME, movieName);
            newFragment.setArguments(args);

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            // replace whatever is in the fragment container view with this fragment and
            // add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.fragment_container_movie_list, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}
