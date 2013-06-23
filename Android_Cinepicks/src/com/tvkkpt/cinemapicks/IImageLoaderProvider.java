package com.tvkkpt.cinemapicks;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created with IntelliJ IDEA.
 * User: Bo Tot
 * Date: 12/26/12
 * Time: 12:29 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IImageLoaderProvider {

    public ImageLoader getImageLoader();

    public DisplayImageOptions getDisplayImageOptions();

}
