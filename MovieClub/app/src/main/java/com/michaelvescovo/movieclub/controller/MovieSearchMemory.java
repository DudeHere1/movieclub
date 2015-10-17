package com.michaelvescovo.movieclub.controller;

import android.util.Log;

import com.michaelvescovo.movieclub.model.Memory;
import com.michaelvescovo.movieclub.model.MovieImpl;

import java.util.ArrayList;

/**
 * Michael Vescovo
 * s3459317
 */
public class MovieSearchMemory extends MovieSearch {
    private static final String DEBUG_TAG = "MovieSearchMemory";
    private ArrayList<MovieImpl> mSearchResults = new ArrayList<>();




    // A searchTitlePreview must find 10 movies, or return null to indicate a db search is required.
    // Since memory is limited to 10 movies it can break out if one of them is not matched.
    public ArrayList searchTitlePreview(String title) {
        Log.i(DEBUG_TAG, "attempting search title preview in memory");
        mSearchResults.clear();
        // search memory
        for (MovieImpl movie : Memory.getInstance().getMovieArrayList()) {
            if (movie.getTitle().toLowerCase().contains(title.toLowerCase())) {
                mSearchResults.add(movie);
            } else {
                return null;
            }
        }
        return mSearchResults;
    }

    // When searching for a movie detail return one movie rather than a list.
    public MovieImpl searchMovieDetailbyImdb(String imdbId) {
        Log.i(DEBUG_TAG, "memory size:" + Memory.getInstance().getMovieArrayList().size());
        Log.i(DEBUG_TAG, "attempting detail search by imdbId in memory");
        for (MovieImpl movie: Memory.getInstance().getMovieArrayList()) {
            Log.i(DEBUG_TAG, "found imdb:" + movie.getImdbId());
            if (movie.getImdbId().toLowerCase().contentEquals(imdbId.toLowerCase())) {
                Log.i(DEBUG_TAG, "found imdb match:" + movie.getImdbId());
                // Found movie.
                return movie;
            }
        }
        // didn't find movie
        return null;
    }
}