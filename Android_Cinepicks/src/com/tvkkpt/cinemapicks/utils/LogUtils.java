package com.tvkkpt.cinemapicks.utils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Created with IntelliJ IDEA.
 * User: Bo Tot
 * Date: 12/24/12
 * Time: 12:58 AM
 * To change this template use File | Settings | File Templates.
 */
public class LogUtils {

    public static String getStackTrace(Exception e) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream stream = new PrintStream(baos);
        e.printStackTrace(stream);
        stream.flush();
        return new String(baos.toByteArray());
    }

}
