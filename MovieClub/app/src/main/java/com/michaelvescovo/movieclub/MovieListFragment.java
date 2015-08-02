package com.michaelvescovo.movieclub;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

/**
 * Created by michael on 1/08/15.
 */
public class MovieListFragment extends ListFragment {
    private static final String DEBUG_TAG = "MovieListFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.movie_list, container, false);
    }

    @Override
    public void setListAdapter(ListAdapter adapter) {
        super.setListAdapter(adapter);


    }

    /*setListAdapter(new ArrayAdapter<String>(getActivity(),
    android.R.layout.simple_list_item_activated_1, fieldNotes));*/
}
