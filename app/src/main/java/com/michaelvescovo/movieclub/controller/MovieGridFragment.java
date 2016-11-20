package com.michaelvescovo.movieclub.controller;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.michaelvescovo.movieclub.R;

/**
 * Created by michael on 1/08/15.
 * Fragment do display and control the movie list view
 */
public class MovieGridFragment extends ListFragment {
    private static final String DEBUG_TAG = "MovieGridFragment";
    OnMovieSelectedListener mCallback;
    Boolean mDualPane;
    int mCurCheckPosition = 0;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

/*        // This makes sure that the container activity has implemented the callback interface.
        // if not it throws an exception.
        try {
            mCallback = (OnMovieSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnMovieSelectedListener");
        }*/
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //String[] movieList = getResources().getStringArray(R.array.movie_names_array);
        MovieAdapter movieAdapter = new MovieAdapter(getActivity(), android.R.layout.simple_list_item_activated_1, DataHolder.getDataHolder().getMovieArrayList());

        // ListFragment has a built in ListView. We must set the adapter to the ListFragment and not set it directly to the ListView.
        setListAdapter(movieAdapter);

        // check to see if there is a frame for the movie details fragment
        View movieDetailsview = getActivity().findViewById(R.id.fragment_container_movie_detail);
        mDualPane = movieDetailsview != null && movieDetailsview.getVisibility() == View.VISIBLE;

        if (savedInstanceState != null) {
            // restore state for checked position.
            mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
        }

        if (mDualPane) {
            // make it so the selected item is highlighted
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            // update to the current position
            displayMovie(mCurCheckPosition);

        }
    }

    @Override
    public void onStart() {
        super.onStart();

        getListView().setItemChecked(mCurCheckPosition, true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("curChoice", mCurCheckPosition);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        displayMovie(position);
    }

    // Container activity must implement this interface
    public interface OnMovieSelectedListener {
        void onMovieSelected(String movie);
    }

    // this method either displays the movie details in the current UI if in dual pane
    // or creates a new activity to show them if in single pane
    void displayMovie(int index) {
        mCurCheckPosition = index;

        if (mDualPane) {
            // highlight the selected item
            // *** seems to be a bug ***
            // ***this method isn't running successfully when rotating back from portrait mode ***
            // *** workaround is to call this method from onStart ***
            getListView().setItemChecked(index, true);

            // check what fragment is currently shown and replace only if it's the wrong one (not current index or empty)
            MovieDetailFragment movieDetailFragment = (MovieDetailFragment)
                    getFragmentManager().findFragmentById(R.id.fragment_container_movie_detail);
            if (movieDetailFragment == null || movieDetailFragment.getShownIndex() != index) {
                movieDetailFragment = MovieDetailFragment.newInstance(index);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container_movie_detail, movieDetailFragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }

        } else {
            Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
            intent.putExtra("index", index);
            startActivity(intent);
        }
    }
}