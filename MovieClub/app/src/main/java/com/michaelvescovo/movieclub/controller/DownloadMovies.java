package com.michaelvescovo.movieclub.controller;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.AsyncQueryHandler;
import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.michaelvescovo.movieclub.model.Memory;
import com.michaelvescovo.movieclub.model.MovieClubConstants;
import com.michaelvescovo.movieclub.model.MovieClubDbContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Michael Vescovo
 * s3459317
 */
public class DownloadMovies {
    private static final String DEBUG_TAG = "DownloadMovies";
    private Fragment mFragment;
    private LoaderManager.LoaderCallbacks mLoaderCallbacks;
    private final RequestQueue mQueue;

    public DownloadMovies(Fragment fragment, LoaderManager.LoaderCallbacks loaderCallbacks) {
        mFragment = fragment;
        mLoaderCallbacks = loaderCallbacks;
        mQueue = Volley.newRequestQueue(mFragment.getContext());
    }


    // Do a movie search on OMDB and save the imdbID's to memory
    public void downloadOmdbMovieList() {

    }


    // Get the jsonarray and then pass each imdbid to an imdb lookup
    public void byTitleSearch(String title) {
        Log.i(DEBUG_TAG, "trying to download movie array from OMDB.");

        String url = "http://www.omdbapi.com/?s=" + title + "&type=movie&r=json";
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray movieSearch;

                        try {
                            movieSearch = response.getJSONArray("Search");

                            for (int i = 0; i < movieSearch.length(); i++)
                                byImdbId(movieSearch.getJSONObject(i).getString("imdbID"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(DEBUG_TAG, "Volley object request error: " + error.toString());
                    }
                });
        mQueue.add(jsObjRequest);
    }

    public void byImdbId(String imdbId) {
        Log.i(DEBUG_TAG, "trying to download movie from OMDB.");

        final String url = "http://www.omdbapi.com/?i=" + imdbId + "&plot=short&r=json&tomatoes=true";
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        final String imdbId;
                        String mTitle;
                        String mYear;
                        String mShortPlot;
                        String mPoster;
                        float mRating = 0;

                        try {
                            imdbId = response.getString("imdbID");
                            mTitle = response.getString("Title");
                            mYear = response.getString("Year");
                            mShortPlot = response.getString("Plot");
                            mPoster = response.getString("Poster");

                            try {
                                mRating = (Float.parseFloat(response.getString("tomatoRating"))) / 2;
                            } catch (NumberFormatException e) {
                                Log.e(DEBUG_TAG, "invalid tomatoes rating for: " + mTitle);
                            }

                            // Request poster download
                            byPoster(mPoster, imdbId);

                            /**
                             * Check if this imdbID is already in the db.
                             * Some OMDB queries have weird characters that aren't detected in a
                             * local db query; those titles would create many duplicates.
                             */
                            String[] mProjection =
                                    {
                                            MovieClubDbContract.Movie._ID,
                                            MovieClubDbContract.Movie.COLUMN_NAME_IMDB_ID,
                                    };

                            String mSelectionClause = MovieClubDbContract.Movie.COLUMN_NAME_IMDB_ID + " = ?";
                            String[] mSelectionArgs = {imdbId};

                            Cursor mCursor = mFragment.getActivity().getContentResolver().query(
                                    MovieClubDbContract.Movie.CONTENT_URI,
                                    mProjection,
                                    mSelectionClause,
                                    mSelectionArgs,
                                    null);

                            if (null == mCursor) {
                                Log.e(DEBUG_TAG, "Error doing query");
                            } else if (mCursor.getCount() < 1) {
                                // Didn't find movie, add to the db
                                mCursor.close();

                                ContentValues mNewValues = new ContentValues();

                                mNewValues.put(MovieClubDbContract.Movie.COLUMN_NAME_IMDB_ID, imdbId);
                                mNewValues.put(MovieClubDbContract.Movie.COLUMN_NAME_MOVIE_TITLE, mTitle);
                                mNewValues.put(MovieClubDbContract.Movie.COLUMN_NAME_MOVIE_YEAR, mYear);
                                mNewValues.put(MovieClubDbContract.Movie.COLUMN_NAME_MOVIE_SHORT_PLOT, mShortPlot);
                                mNewValues.put(MovieClubDbContract.Movie.COLUMN_NAME_MOVIE_POSTER, mPoster);
                                mNewValues.put(MovieClubDbContract.Movie.COLUMN_NAME_MOVIE_RATING, mRating);

                                // Use the query handler to get notified when the insert is complete (and to do it asynchronously in the background)
                                AsyncQueryHandler asyncQueryHandler = new AsyncQueryHandler(mFragment.getActivity().getContentResolver()) {
                                    @Override
                                    protected void onInsertComplete(int token, Object cookie, Uri uri) {
                                        super.onInsertComplete(token, cookie, uri);
//                                        Log.i(DEBUG_TAG, "insert complete for uri: " + uri.toString());

                                        // restart loader to update UI with new item that is now inserted
                                        mFragment.getLoaderManager().restartLoader(0, null, mLoaderCallbacks);
                                        byFullPlot(imdbId); // get fullplot
                                    }
                                };

                                asyncQueryHandler.startInsert(0, null, MovieClubDbContract.Movie.CONTENT_URI, mNewValues);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(DEBUG_TAG, "Volley object request error: " + error.toString());
                    }
                });
        mQueue.add(jsObjRequest);
    }

    private void byPoster(final String url, final String imdbId) {
        // Retrieves an image specified by the URL and puts it in the memory model
        ImageRequest request = new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        Memory.getInstance().getPosterMap().put(imdbId, bitmap);
                        // TODO Maybe do something here to get a refresh depending on who needs it
                        // restart loader to update UI with new item that is now inserted
                        mFragment.getLoaderManager().restartLoader(MovieClubConstants.MOVIE_LOADER_PREVIEW_ID, null, mLoaderCallbacks);
                    }
                }, 0, 0, null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        Log.e(DEBUG_TAG, "Volley image request error: " + error.toString());
                    }
                });
        mQueue.add(request);
    }

    private void byFullPlot(String imdbId){
        Log.i(DEBUG_TAG, "trying to download fullplot from OMDB.");

        final String url = "http://www.omdbapi.com/?i=" + imdbId + "&plot=full&r=json";
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(final JSONObject response) {
                        final String imdbId;
                        String mFullPlot;

                        try {
                            imdbId = response.getString("imdbID");
                            mFullPlot = response.getString("Plot");
                            Log.i(DEBUG_TAG, "got full plot: " + mFullPlot);

                            ContentValues mNewValues = new ContentValues();
                            mNewValues.put(MovieClubDbContract.Movie.COLUMN_NAME_MOVIE_FULL_PLOT, mFullPlot);
                            String mSelectionClause = MovieClubDbContract.Movie.COLUMN_NAME_IMDB_ID + " = ?";
                            String[] mSelectionArgs = {imdbId};

                            // Use the query handler to get notified when the insert is complete (and to do it asynchronously in the background)
                            AsyncQueryHandler asyncQueryHandler = new AsyncQueryHandler(mFragment.getActivity().getContentResolver()) {
                                @Override
                                protected void onUpdateComplete(int token, Object cookie, int result) {
                                    super.onUpdateComplete(token, cookie, result);
                                    Log.i(DEBUG_TAG, "added full plot to db, result: " + result);

                                    // restart loader to update UI with new item that is now inserted
                                    mFragment.getLoaderManager().restartLoader(0, null, mLoaderCallbacks);
                                }
                            };
                            asyncQueryHandler.startUpdate(0, null, MovieClubDbContract.Movie.CONTENT_URI, mNewValues, mSelectionClause, mSelectionArgs);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(DEBUG_TAG, "Volley object request error: " + error.toString());
                    }
                });
        mQueue.add(jsObjRequest);
    }
}
