package com.tvkkpt.cinemapicks.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright © Smart Cows Inc. All rights reserved.
 * Author: tvkkpt
 * Date: 12/31/12
 * Time: 12:32 PM
 */
public class FontUtils {

    public static interface FontTypes {
        public static String LIGHT = "Light";
        public static String BOLD = "Bold";
        public static String ITALIC = "Italic";
    }

    /**
     * map of font types to font paths in assets
     */
    private static Map<String, String> fontMap = new HashMap<String, String>();

    static {
        fontMap.put(FontTypes.LIGHT, "fonts/Roboto-Light.ttf");
        fontMap.put(FontTypes.BOLD, "fonts/Roboto-Bold.ttf");
        fontMap.put(FontTypes.ITALIC, "fonts/Roboto-Italic.ttf");
    }

    /* cache for loaded Roboto typefaces*/
    private static Map<String, Typeface> typefaceCache = new HashMap<String, Typeface>();

    /**
     * Creates Roboto typeface and puts it into cache
     *
     * @param context
     * @param fontType
     * @return
     */
    private static Typeface getRobotoTypeface(Context context, String fontType) {
        String fontPath = fontMap.get(fontType);

        if (!typefaceCache.containsKey(fontType)) {
            typefaceCache.put(fontType, Typeface.createFromAsset(context.getAssets(), fontPath));
        }

        return typefaceCache.get(fontType);
    }

    /**
     * Gets roboto typeface according to passed typeface style settings.
     * <p/>
     * Will get Roboto-Bold for Typeface.BOLD etc
     *
     * @param context
     * @param originalTypeface
     * @return
     */
    private static Typeface getRobotoTypeface(Context context, Typeface originalTypeface) {
        String robotoFontType = FontTypes.LIGHT; //default Light Roboto font

        if (originalTypeface != null) {
            int style = originalTypeface.getStyle();

            switch (style) {
                case Typeface.BOLD:
                    robotoFontType = FontTypes.BOLD;
                    break;
                case Typeface.ITALIC:
                    robotoFontType = FontTypes.ITALIC;
                    break;
            }
        }

        return getRobotoTypeface(context, robotoFontType);
    }

    /**
     * Walks ViewGroups, finds TextViews and applies Typefaces taking styling in consideration
     *
     * @param context - to reach assets
     * @param view    - root view to apply typeface to
     */
    public static void setRobotoFont(Context context, View view)
    {
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++){
                setRobotoFont(context, ((ViewGroup) view).getChildAt(i));
            }
        } else if (view instanceof TextView) {
            Typeface currentTypeface = ((TextView) view).getTypeface();
            ((TextView) view).setTypeface(getRobotoTypeface(context, currentTypeface));
        }
    }

}
