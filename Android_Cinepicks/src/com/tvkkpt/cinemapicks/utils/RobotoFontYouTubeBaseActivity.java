package com.tvkkpt.cinemapicks.utils;

import android.view.ViewGroup;
import com.google.android.youtube.player.YouTubeBaseActivity;

/**
 * Copyright © Smart Cows Inc. All rights reserved.
 * Author: tvkkpt
 * Date: 12/31/12
 * Time: 1:04 PM
 */
public class RobotoFontYouTubeBaseActivity extends YouTubeBaseActivity {

    @Override
    protected void onPostCreate(android.os.Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        FontUtils.setRobotoFont(this, (ViewGroup)this.getWindow().getDecorView());
    }

}
