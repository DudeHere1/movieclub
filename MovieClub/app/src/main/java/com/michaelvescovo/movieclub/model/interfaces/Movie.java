package com.michaelvescovo.movieclub.model.interfaces;

/**
 * Created by michael on 20/08/15.
 *
 * The main interface for the movie club model.
 */
public interface Movie {
    String getTitle();

    void setRating(float rating);

    float getRating();

    String getVenue();
}
