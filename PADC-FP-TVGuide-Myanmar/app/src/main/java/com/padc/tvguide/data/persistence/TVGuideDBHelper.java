package com.padc.tvguide.data.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.padc.tvguide.data.persistence.TVGuideContract.ChannelEntry;

/**
 * Created by user on 9/24/2016.
 */
public class TVGuideDBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "tvguide.db";

    private static final String SQL_CREATE_CHANNEL_TABLE = "CREATE TABLE " + ChannelEntry.TABLE_NAME + " (" +
            ChannelEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ChannelEntry.COLUMN_CHANNEL_ID + " INTEGER NOT NULL, " +
            ChannelEntry.COLUMN_CHANNEL_NAME + " TEXT NOT NULL, " +
            ChannelEntry.COLUMN_CHANNEL_DESC + " TEXT NOT NULL, " +
            ChannelEntry.COLUMN_CHANNEL_ICON + " TEXT NOT NULL, " +
            ChannelEntry.COLUMN_CHANNEL_BANNER + " TEXT NOT NULL, " +
            ChannelEntry.COLUMN_CHANNEL_START_TIME + " TEXT NOT NULL, " +
            ChannelEntry.COLUMN_CHANNEL_END_TIME + " TEXT NOT NULL, " +
            ChannelEntry.COLUMN_CHANNEL_SORT_ORDER + " INTEGER NOT NULL, " +
            ChannelEntry.COLUMN_CHANNEL_ROW_TIMESTAMP + " INTEGER NOT NULL, " +
            ChannelEntry.COLUMN_CHANNEL_RECORD_STATUS + " INTEGER NOT NULL, " +
            " UNIQUE (" + ChannelEntry.COLUMN_CHANNEL_ID + ") ON CONFLICT IGNORE" +
            " );";

    public TVGuideDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_CHANNEL_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ChannelEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
