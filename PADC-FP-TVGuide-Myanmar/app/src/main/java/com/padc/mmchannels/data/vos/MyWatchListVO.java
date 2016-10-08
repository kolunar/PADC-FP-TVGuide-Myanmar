package com.padc.mmchannels.data.vos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.padc.mmchannels.MMChannelsApp;
import com.padc.mmchannels.data.persistence.TVGuideContract.MyWatchListEntry;

import java.util.List;

/**
 * Created by user on 9/10/2016.
 */
public class MyWatchListVO {

    private long my_watchlist_id;
    private long user_id;
    private long program_id;
    private int watched;
    private int sort_order;
    private long row_timestamp;
    private int record_status;
    private ProgramVO program;

    public long getMy_watchlist_id() {
        return my_watchlist_id;
    }

    public void setMy_watchlist_id(long my_watchlist_id) {
        this.my_watchlist_id = my_watchlist_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getProgram_id() {
        return program_id;
    }

    public void setProgram_id(long program_id) {
        this.program_id = program_id;
    }

    public int getWatched() {
        return watched;
    }

    public void setWatched(int watched) {
        this.watched = watched;
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

    public ProgramVO getProgram() {
        return program;
    }

    public void setProgram(ProgramVO program) {
        this.program = program;
    }

    public MyWatchListVO(){

    }

    public MyWatchListVO(long user_id, long program_id) {
        this.user_id = user_id;
        this.program_id = program_id;
    }

    public MyWatchListVO(long id, String name, String icon) {
        this.program_id = id;
        this.program.setParent_title(name);
        this.program.setProgram_image(icon);
    }

    public static int getIsMyWatchList(long user_id, long program_id){

        String selection = MyWatchListEntry.COLUMN_USER_ID+"=? AND "+MyWatchListEntry.COLUMN_PROGRAM_ID+"=?";
        String[] selectionArgs = {String.valueOf(user_id), String.valueOf(program_id)};
        Cursor cursor = MMChannelsApp.getContext().getContentResolver().query(
                MyWatchListEntry.buildMyWatchlistUriWithIDs(user_id, program_id),
                null, selection, selectionArgs, null);
        if(cursor != null && cursor.moveToFirst()) {
            return 1;
        }
        return 0;
    }

    public static void saveMyWatchList(MyWatchListVO channel) {
        Context context = MMChannelsApp.getContext();
        ContentValues channelCV = new ContentValues();

        channelCV = channel.parseToContentValues();
        //insert into channels.
        context.getContentResolver().insert(MyWatchListEntry.CONTENT_URI, channelCV);

        Log.d(MMChannelsApp.TAG, "Channel inserted into channel table");
    }

    public static void deleteMyWatchList(long user_id, long program_id){
        Context context = MMChannelsApp.getContext();
        String where = MyWatchListEntry.COLUMN_USER_ID + "=? AND " + MyWatchListEntry.COLUMN_PROGRAM_ID + "=?";
        String[] selectionArgs = {String.valueOf(user_id), String.valueOf(program_id)};
        context.getContentResolver().delete(MyWatchListEntry.CONTENT_URI, where, selectionArgs);
    }

    public static void saveMyWatchList(List<MyWatchListVO> myProgramList) {
        Context context = MMChannelsApp.getContext();
        ContentValues[] watchListCVs = new ContentValues[myProgramList.size()];
        for (int index = 0; index < myProgramList.size(); index++) {
            MyWatchListVO watchListVO = myProgramList.get(index);
            watchListCVs[index] = watchListVO.parseToContentValues();
        }

        //Bulk insert into channels.
        int insertedCount = context.getContentResolver().bulkInsert(MyWatchListEntry.CONTENT_URI, watchListCVs);

        Log.d(MMChannelsApp.TAG, "Bulk inserted into channel table : " + insertedCount);
    }

    private ContentValues parseToContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(MyWatchListEntry.COLUMN_MY_WATCHLIST_ID, my_watchlist_id);
        cv.put(MyWatchListEntry.COLUMN_USER_ID, user_id);
        cv.put(MyWatchListEntry.COLUMN_PROGRAM_ID, program_id);
        cv.put(MyWatchListEntry.COLUMN_WATCHED, watched);
        cv.put(MyWatchListEntry.COLUMN_SORT_ORDER, sort_order);
        cv.put(MyWatchListEntry.COLUMN_ROW_TIMESTAMP, row_timestamp);
        cv.put(MyWatchListEntry.COLUMN_RECORD_STATUS, record_status);
        return cv;
    }

    public static MyWatchListVO parseFromCursor(Cursor data) {
        MyWatchListVO watchListVO = new MyWatchListVO();
        watchListVO.my_watchlist_id = data.getLong(data.getColumnIndex(MyWatchListEntry.COLUMN_MY_WATCHLIST_ID));
        watchListVO.user_id = data.getLong(data.getColumnIndex(MyWatchListEntry.COLUMN_USER_ID));
        watchListVO.program_id = data.getLong(data.getColumnIndex(MyWatchListEntry.COLUMN_PROGRAM_ID));
        watchListVO.watched = data.getInt(data.getColumnIndex(MyWatchListEntry.COLUMN_WATCHED));
        watchListVO.sort_order = data.getInt(data.getColumnIndex(MyWatchListEntry.COLUMN_SORT_ORDER));
        watchListVO.row_timestamp = data.getLong(data.getColumnIndex(MyWatchListEntry.COLUMN_ROW_TIMESTAMP));
        watchListVO.record_status = data.getInt(data.getColumnIndex(MyWatchListEntry.COLUMN_RECORD_STATUS));
        return watchListVO;
    }
}
