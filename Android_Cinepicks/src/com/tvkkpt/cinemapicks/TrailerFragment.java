package com.tvkkpt.cinemapicks;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.tvkkpt.cinemapicks.models.MovieItemModel;
import com.tvkkpt.cinemapicks.models.ViewModel;
import com.tvkkpt.cinemapicks.utils.RobotoFontFragment;

/**
 * Created with IntelliJ IDEA.
 * User: Bo Tot
 * Date: 12/26/12
 * Time: 9:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class TrailerFragment extends RobotoFontFragment implements YouTubePlayer.OnInitializedListener {

    private static final int RECOVERY_DIALOG_REQUEST = 1;

    private MovieItemModel movie;

    private Dialog errorDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }

        int movieType = getArguments().getInt("movie_type");
        int moviePosition = getArguments().getInt("movie_position");
        movie = ViewModel.getInstance().getMovieListByType(movieType).get(moviePosition);

        View view = inflater.inflate(R.layout.fragment_trailer, container, false);

        YouTubePlayerView player = (YouTubePlayerView)view.findViewById(R.id.youtube_view);
        player.initialize(getString(R.string.google_developer_key), this);

        return view;
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer,
                                        boolean wasRestored) {
        if (!wasRestored) {
            String youtubeId = getYoutubeId();
            if (youtubeId != null) {
                youTubePlayer.loadVideo(youtubeId);
            }
            else {
                TextView noTrailer = (TextView)getView().findViewById(R.id.no_trailer_caption);
                noTrailer.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            if (errorDialog == null || !errorDialog.isShowing()) {
                errorDialog = errorReason.getErrorDialog(getActivity(), RECOVERY_DIALOG_REQUEST);
                errorDialog.show();
            }
        } else {
            String errorMessage = String.format(getString(R.string.error_player), errorReason.toString());
            Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    private String getYoutubeId() {
        if (movie.getVideoUrl().isEmpty()) return null;

        String youtubeId;
        int pos = movie.getVideoUrl().indexOf("v=");

        if (pos != -1)
        {
            youtubeId = movie.getVideoUrl().substring(pos + 2);
        }
        else
        {
            pos = movie.getVideoUrl().indexOf("youtu.be/");
            youtubeId = movie.getVideoUrl().substring(pos + 9);
        }

        return youtubeId;
    }
}
