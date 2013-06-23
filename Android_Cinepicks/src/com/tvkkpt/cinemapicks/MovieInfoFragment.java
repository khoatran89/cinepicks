package com.tvkkpt.cinemapicks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.tvkkpt.cinemapicks.models.MovieItemModel;
import com.tvkkpt.cinemapicks.models.ViewModel;
import com.tvkkpt.cinemapicks.utils.RobotoFontFragment;

/**
 * Created with IntelliJ IDEA.
 * User: Bo Tot
 * Date: 12/26/12
 * Time: 2:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class MovieInfoFragment extends RobotoFontFragment {

    private MovieItemModel movie;

    protected final ImageLoader imageLoader = ImageLoader.getInstance();
    protected DisplayImageOptions displayImageOptions;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }

        // retrieve the movie item
        int movieType = getArguments().getInt("movie_type");
        int moviePosition = getArguments().getInt("movie_position");
        movie = ViewModel.getInstance().getMovieListByType(movieType).get(moviePosition);

        View view = inflater.inflate(R.layout.fragment_movie_info, container, false);

        TextView title = (TextView)view.findViewById(R.id.title);
        TextView director = (TextView)view.findViewById(R.id.director);
        TextView casting = (TextView)view.findViewById(R.id.casting);
        TextView duration = (TextView)view.findViewById(R.id.duration);
        TextView genre = (TextView)view.findViewById(R.id.genre);
        TextView description = (TextView)view.findViewById(R.id.description);
        ImageView thumbnail = (ImageView)view.findViewById(R.id.thumbnail);

        title.setText(movie.getMovieTitle());
        director.setText(movie.getDirector());
        casting.setText(movie.getCasting());
        duration.setText(movie.getDuration());
        genre.setText(movie.getGenre());
        description.setText(movie.getMovieDescription());

        imageLoader.displayImage(movie.getThumbUrl(), thumbnail, displayImageOptions);

        return view;
    }

    @Override
    public void onActivityCreated(android.os.Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
        displayImageOptions = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.bg_image_loading)
                .showImageForEmptyUri(R.drawable.bg_image_notfound)
                .cacheInMemory()
                .cacheOnDisc()
                .build();
    }
}
