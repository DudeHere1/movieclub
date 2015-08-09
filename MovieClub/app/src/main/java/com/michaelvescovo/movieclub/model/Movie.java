package com.michaelvescovo.movieclub.model;

import android.location.Location;
import android.media.Image;

import java.util.Date;

/**
 * Created by michael on 9/08/15.
 */
public class Movie {
    // not editable by user
    private String title;
    private int year;
    private String shortPlot;
    private String fullPlot;
    private Image poster;
    private String id;

    // fields entered by user
    private int rating;
    private Date partyDate;
    private String venue;
    private Location location;
    private String[] invitees;

    public Movie(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }
}
