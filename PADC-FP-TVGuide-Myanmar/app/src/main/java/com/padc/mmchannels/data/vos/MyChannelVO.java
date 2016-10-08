package com.padc.mmchannels.data.vos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.padc.mmchannels.MMChannelsApp;
import com.padc.mmchannels.data.persistence.TVGuideContract.MyChannelEntry;

import java.util.List;

/**
 * Created by user on 9/10/2016.
 */
public class MyChannelVO {

    private long my_channel_id;
    private long user_id;
    private long channel_id;
    private int sort_order;
    private long row_timestamp;
    private int record_status;
    private ChannelVO channelVO;

    public long getMy_channel_id() {
        return my_channel_id;
    }

    public void setMy_channel_id(long my_channel_id) {
        this.my_channel_id = my_channel_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(long channel_id) {
        this.channel_id = channel_id;
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

    public ChannelVO getChannelVO() {
        return channelVO;
    }

    public void setChannelVO(ChannelVO channelVO) {
        this.channelVO = channelVO;
    }

    public MyChannelVO(){

    }

    public MyChannelVO(long user_id, long channel_id) {
        this.user_id = user_id;
        this.channel_id = channel_id;
    }

    public static boolean getIsMyChannel(long user_id, long channel_id){

        String selection = MyChannelEntry.COLUMN_USER_ID+"=? AND "+MyChannelEntry.COLUMN_CHANNEL_ID+"=?";
        String[] selectionArgs = {String.valueOf(user_id), String.valueOf(channel_id)};
        Cursor cursor = MMChannelsApp.getContext().getContentResolver().query(
                MyChannelEntry.buildMyChannelUriWithIDs(user_id, channel_id),
                null, selection, selectionArgs, null);
        if(cursor != null && cursor.moveToFirst()) {
            return true;
        }
        return false;
    }

    public static void saveMyChannel(MyChannelVO channel) {
        Context context = MMChannelsApp.getContext();
        ContentValues channelCV = new ContentValues();

        channelCV = channel.parseToContentValues();
        //insert into channels.
        context.getContentResolver().insert(MyChannelEntry.CONTENT_URI, channelCV);

        Log.d(MMChannelsApp.TAG, "Channel inserted into channel table");
    }

    public static void deleteMyChannel(long user_id, long channel_id){
        Context context = MMChannelsApp.getContext();
        String where = MyChannelEntry.COLUMN_USER_ID + "=? AND " + MyChannelEntry.COLUMN_CHANNEL_ID + "=?";
        String[] selectionArgs = {String.valueOf(user_id), String.valueOf(channel_id)};
        context.getContentResolver().delete(MyChannelEntry.CONTENT_URI, where, selectionArgs);
    }

    public static void saveMyChannels(List<MyChannelVO> myChannelList) {
        Context context = MMChannelsApp.getContext();
        ContentValues[] channelCVs = new ContentValues[myChannelList.size()];
        for (int index = 0; index < myChannelList.size(); index++) {
            MyChannelVO channel = myChannelList.get(index);
            channelCVs[index] = channel.parseToContentValues();
        }

        //Bulk insert into channels.
        int insertedCount = context.getContentResolver().bulkInsert(MyChannelEntry.CONTENT_URI, channelCVs);

        Log.d(MMChannelsApp.TAG, "Bulk inserted into channel table : " + insertedCount);
    }

    private ContentValues parseToContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(MyChannelEntry.COLUMN_MY_CHANNEL_ID, my_channel_id);
        cv.put(MyChannelEntry.COLUMN_USER_ID, user_id);
        cv.put(MyChannelEntry.COLUMN_CHANNEL_ID, channel_id);
        cv.put(MyChannelEntry.COLUMN_SORT_ORDER, sort_order);
        cv.put(MyChannelEntry.COLUMN_ROW_TIMESTAMP, row_timestamp);
        cv.put(MyChannelEntry.COLUMN_RECORD_STATUS, record_status);
        return cv;
    }

    public static MyChannelVO parseFromCursor(Cursor data) {
        MyChannelVO channel = new MyChannelVO();
        channel.my_channel_id = data.getLong(data.getColumnIndex(MyChannelEntry.COLUMN_MY_CHANNEL_ID));
        channel.user_id = data.getLong(data.getColumnIndex(MyChannelEntry.COLUMN_USER_ID));
        channel.channel_id = data.getLong(data.getColumnIndex(MyChannelEntry.COLUMN_CHANNEL_ID));
        channel.sort_order = data.getInt(data.getColumnIndex(MyChannelEntry.COLUMN_SORT_ORDER));
        channel.row_timestamp = data.getLong(data.getColumnIndex(MyChannelEntry.COLUMN_ROW_TIMESTAMP));
        channel.record_status = data.getInt(data.getColumnIndex(MyChannelEntry.COLUMN_RECORD_STATUS));
        return channel;
    }
}
