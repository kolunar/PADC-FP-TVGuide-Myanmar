package com.padc.mmchannels;

import android.app.Application;
import android.content.Context;

import com.crashlytics.android.Crashlytics;
import com.padc.mmchannels.utils.NetworkUtils;

import io.fabric.sdk.android.Fabric;

/**
 * Created by user on 9/4/2016.
 */
public class MMChannelsApp extends Application {

    private static Context context;
    public static final String TAG = "TVGuideApp";
    public static boolean hasInternet = true;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        context = getApplicationContext();
        hasInternet = NetworkUtils.isOnline(context);
    }

    public static Context getContext() {
        return context;
    }

}
