package com.michaelvescovo.movieclub.controller;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.michaelvescovo.movieclub.R;

/**
 * Created by michael on 1/08/15.
 */
public class MovieDetailFragment extends Fragment {
    private static final String DEBUG_TAG = "MovieDetailFragment";
    Bundle args;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movie_detail, container, false);
        args = getArguments();

        if (args != null) {
            final String KEY_NAME = getResources().getString(R.string.movie_title);
            String movieTitle;

            movieTitle = args.getString(KEY_NAME);
            TextView movieText = (TextView) view.findViewById(R.id.movie_name_text);
            if (movieText != null) {
                movieText.setText(movieTitle);
            }
        }

        return view;
    }

    public void displayMovie(String movieName) {
        assert (getView() != null);

        TextView movieText = (TextView) getView().findViewById(R.id.movie_name_text);
        if (movieText != null) {
            movieText.setText(movieName);
        }
    }
}
