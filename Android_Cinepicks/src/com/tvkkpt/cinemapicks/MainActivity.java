package com.tvkkpt.cinemapicks;

import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.tvkkpt.cinemapicks.models.ViewModel;
import com.tvkkpt.cinemapicks.utils.RobotoFontActivity;

public class MainActivity extends RobotoFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Add Now Showing tab
        ActionBar.Tab nowShowingTab = actionBar.newTab().setText(R.string.now_showing_title);
        Fragment nowShowingFragment = MovieListFragment.newInstance(ViewModel.NOW_SHOWING_MOVIES);
        nowShowingTab.setTabListener(new MainTabsListener(nowShowingFragment));
        actionBar.addTab(nowShowingTab);

        // Add Upcoming tab
        ActionBar.Tab upcomingTab = actionBar.newTab().setText(R.string.upcoming_title);
        Fragment upcomingFragment = MovieListFragment.newInstance(ViewModel.UPCOMING_MOVIES);
        upcomingTab.setTabListener(new MainTabsListener(upcomingFragment));
        actionBar.addTab(upcomingTab);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_support:
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

