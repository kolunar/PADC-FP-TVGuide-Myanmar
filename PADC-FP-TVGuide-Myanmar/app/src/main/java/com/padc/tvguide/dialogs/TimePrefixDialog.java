package com.padc.tvguide.dialogs;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.padc.tvguide.R;
import com.padc.tvguide.TVGuideApp;
import com.padc.tvguide.receivers.system.AlarmReceiver;
import com.padc.tvguide.utils.DateTimeUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by user on 9/17/2016.
 */
public class TimePrefixDialog extends DialogFragment {

    static String ITEM_TIME_AHEAD = "IE_ITEM_TIME_AHEAD";
    static String ITEM_CHANNEL_PROGRAM_ID = "IE_ITEM_CHANNEL_PROGRAM_ID";
    private int mCheckedItem = 0;
    private long mChannel_Program_ID = 0;
    private AddRemoveAlarmDelegate mAddRemoveAlarmDelegate;

    public static void promptSingleChoiceItems(Activity activity, int checkedItem, final AddRemoveAlarmDelegate controller) {
//        TimePrefixDialog.newInstance(checkedItem, controller).show
    }

    public static TimePrefixDialog newInstance(long channel_program_id, int time_ahead) {
        TimePrefixDialog f = new TimePrefixDialog();
        Bundle args = new Bundle();
        args.putLong(ITEM_CHANNEL_PROGRAM_ID, channel_program_id);
        args.putInt(ITEM_TIME_AHEAD, time_ahead);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState==null) {
            //not restart
            Bundle args = getArguments();
            if (args==null) {
                throw new IllegalArgumentException("Bundle args required");
            }
            mChannel_Program_ID = args.getLong(ITEM_CHANNEL_PROGRAM_ID);
            mCheckedItem = getCheckedItem(args.getInt(ITEM_TIME_AHEAD));
        } else {
            //restart
            mChannel_Program_ID = savedInstanceState.getLong(ITEM_CHANNEL_PROGRAM_ID);
            mCheckedItem = getCheckedItem(savedInstanceState.getInt(ITEM_TIME_AHEAD));
        }
    }

    private int getCheckedItem(int time_ahead){
        switch(time_ahead){
            case 30:
                return 1;
            case 60:
                return 2;
            case 90:
                return 3;
            case 120:
                return 4;
            case 150:
                return 5;
            case 180:
                return 6;
        }
        return 0;
    }

    private int getTimeAhead(int checkedItem){
        switch(checkedItem){
            case 1:
                return 30;
            case 2:
                return 60;
            case 3:
                return 90;
            case 4:
                return 120;
            case 5:
                return 150;
            case 6:
                return 180;
        }
        return 0;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);
//        CharSequence times[] = new CharSequence[] {"when program starts", "5 mins to start", "15 mins to start", "30 mins to start"};
        String[] programListArray = getResources().getStringArray(R.array.dummy_alarm_prefix);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Pick a time");
        builder.setSingleChoiceItems(programListArray, mCheckedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(getContext(), "time:"+which , Toast.LENGTH_SHORT).show();
                if(which > 0) {
                    mAddRemoveAlarmDelegate.onAddAlarm(mChannel_Program_ID, getTimeAhead(which));
                }
                else {
                    mAddRemoveAlarmDelegate.onRemoveAlarm(mChannel_Program_ID);
                }
                dialog.dismiss();
            }
        });

        /*
        builder.setMessage("Are you sure?");

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View alertView = inflater.inflate(R.layout.view_item_channel, null);
        builder.setView(alertView);
        */

//        final Dialog thisDialog = builder.create();

//        builder.setPositiveButton("OK",  new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                // on success
//            }
//        });
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });

/*        builder.setOnKeyListener(new Dialog.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface arg0, int keyCode,
                                 KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    new AlertDialog.Builder(getActivity())
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Add Item")
                            .setMessage("Are you sure you want to finish?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    thisDialog.dismiss();
                                }
                            }).setNegativeButton("No", null).show();
                }
                return false;
            }
        });*/
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mAddRemoveAlarmDelegate = (AddRemoveAlarmDelegate) context;
    }

    public static void AddAlarm(int requestCode, String air_date, String start_time, int time_ahead, boolean isCancel) {
        Intent intent = new Intent(TVGuideApp.getContext(), AlarmReceiver.class);
        intent.putExtra("ALARM_REQUEST_CODE", requestCode);
        PendingIntent operation = PendingIntent.getBroadcast(TVGuideApp.getContext(), requestCode, intent,  PendingIntent.FLAG_ONE_SHOT );
        AlarmManager alarm = (AlarmManager) TVGuideApp.getContext().getSystemService(ALARM_SERVICE);
        Toast.makeText(TVGuideApp.getContext(), "Alarm Set", Toast.LENGTH_SHORT).show();
        alarm.cancel(operation);
        if(!isCancel)
            alarm.set(AlarmManager.RTC_WAKEUP, DateTimeUtils.getAlarmTime(air_date, start_time, time_ahead), operation);
    }

    public interface AddRemoveAlarmDelegate {

        void onAddAlarm(long channel_program_id, int time_ahead);

        void onRemoveAlarm(long channel_program_id);
    }
}
