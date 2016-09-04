package com.padc.tvguide;

import android.app.Application;
import android.content.Context;

/**
 * Created by user on 9/4/2016.
 */
public class TVGuideApp extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
