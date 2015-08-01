package com.michaelvescovo.movieclub;

import android.app.FragmentManager;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by michael on 1/08/15.
 */
public class MovieListFragment extends ListFragment implements FragmentManager.OnBackStackChangedListener {

    private static final String DEBUG_TAG = "MovieListFragment";
    int mCurPosition = -1;
    boolean mShowTwoFragments;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        String[] movieNames = getResources().getStringArray(R.array.movienames_array);
        setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_activated_1, movieNames));
        //View detailsFrame = getActivity().findViewById(R.id.fieldentry);
    }

    @Override
    public void onBackStackChanged() {

    }
}
