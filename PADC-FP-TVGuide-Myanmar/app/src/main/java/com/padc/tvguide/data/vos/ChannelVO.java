package com.padc.tvguide.data.vos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.padc.tvguide.TVGuideApp;
import com.padc.tvguide.data.persistence.TVGuideContract;

import java.sql.Time;
import java.util.List;

/**
 * Created by user on 9/10/2016.
 */
public class ChannelVO {

    private int channel_id;
    private String channel_name;
    private String channel_desc;
    private String channel_icon;
    private String channel_banner;
    private String start_time;
    private String end_time;
    private int sort_order;
    private long row_timestamp;
    private int record_status;

    public int getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(int channel_id) {
        this.channel_id = channel_id;
    }

    public String getChannel_name() {
        return channel_name;
    }

    public void setChannel_name(String channel_name) {
        this.channel_name = channel_name;
    }

    public String getChannel_desc() {
        return channel_desc;
    }

    public void setChannel_desc(String channel_desc) {
        this.channel_desc = channel_desc;
    }

    public String getChannel_icon() {
        return channel_icon;
    }

    public void setChannel_icon(String channel_icon) {
        this.channel_icon = channel_icon;
    }

    public String getChannel_banner() {
        return channel_banner;
    }

    public void setChannel_banner(String channel_banner) {
        this.channel_banner = channel_banner;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
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

    public ChannelVO(){

    }

    public ChannelVO(int channel_id, String channel_name, String channel_icon) {
        this.channel_id = channel_id;
        this.channel_name = channel_name;
        this.channel_icon = channel_icon;
    }

    public static void saveChannels(List<ChannelVO> channelList) {
        Context context = TVGuideApp.getContext();
        ContentValues[] channelCVs = new ContentValues[channelList.size()];
        for (int index = 0; index < channelList.size(); index++) {
            ChannelVO channel = channelList.get(index);
            channelCVs[index] = channel.parseToContentValues();

            //Bulk insert into channel_details.
            //TODO::ChannelVO.saveChannelDetails(channel.getId(), channel.getDetails());
        }

        //Bulk insert into channels.
        int insertedCount = context.getContentResolver().bulkInsert(TVGuideContract.ChannelEntry.CONTENT_URI, channelCVs);

        Log.d(TVGuideApp.TAG, "Bulk inserted into channel table : " + insertedCount);
    }

    private ContentValues parseToContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(TVGuideContract.ChannelEntry.COLUMN_CHANNEL_ID, channel_id);
        cv.put(TVGuideContract.ChannelEntry.COLUMN_CHANNEL_NAME, channel_name);
        cv.put(TVGuideContract.ChannelEntry.COLUMN_CHANNEL_DESC, channel_desc);
        cv.put(TVGuideContract.ChannelEntry.COLUMN_CHANNEL_ICON, channel_icon);
        cv.put(TVGuideContract.ChannelEntry.COLUMN_CHANNEL_BANNER, channel_banner);
        cv.put(TVGuideContract.ChannelEntry.COLUMN_CHANNEL_START_TIME, start_time);
        cv.put(TVGuideContract.ChannelEntry.COLUMN_CHANNEL_END_TIME, end_time);
        //DateFormat formatter = new SimpleDateFormat("HH:mm");
        //java.sql.Time timeValue = new java.sql.Time(formatter.parse(start_time_string).getTime());
        cv.put(TVGuideContract.ChannelEntry.COLUMN_CHANNEL_SORT_ORDER, sort_order);
        cv.put(TVGuideContract.ChannelEntry.COLUMN_CHANNEL_ROW_TIMESTAMP, row_timestamp);
        cv.put(TVGuideContract.ChannelEntry.COLUMN_CHANNEL_RECORD_STATUS, record_status);
        return cv;
    }

    public static ChannelVO parseFromCursor(Cursor data) {
        ChannelVO channel = new ChannelVO();
        channel.channel_id = data.getInt(data.getColumnIndex(TVGuideContract.ChannelEntry.COLUMN_CHANNEL_ID));
        channel.channel_name = data.getString(data.getColumnIndex(TVGuideContract.ChannelEntry.COLUMN_CHANNEL_NAME));
        channel.channel_desc = data.getString(data.getColumnIndex(TVGuideContract.ChannelEntry.COLUMN_CHANNEL_DESC));
        channel.channel_icon = data.getString(data.getColumnIndex(TVGuideContract.ChannelEntry.COLUMN_CHANNEL_ICON));
        channel.channel_banner = data.getString(data.getColumnIndex(TVGuideContract.ChannelEntry.COLUMN_CHANNEL_BANNER));
        channel.start_time = data.getString(data.getColumnIndex(TVGuideContract.ChannelEntry.COLUMN_CHANNEL_START_TIME));
        channel.end_time = data.getString(data.getColumnIndex(TVGuideContract.ChannelEntry.COLUMN_CHANNEL_END_TIME));
        channel.sort_order = data.getInt(data.getColumnIndex(TVGuideContract.ChannelEntry.COLUMN_CHANNEL_SORT_ORDER));
        channel.row_timestamp = data.getInt(data.getColumnIndex(TVGuideContract.ChannelEntry.COLUMN_CHANNEL_ROW_TIMESTAMP));
        channel.record_status = data.getInt(data.getColumnIndex(TVGuideContract.ChannelEntry.COLUMN_CHANNEL_RECORD_STATUS));
        return channel;
    }
}
