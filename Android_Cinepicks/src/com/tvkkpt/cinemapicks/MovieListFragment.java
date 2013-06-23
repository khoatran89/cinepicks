package com.tvkkpt.cinemapicks;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.tvkkpt.cinemapicks.adapters.MovieListAdapter;
import com.tvkkpt.cinemapicks.models.MovieItemModel;
import com.tvkkpt.cinemapicks.models.ViewModel;
import com.tvkkpt.cinemapicks.utils.NetworkUtils;
import com.tvkkpt.cinemapicks.utils.RobotoFontListFragment;

import java.util.List;

/**
 * Copyright © Smart Cows Inc. All rights reserved.
 * Author: tvkkpt
 * Date: 12/26/12
 * Time: 12:01 PM
 *
 */
public class MovieListFragment extends RobotoFontListFragment implements IImageLoaderProvider {

    private int movieType;
    protected final ImageLoader imageLoader = ImageLoader.getInstance();
    protected DisplayImageOptions displayImageOptions;
    private Activity activity;
    private AlertDialog errorDialog;

    public static MovieListFragment newInstance(int type) {
        MovieListFragment f = new MovieListFragment();
        Bundle args = new Bundle();

        args.putInt("movie_type", type);
        f.setArguments(args);

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        movieType = getArguments().getInt("movie_type");
        activity = getActivity();
        imageLoader.init(ImageLoaderConfiguration.createDefault(activity));
        displayImageOptions = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.bg_image_loading)
                .showImageForEmptyUri(R.drawable.bg_image_notfound)
                .cacheInMemory()
                .cacheOnDisc()
                .build();
        return inflater.inflate(R.layout.fragment_movie_list, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (NetworkUtils.isNetworkConnected(activity)) {
            new UpdateMoviesTask().execute(movieType);
        } else {
            if (errorDialog == null) {
                errorDialog = new AlertDialog.Builder(activity).create();
                errorDialog.setCancelable(false);
                errorDialog.setButton(getString(R.string.network_error_ok_button),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }
                );
            }

            errorDialog.setTitle(getString(R.string.network_error_title));
            errorDialog.setMessage(getString(R.string.network_error_message));
            errorDialog.show();
        }
    }

    @Override
    public void onListItemClick(android.widget.ListView l, android.view.View v, int position, long id) {
        Intent intent = new Intent();
        intent.setClass(activity, MovieDetailsActivity.class);
        intent.putExtra("movie_type", movieType);
        intent.putExtra("movie_position", position);
        startActivity(intent);
    }


    @Override
    public final ImageLoader getImageLoader() {
        return imageLoader;
    }

    @Override
    public final DisplayImageOptions getDisplayImageOptions() {
        return displayImageOptions;
    }


    private class UpdateMoviesTask extends AsyncTask<Integer, Integer, String> {

        private List<MovieItemModel> shownList;

        @Override
        protected String doInBackground(Integer... params) {
            int movieType = params[0];
            switch (movieType) {
                case ViewModel.NOW_SHOWING_MOVIES:
                    shownList = ViewModel.getInstance().getNowShowingItems();
                    break;

                case ViewModel.UPCOMING_MOVIES:
                    shownList = ViewModel.getInstance().getUpcomingItems();
                    break;
            }

            ViewModel vm = ViewModel.getInstance();
            if (!vm.isDataLoaded()) {
                vm.loadData();
            }

            return "All Done!";
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            setListAdapter(new MovieListAdapter(
                    MovieListFragment.this,
                    shownList,
                    (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE),
                    activity
            ));

            View progressBar = activity.findViewById(R.id.progressBar);
            if (progressBar != null) {
                ((RelativeLayout)progressBar.getParent()).removeView(progressBar);
            }
        }

    }

}
