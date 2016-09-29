package com.padc.tvguide.receivers.system;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.padc.tvguide.TVGuideApp;
import com.padc.tvguide.utils.NetworkUtils;

/**
 * Created by user on 9/29/2016.
 */
public class InternetConnectivityStateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (NetworkUtils.isOnline(context)) {
            TVGuideApp.hasInternet = true;
            Toast.makeText(context, "Device is connected with Internet.", Toast.LENGTH_SHORT).show();
        } else {
            TVGuideApp.hasInternet = false;
            Toast.makeText(context, "Device is disconnected with Internet.", Toast.LENGTH_SHORT).show();
        }
    }
}
