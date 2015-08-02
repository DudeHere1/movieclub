package com.michaelvescovo.movieclub;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MovieClubActivity extends AppCompatActivity {
    private static final String DEBUG_TAG = "MovieClubActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_club_layout);

        // Check that the activity is using the layout version with the fragment_container_1 FrameLayout
        if (findViewById(R.id.fragment_container_1) != null) {
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
            getFragmentManager().beginTransaction().add(R.id.fragment_container_1, movieListFragment).commit();
        }

        // Check that the activity is using the layout version with the fragment_container_2 FrameLayout
        if (findViewById(R.id.fragment_container_2) != null) {
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
            getFragmentManager().beginTransaction().add(R.id.fragment_container_2, movieDetailFragment).commit();
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
}
