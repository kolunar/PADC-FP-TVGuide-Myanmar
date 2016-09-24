package com.padc.tvguide.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.padc.tvguide.R;

/**
 * Created by user on 9/17/2016.
 */
public class TimePrefixDialog extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);
//        CharSequence times[] = new CharSequence[] {"when program starts", "5 mins to start", "15 mins to start", "30 mins to start"};
        String[] programListArray = getResources().getStringArray(R.array.dummy_alarm_prefix);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Pick a time");
        builder.setSingleChoiceItems(programListArray, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), "time:"+which , Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        return builder.create();
    }
}
