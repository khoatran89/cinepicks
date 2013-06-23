package com.tvkkpt.cinemapicks.utils;

import android.app.Activity;
import android.app.ListFragment;
import android.view.ViewGroup;

/**
 * Copyright © Smart Cows Inc. All rights reserved.
 * Author: tvkkpt
 * Date: 12/31/12
 * Time: 1:06 PM
 */
public class RobotoFontListFragment extends ListFragment {

    @Override
    public void onActivityCreated(android.os.Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Activity activity = getActivity();
        FontUtils.setRobotoFont(activity, (ViewGroup)activity.getWindow().getDecorView());
    }

}
