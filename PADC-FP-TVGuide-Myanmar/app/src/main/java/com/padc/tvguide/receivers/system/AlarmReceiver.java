package com.padc.tvguide.receivers.system;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.padc.tvguide.TVGuideApp;
import com.padc.tvguide.utils.NetworkUtils;
import com.padc.tvguide.utils.NotificationUtils;

/**
 * Created by user on 9/29/2016.
 */
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent)
    {
        // TODO Auto-generated method stub


        // here you can start an activity or service depending on your need
        // for ex you can start an activity to vibrate phone or to ring the phone

        NotificationUtils.showNotification();
    }
}
