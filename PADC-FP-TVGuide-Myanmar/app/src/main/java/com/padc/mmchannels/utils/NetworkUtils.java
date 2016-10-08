package com.padc.mmchannels.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by user on 9/29/2016.
 */
public class NetworkUtils {

    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        //should check null because in air plan mode it will be null
        return (netInfo != null && netInfo.isConnected());
    }

/*
    private boolean hasNetworkConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
*/

    private static boolean isRemoteHostAvailable() {
        // TODO:: to ping and verify the host API, may be not necessary
        return false;
    }
}
