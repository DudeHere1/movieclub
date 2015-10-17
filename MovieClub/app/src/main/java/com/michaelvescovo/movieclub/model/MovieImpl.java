package com.michaelvescovo.movieclub.model;

import android.location.Location;
import android.media.Image;
import android.util.Log;

import com.michaelvescovo.movieclub.R;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by michael on 9/08/15.
 * MovieImpl object class
 */
public class MovieImpl implements Serializable, MovieInterface {
    private static final String DEBUG_TAG = "MovieImpl";

    // not editable by user
    private String id;
    private String title;
    private String year;
    private String year_temp;
    private String shortPlot;
    private String fullPlot;
    private Image poster;

    // fields entered by user
    private float rating = 0;
    private Date partyDate;
    private String date; // TODO temp field until I do the proper date
    private String venue = "No venue set";
    private Location location;

    private String[] invitees;
    private String invitees_tmp; // TODO temp field

    public MovieImpl(String title, String year, String shortPlot, String fullPlot, String id, String date,
                     String venue, String invitees) {
        this.title = title;
        this.year_temp = year;
        this.shortPlot = shortPlot;
        this.fullPlot = fullPlot;
        this.id = id;
        this.date = date;
        this.venue = venue;
        this.invitees_tmp = invitees;

    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public void setRating(float rating) {
        this.rating = rating;
    }

    @Override
    public float getRating() {
        return this.rating;
    }

    @Override
    public String getVenue() {
        return this.venue;
    }

    @Override
    public String toString() {
        String string = this.getTitle() + " " + this.getRating() + " " + this.getVenue();
        return string;
        //return super.toString();
    }

    public String testMethod() {
        return "test blah blah";
    }
}
