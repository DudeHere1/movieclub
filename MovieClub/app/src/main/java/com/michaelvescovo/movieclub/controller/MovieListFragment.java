package com.michaelvescovo.movieclub.controller;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.michaelvescovo.movieclub.R;

/**
 * Created by michael on 1/08/15.
 */
public class MovieListFragment extends ListFragment {
    private static final String DEBUG_TAG = "MovieListFragment";
    OnMovieSelectedListener mCallback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String[] movieList = getResources().getStringArray(R.array.movienames_array);
        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_activated_1, movieList);

        this.setListAdapter(arrayAdapter);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.movie_list, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented the callback interface.
        // if not it throws an exception.
        try {
            mCallback = (OnMovieSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnMovieSelectedListener");
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // send the event to the host activity
        mCallback.onMovieSelected((String) l.getItemAtPosition(position));
    }

    // Container activity must implement this interface
    public interface OnMovieSelectedListener {
        public void onMovieSelected(String movieName);
    }
}