package com.padc.mmchannels.data.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.padc.mmchannels.data.persistence.TVGuideContract.ChannelEntry;
import com.padc.mmchannels.data.persistence.TVGuideContract.ProgramEntry;
import com.padc.mmchannels.data.persistence.TVGuideContract.ChannelProgramEntry;
import com.padc.mmchannels.data.persistence.TVGuideContract.MyChannelEntry;
import com.padc.mmchannels.data.persistence.TVGuideContract.MyWatchListEntry;
import com.padc.mmchannels.data.persistence.TVGuideContract.MyRemindersEntry;


/**
 * Created by user on 9/24/2016.
 */
public class TVGuideDBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "mmchannels.db";

    private static final String SQL_CREATE_CHANNEL_TABLE = "CREATE TABLE " + ChannelEntry.TABLE_NAME + " (" +
            ChannelEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ChannelEntry.COLUMN_CHANNEL_ID + " INTEGER NOT NULL, " +
            ChannelEntry.COLUMN_CHANNEL_NAME + " TEXT NOT NULL, " +
            ChannelEntry.COLUMN_CHANNEL_DESC + " TEXT NOT NULL, " +
            ChannelEntry.COLUMN_CHANNEL_ICON + " TEXT NOT NULL, " +
            ChannelEntry.COLUMN_CHANNEL_BANNER + " TEXT NOT NULL, " +
            ChannelEntry.COLUMN_START_TIME + " TEXT NOT NULL, " +
            ChannelEntry.COLUMN_END_TIME + " TEXT NOT NULL, " +
            ChannelEntry.COLUMN_SORT_ORDER + " INTEGER NOT NULL, " +
            ChannelEntry.COLUMN_ROW_TIMESTAMP + " INTEGER NOT NULL, " +
            ChannelEntry.COLUMN_RECORD_STATUS + " INTEGER NOT NULL, " +
            " UNIQUE (" + ChannelEntry.COLUMN_CHANNEL_ID + ") ON CONFLICT REPLACE" +
            " );";

    private static final String SQL_CREATE_PROGRAM_TABLE = "CREATE TABLE " + ProgramEntry.TABLE_NAME + " (" +
            ProgramEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ProgramEntry.COLUMN_PROGRAM_ID + " INTEGER NOT NULL, " +
            ProgramEntry.COLUMN_PARENT_ID + " INTEGER NOT NULL, " +
            ProgramEntry.COLUMN_PROGRAM_TITLE + " TEXT NOT NULL, " +
            ProgramEntry.COLUMN_PROGRAM_DESC + " TEXT NOT NULL, " +
            ProgramEntry.COLUMN_PROGRAM_IMAGE + " TEXT NOT NULL, " +
            ProgramEntry.COLUMN_LANGUAGE + " TEXT DEFAULT '', " +
            ProgramEntry.COLUMN_PROGRAM_TYPE + " INTEGER NOT NULL, " +
            ProgramEntry.COLUMN_ROW_TIMESTAMP + " INTEGER NOT NULL, " +
            ProgramEntry.COLUMN_RECORD_STATUS + " INTEGER NOT NULL, " +
            " UNIQUE (" + ProgramEntry.COLUMN_PROGRAM_ID + ") ON CONFLICT REPLACE" +
            " );";

    private static final String SQL_CREATE_CHANNEL_PROGRAM_TABLE = "CREATE TABLE " + ChannelProgramEntry.TABLE_NAME + " (" +
            ChannelProgramEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ChannelProgramEntry.COLUMN_CHANNEL_PROGRAM_ID + " INTEGER NOT NULL, " +
            ChannelProgramEntry.COLUMN_AIR_DATE + " TEXT NOT NULL, " +
            ChannelProgramEntry.COLUMN_AIR_DAY + " TEXT NOT NULL, " +
            ChannelProgramEntry.COLUMN_START_TIME + " TEXT NOT NULL, " +
            ChannelProgramEntry.COLUMN_DURATION + " INTEGER NOT NULL, " +
            ChannelProgramEntry.COLUMN_CHANNEL_ID + " INTEGER NOT NULL, " +
            ChannelProgramEntry.COLUMN_PROGRAM_ID + " INTEGER NOT NULL, " +
            ChannelProgramEntry.COLUMN_ROW_TIMESTAMP + " INTEGER NOT NULL, " +
            ChannelProgramEntry.COLUMN_RECORD_STATUS + " INTEGER NOT NULL, " +
            " UNIQUE (" + ChannelProgramEntry.COLUMN_CHANNEL_PROGRAM_ID + ") ON CONFLICT REPLACE" +
            " );";

    private static final String SQL_CREATE_MY_CHANNEL_TABLE = "CREATE TABLE " + MyChannelEntry.TABLE_NAME + " (" +
            MyChannelEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            MyChannelEntry.COLUMN_MY_CHANNEL_ID + " INTEGER DEFAULT 0, " +
            MyChannelEntry.COLUMN_USER_ID + " INTEGER NOT NULL, " +
            MyChannelEntry.COLUMN_CHANNEL_ID + " INTEGER NOT NULL, " +
            MyChannelEntry.COLUMN_SORT_ORDER + " INTEGER DEFAULT 0, " +
            MyChannelEntry.COLUMN_ROW_TIMESTAMP + " INTEGER DEFAULT 0, " +
            MyChannelEntry.COLUMN_RECORD_STATUS + " INTEGER DEFAULT 0, " +
            " UNIQUE (" + MyChannelEntry.COLUMN_USER_ID + ", " + MyChannelEntry.COLUMN_CHANNEL_ID + ") ON CONFLICT REPLACE" +
            " );";

    private static final String SQL_CREATE_MY_WATCHLIST_TABLE = "CREATE TABLE " + MyWatchListEntry.TABLE_NAME + " (" +
            MyWatchListEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            MyWatchListEntry.COLUMN_MY_WATCHLIST_ID + " INTEGER DEFAULT 0, " +
            MyWatchListEntry.COLUMN_USER_ID + " INTEGER NOT NULL, " +
            MyWatchListEntry.COLUMN_PROGRAM_ID + " INTEGER NOT NULL, " +
            MyWatchListEntry.COLUMN_WATCHED + " INTEGER DEFAULT 0, " +
            MyWatchListEntry.COLUMN_SORT_ORDER + " INTEGER DEFAULT 0, " +
            MyWatchListEntry.COLUMN_ROW_TIMESTAMP + " INTEGER DEFAULT 0, " +
            MyWatchListEntry.COLUMN_RECORD_STATUS + " INTEGER DEFAULT 0, " +
            " UNIQUE (" + MyWatchListEntry.COLUMN_USER_ID + ", " + MyWatchListEntry.COLUMN_PROGRAM_ID + ") ON CONFLICT REPLACE" +
            " );";

    private static final String SQL_CREATE_MY_REMINDERS_TABLE = "CREATE TABLE " + MyRemindersEntry.TABLE_NAME + " (" +
            MyRemindersEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            MyRemindersEntry.COLUMN_MY_REMINDER_ID + " INTEGER DEFAULT 0, " +
            MyRemindersEntry.COLUMN_USER_ID + " INTEGER NOT NULL, " +
            MyRemindersEntry.COLUMN_CHANNEL_PROGRAM_ID + " INTEGER NOT NULL, " +
            MyRemindersEntry.COLUMN_TIME_AHEAD + " INTEGER DEFAULT 0, " +
            MyRemindersEntry.COLUMN_SORT_ORDER + " INTEGER DEFAULT 0, " +
            MyRemindersEntry.COLUMN_ROW_TIMESTAMP + " INTEGER DEFAULT 0, " +
            MyRemindersEntry.COLUMN_RECORD_STATUS + " INTEGER DEFAULT 0, " +
            " UNIQUE (" + MyRemindersEntry.COLUMN_USER_ID + ", " + MyRemindersEntry.COLUMN_CHANNEL_PROGRAM_ID + ") ON CONFLICT REPLACE" +
            " );";

    public TVGuideDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_CHANNEL_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_PROGRAM_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_CHANNEL_PROGRAM_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_MY_CHANNEL_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_MY_WATCHLIST_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_MY_REMINDERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ChannelEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ProgramEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ChannelProgramEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MyChannelEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MyWatchListEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MyRemindersEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
