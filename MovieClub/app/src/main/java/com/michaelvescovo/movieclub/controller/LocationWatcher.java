package com.michaelvescovo.movieclub.controller;

import android.text.Editable;
import android.text.TextWatcher;

import com.michaelvescovo.movieclub.model.DataHolder;

/**
 * Created by michael on 28/08/15.
 * blah blah
 */

public class LocationWatcher implements TextWatcher {
    int mPosition;

    public LocationWatcher(int position) {
        mPosition = position;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        DataHolder.getDataHolder().getMovieArrayList().get(mPosition).setVenue(s.toString());
    }
}
