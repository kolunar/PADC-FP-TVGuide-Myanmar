package com.padc.tvguide;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.padc.tvguide.utils.NetworkUtils;

/**
 * Created by user on 9/4/2016.
 */
public class TVGuideApp extends Application {

    private static Context context;
    public static final String TAG = "TVGuideApp";
    public static boolean hasInternet = true;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        hasInternet = NetworkUtils.isOnline(context);
    }

    public static Context getContext() {
        return context;
    }

}
