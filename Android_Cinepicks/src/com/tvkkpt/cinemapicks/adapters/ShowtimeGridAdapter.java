package com.tvkkpt.cinemapicks.adapters;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.tvkkpt.cinemapicks.R;
import com.tvkkpt.cinemapicks.utils.FontUtils;

import java.util.List;

/**
 * Copyright © Smart Cows Inc. All rights reserved.
 * Author: tvkkpt
 * Date: 12/30/12
 * Time: 3:06 PM
 *
 */
public class ShowtimeGridAdapter extends BaseAdapter {

    private Context context;
    private List<String> list;

    public ShowtimeGridAdapter(Context c, List<String> l) {
        context = c;
        list = l;
    }

    @Override
    public int getCount() {
        return list.size();
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
        TextView textView;

        if (view == null) {
            textView = new TextView(context);
            textView.setBackgroundResource(R.drawable.bg_showtime);
            textView.setTextAppearance(context, R.style.ShowtimeText);
            textView.setGravity(Gravity.CENTER);
            FontUtils.setRobotoFont(context, textView);
        }
        else {
            textView = (TextView)view;
        }

        textView.setText(list.get(i));

        return textView;
    }
}
