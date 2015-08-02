package com.michaelvescovo.movieclub;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by michael on 1/08/15.
 */
public class MovieDetailFragment extends Fragment {
    private static final String DEBUG_TAG = "MovieDetailFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.movie_detail, container, false);
    }
}
