package com.padc.tvguide.utils;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;

import com.padc.tvguide.R;
import com.padc.tvguide.TVGuideApp;
import com.padc.tvguide.activities.HomeActivity;

/**
 * Created by user on 10/8/2016.
 */

public class NotificationUtils {

    private static final int GENERAL_NOTIFICATION_ID = 4200;
    private static final int GENERAL_NOTIFICATION_ID_BIG_TEXT = 4300;

    public static void showNotification() {
        Context context = TVGuideApp.getContext();

        //Notification Title
        String title = "Alarm from TVGuide";

        //Notification Text
        String text = "Your program will be aired soon";

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setColor(context.getResources().getColor(R.color.primary))
                .setSmallIcon(R.drawable.ic_notifications_gold_24dp)
//                .setLargeIcon(TVGuideApp.getAppIcon())
                .setContentTitle(title)
                .setContentText(text)
                .setAutoCancel(true);

        //Open the app when user tap on notification
        Intent resultIntent = new Intent(context, HomeActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(GENERAL_NOTIFICATION_ID, builder.build());
    }
}
