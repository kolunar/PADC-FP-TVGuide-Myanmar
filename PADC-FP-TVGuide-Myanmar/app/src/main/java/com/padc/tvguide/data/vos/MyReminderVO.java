package com.padc.tvguide.data.vos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.padc.tvguide.TVGuideApp;
import com.padc.tvguide.data.persistence.TVGuideContract.MyRemindersEntry;

import java.util.List;

/**
 * Created by user on 9/10/2016.
 */
public class MyReminderVO {

    private long my_reminder_id;
    private long user_id;
    private long channel_program_id;
    private int time_ahead;
    private int sort_order;
    private long row_timestamp;
    private int record_status;
    private ChannelProgramVO channelProgramVO;

    public long getMy_reminder_id() {
        return my_reminder_id;
    }

    public void setMy_reminder_id(long my_reminder_id) {
        this.my_reminder_id = my_reminder_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public long getChannel_program_id() {
        return channel_program_id;
    }

    public void setChannel_program_id(long channel_program_id) {
        this.channel_program_id = channel_program_id;
    }

    public int getTime_ahead() {
        return time_ahead;
    }

    public void setTime_ahead(int time_ahead) {
        this.time_ahead = time_ahead;
    }

    public int getSort_order() {
        return sort_order;
    }

    public void setSort_order(int sort_order) {
        this.sort_order = sort_order;
    }

    public long getRow_timestamp() {
        return row_timestamp;
    }

    public void setRow_timestamp(long row_timestamp) {
        this.row_timestamp = row_timestamp;
    }

    public int getRecord_status() {
        return record_status;
    }

    public void setRecord_status(int record_status) {
        this.record_status = record_status;
    }

    public ChannelProgramVO getChannelProgramVO() {
        return channelProgramVO;
    }

    public void setChannelProgramVO(ChannelProgramVO channelProgramVO) {
        this.channelProgramVO = channelProgramVO;
    }

    public MyReminderVO(){

    }

    public MyReminderVO(long user_id, long channel_program_id, int time_ahead) {
        this.user_id = user_id;
        this.channel_program_id = channel_program_id;
        this.time_ahead = time_ahead;
    }

    public static MyReminderVO getMyReminderItem(long user_id, long channel_program_id){

        String selection = MyRemindersEntry.COLUMN_USER_ID+"=? AND "+MyRemindersEntry.COLUMN_CHANNEL_PROGRAM_ID+"=?";
        String[] selectionArgs = {String.valueOf(user_id), String.valueOf(channel_program_id)};
        Cursor cursor = TVGuideApp.getContext().getContentResolver().query(
                MyRemindersEntry.buildMyRemindersUriWithIDs(user_id, channel_program_id),
                null, selection, selectionArgs, null);
        if(cursor != null && cursor.moveToFirst()) {
            return parseFromCursor(cursor);
        }
        return null;
    }

    public static void saveMyReminder(MyReminderVO reminderVO) {
        Context context = TVGuideApp.getContext();
        ContentValues reminderCV = new ContentValues();

        reminderCV = reminderVO.parseToContentValues();
        //insert into channels.
        context.getContentResolver().insert(MyRemindersEntry.CONTENT_URI, reminderCV);

        Log.d(TVGuideApp.TAG, "Channel inserted into channel table");
    }

    public static void deleteMyReminder(long user_id, long channel_program_id){
        Context context = TVGuideApp.getContext();
        String where = MyRemindersEntry.COLUMN_USER_ID + "=? AND " + MyRemindersEntry.COLUMN_CHANNEL_PROGRAM_ID + "=?";
        String[] selectionArgs = {String.valueOf(user_id), String.valueOf(channel_program_id)};
        context.getContentResolver().delete(MyRemindersEntry.CONTENT_URI, where, selectionArgs);
    }

    public static void saveMyReminderList(List<MyReminderVO> myReminderList) {
        Context context = TVGuideApp.getContext();
        ContentValues[] reminderCVs = new ContentValues[myReminderList.size()];
        for (int index = 0; index < myReminderList.size(); index++) {
            MyReminderVO reminderVO = myReminderList.get(index);
            reminderCVs[index] = reminderVO.parseToContentValues();
        }

        //Bulk insert into channels.
        int insertedCount = context.getContentResolver().bulkInsert(MyRemindersEntry.CONTENT_URI, reminderCVs);

        Log.d(TVGuideApp.TAG, "Bulk inserted into channel table : " + insertedCount);
    }

    private ContentValues parseToContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(MyRemindersEntry.COLUMN_MY_REMINDER_ID, my_reminder_id);
        cv.put(MyRemindersEntry.COLUMN_USER_ID, user_id);
        cv.put(MyRemindersEntry.COLUMN_CHANNEL_PROGRAM_ID, channel_program_id);
        cv.put(MyRemindersEntry.COLUMN_TIME_AHEAD, time_ahead);
        cv.put(MyRemindersEntry.COLUMN_SORT_ORDER, sort_order);
        cv.put(MyRemindersEntry.COLUMN_ROW_TIMESTAMP, row_timestamp);
        cv.put(MyRemindersEntry.COLUMN_RECORD_STATUS, record_status);
        return cv;
    }

    public static MyReminderVO parseFromCursor(Cursor data) {
        MyReminderVO reminderVO = new MyReminderVO();
        reminderVO.my_reminder_id = data.getLong(data.getColumnIndex(MyRemindersEntry.COLUMN_MY_REMINDER_ID));
        reminderVO.user_id = data.getLong(data.getColumnIndex(MyRemindersEntry.COLUMN_USER_ID));
        reminderVO.channel_program_id = data.getLong(data.getColumnIndex(MyRemindersEntry.COLUMN_CHANNEL_PROGRAM_ID));
        reminderVO.time_ahead = data.getInt(data.getColumnIndex(MyRemindersEntry.COLUMN_TIME_AHEAD));
        reminderVO.sort_order = data.getInt(data.getColumnIndex(MyRemindersEntry.COLUMN_SORT_ORDER));
        reminderVO.row_timestamp = data.getLong(data.getColumnIndex(MyRemindersEntry.COLUMN_ROW_TIMESTAMP));
        reminderVO.record_status = data.getInt(data.getColumnIndex(MyRemindersEntry.COLUMN_RECORD_STATUS));
        return reminderVO;
    }
}
