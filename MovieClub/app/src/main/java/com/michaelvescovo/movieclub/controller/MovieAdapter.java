package com.michaelvescovo.movieclub.controller;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.michaelvescovo.movieclub.model.Movie;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by michael on 20/08/15.
 * blah blah
 */

public class MovieAdapter extends ArrayAdapter {
    private static final String DEBUG_TAG = "MovieAdapter";

    //ViewGroup viewGroup;

    public MovieAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        Log.i(DEBUG_TAG, "I happened 5");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout viewGroup = new LinearLayout(getContext());
        TextView textView;
      //  if (convertView == null) {
            textView = new TextView(getContext());
            textView.setText(((Movie) getItem(position)).testMethod());
      //  } else {
         //   textView = (TextView) convertView;
        //}
        viewGroup.addView(textView);


        TextView textView2 = new TextView(getContext());
        textView2.setText(((Movie) getItem(position)).toString());

        viewGroup.addView(textView2);

        return viewGroup;
        //return super.getView(position, convertView, parent);
    }

/*    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }*/
}
