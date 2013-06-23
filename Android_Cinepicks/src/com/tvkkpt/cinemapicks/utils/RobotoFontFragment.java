package com.tvkkpt.cinemapicks.utils;

import android.app.Activity;
import android.app.Fragment;
import android.view.ViewGroup;

/**
 * Copyright © Smart Cows Inc. All rights reserved.
 * Author: tvkkpt
 * Date: 12/31/12
 * Time: 12:58 PM
 */
public class RobotoFontFragment extends Fragment {

    @Override
    public void onActivityCreated(android.os.Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Activity activity = getActivity();
        FontUtils.setRobotoFont(activity, (ViewGroup)activity.getWindow().getDecorView());
    }

}
