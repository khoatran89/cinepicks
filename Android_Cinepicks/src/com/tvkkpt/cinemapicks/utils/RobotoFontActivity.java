package com.tvkkpt.cinemapicks.utils;

import android.app.Activity;
import android.view.ViewGroup;

/**
 * Copyright © Smart Cows Inc. All rights reserved.
 * Author: tvkkpt
 * Date: 12/31/12
 * Time: 1:02 PM
 */
public class RobotoFontActivity extends Activity {

    @Override
    protected void onPostCreate(android.os.Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        FontUtils.setRobotoFont(this, (ViewGroup)this.getWindow().getDecorView());
    }

}
