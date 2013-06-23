package com.tvkkpt.cinemapicks.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.tvkkpt.cinemapicks.IImageLoaderProvider;
import com.tvkkpt.cinemapicks.R;
import com.tvkkpt.cinemapicks.models.MovieItemModel;
import com.tvkkpt.cinemapicks.utils.FontUtils;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Bo Tot
 * Date: 12/24/12
 * Time: 4:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class MovieListAdapter extends BaseAdapter {

    private IImageLoaderProvider provider;
    private List<MovieItemModel> movies;
    private LayoutInflater inflater;
    private Context context;

    public MovieListAdapter(IImageLoaderProvider p, List<MovieItemModel> m, LayoutInflater li, Context c) {
        provider = p;
        movies = m;
        inflater = li;
        context = c;
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(R.layout.list_row, null);
        }

        TextView title = (TextView)view.findViewById(R.id.title);
        TextView director = (TextView)view.findViewById(R.id.director);
        TextView duration = (TextView)view.findViewById(R.id.duration);
        TextView genre = (TextView)view.findViewById(R.id.genre);
        ImageView thumbnail = (ImageView)view.findViewById(R.id.list_image);

        MovieItemModel m = movies.get(i);

        title.setText(m.getMovieShortTitle());
        director.setText(m.getDirector());
        duration.setText(m.getDuration());
        genre.setText(m.getGenre());

        // load thumbnail image asynchronously
        provider.getImageLoader().displayImage(m.getThumbUrl(), thumbnail, provider.getDisplayImageOptions());

        FontUtils.setRobotoFont(context, view);
        FontUtils.setRobotoFont(context, title);

        return view;
    }

}
