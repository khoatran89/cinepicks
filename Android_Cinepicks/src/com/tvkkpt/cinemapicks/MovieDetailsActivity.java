package com.tvkkpt.cinemapicks;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import com.tvkkpt.cinemapicks.utils.RobotoFontYouTubeBaseActivity;

/**
 * Created with IntelliJ IDEA.
 * User: Bo Tot
 * Date: 12/26/12
 * Time: 2:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class MovieDetailsActivity extends RobotoFontYouTubeBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        Bundle extras = getIntent().getExtras();

        // Add Movie Info tab
        ActionBar.Tab movieInfoTab = actionBar.newTab().setText(R.string.movie_info_title);
        MovieInfoFragment movieInfoFragment = new MovieInfoFragment();
        movieInfoFragment.setArguments(extras);
        movieInfoTab.setTabListener(new MainTabsListener(movieInfoFragment));
        actionBar.addTab(movieInfoTab);

        // Add Trailer tab
        ActionBar.Tab trailerTab = actionBar.newTab().setText(R.string.movie_trailer_title);
        TrailerFragment trailerFragment = new TrailerFragment();
        trailerFragment.setArguments(extras);
        trailerTab.setTabListener(new MainTabsListener(trailerFragment));
        actionBar.addTab(trailerTab);

        // Add Showtimes tab
        ActionBar.Tab showtimesTab = actionBar.newTab().setText(R.string.movie_showtimes_title);
        ShowtimesFragment showtimesFragment = new ShowtimesFragment();
        showtimesFragment.setArguments(extras);
        showtimesTab.setTabListener(new MainTabsListener(showtimesFragment));
        actionBar.addTab(showtimesTab);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
