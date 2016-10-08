package com.padc.mmchannels.data.persistence;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by user on 9/24/2016.
 */
public class TVGuideProvider extends ContentProvider {
    public static final int CHANNEL = 100;
    public static final int PROGRAM = 200;
    public static final int CHANNEL_PROGRAM = 400;
    public static final int MY_CHANNELS = 500;
    public static final int MY_WATCHLIST = 600;
    public static final int MY_REMINDERS = 700;


    public static final int LOGIN_USER = 300;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private TVGuideDBHelper mTVGuideDBHelper;

    @Override
    public boolean onCreate() {
        mTVGuideDBHelper = new TVGuideDBHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor queryCursor;
        int matchUri = sUriMatcher.match(uri);
        switch (matchUri){
            case CHANNEL:
                queryCursor = mTVGuideDBHelper.getReadableDatabase().query(TVGuideContract.ChannelEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null, //group_by
                        null, //having
                        sortOrder);
                break;
            case PROGRAM:
                queryCursor = mTVGuideDBHelper.getReadableDatabase().query(TVGuideContract.ProgramEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null, //group_by
                        null, //having
                        sortOrder);
                break;
            case CHANNEL_PROGRAM:
                queryCursor = mTVGuideDBHelper.getReadableDatabase().query(TVGuideContract.ChannelProgramEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null, //group_by
                        null, //having
                        sortOrder);
                break;
            case MY_CHANNELS:
                queryCursor = mTVGuideDBHelper.getReadableDatabase().query(TVGuideContract.MyChannelEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null, //group_by
                        null, //having
                        sortOrder);
                break;
            case MY_WATCHLIST:
                queryCursor = mTVGuideDBHelper.getReadableDatabase().query(TVGuideContract.MyWatchListEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null, //group_by
                        null, //having
                        sortOrder);
                break;
            case MY_REMINDERS:
                queryCursor = mTVGuideDBHelper.getReadableDatabase().query(TVGuideContract.MyRemindersEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null, //group_by
                        null, //having
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri : " + uri);
        }
        Context context = getContext();
        if (context != null) {
            queryCursor.setNotificationUri(context.getContentResolver(), uri);
        }

        return queryCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        final int matchUri = sUriMatcher.match(uri);
        switch (matchUri) {
            case CHANNEL:
                return TVGuideContract.ChannelEntry.DIR_TYPE;
            case PROGRAM:
                return TVGuideContract.ProgramEntry.DIR_TYPE;
            case CHANNEL_PROGRAM:
                return TVGuideContract.ChannelProgramEntry.DIR_TYPE;
            case MY_CHANNELS:
                return TVGuideContract.MyChannelEntry.DIR_TYPE;
            case MY_WATCHLIST:
                return TVGuideContract.MyWatchListEntry.DIR_TYPE;
            case MY_REMINDERS:
                return TVGuideContract.MyRemindersEntry.DIR_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri : " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final SQLiteDatabase db = mTVGuideDBHelper.getWritableDatabase();
        final int matchUri = sUriMatcher.match(uri);
        Uri insertedUri;

        switch (matchUri) {
            case CHANNEL: {
                long _id = db.insert(TVGuideContract.ChannelEntry.TABLE_NAME, null, contentValues);
                if (_id > 0) {
                    insertedUri = TVGuideContract.ChannelEntry.buildChannelUri(_id);
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case PROGRAM: {
                long _id = db.insert(TVGuideContract.ProgramEntry.TABLE_NAME, null, contentValues);
                if (_id > 0) {
                    insertedUri = TVGuideContract.ProgramEntry.buildProgramUri(_id);
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case CHANNEL_PROGRAM: {
                long _id = db.insert(TVGuideContract.ChannelProgramEntry.TABLE_NAME, null, contentValues);
                if (_id > 0) {
                    insertedUri = TVGuideContract.ChannelProgramEntry.buildChannelProgramUri(_id);
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case MY_CHANNELS: {
                long _id = db.insert(TVGuideContract.MyChannelEntry.TABLE_NAME, null, contentValues);
                if (_id > 0) {
                    insertedUri = TVGuideContract.MyChannelEntry.buildMyChannelUri(_id);
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case MY_WATCHLIST: {
                long _id = db.insert(TVGuideContract.MyWatchListEntry.TABLE_NAME, null, contentValues);
                if (_id > 0) {
                    insertedUri = TVGuideContract.MyWatchListEntry.buildMyWatchlistUri(_id);
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case MY_REMINDERS: {
                long _id = db.insert(TVGuideContract.MyRemindersEntry.TABLE_NAME, null, contentValues);
                if (_id > 0) {
                    insertedUri = TVGuideContract.MyRemindersEntry.buildMyRemindersUri(_id);
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri : " + uri);
        }

        Context context = getContext();
        if (context != null) {
            context.getContentResolver().notifyChange(uri, null);
        }

        return insertedUri;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = mTVGuideDBHelper.getWritableDatabase();
        String tableName = getTableName(uri);
        int insertedCount = 0;

        try {
            db.beginTransaction();
            for (ContentValues cv : values) {
                long _id = db.insert(tableName, null, cv);
                if (_id > 0) {
                    insertedCount++;
                }
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }

        Context context = getContext();
        if (context != null) {
            context.getContentResolver().notifyChange(uri, null);
        }

        return insertedCount;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mTVGuideDBHelper.getWritableDatabase();
        int rowDeleted;
        String tableName = getTableName(uri);

        rowDeleted = db.delete(tableName, selection, selectionArgs);
        Context context = getContext();
        if (context != null && rowDeleted > 0) {
            context.getContentResolver().notifyChange(uri, null);
        }
        return rowDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mTVGuideDBHelper.getWritableDatabase();
        int rowUpdated;
        String tableName = getTableName(uri);

        rowUpdated = db.update(tableName, contentValues, selection, selectionArgs);
        Context context = getContext();
        if (context != null && rowUpdated > 0) {
            context.getContentResolver().notifyChange(uri, null);
        }
        return rowUpdated;
    }

    private static UriMatcher buildUriMatcher() {
        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(TVGuideContract.CONTENT_AUTHORITY, TVGuideContract.PATH_CHANNELS, CHANNEL);
        uriMatcher.addURI(TVGuideContract.CONTENT_AUTHORITY, TVGuideContract.PATH_PROGRAMS, PROGRAM);
        uriMatcher.addURI(TVGuideContract.CONTENT_AUTHORITY, TVGuideContract.PATH_CHANNEL_PROGRAMS, CHANNEL_PROGRAM);
        uriMatcher.addURI(TVGuideContract.CONTENT_AUTHORITY, TVGuideContract.PATH_MY_CHANNELS, MY_CHANNELS);
        uriMatcher.addURI(TVGuideContract.CONTENT_AUTHORITY, TVGuideContract.PATH_MY_WATCHLIST, MY_WATCHLIST);
        uriMatcher.addURI(TVGuideContract.CONTENT_AUTHORITY, TVGuideContract.PATH_MY_REMINDERS, MY_REMINDERS);

        return uriMatcher;
    }

    private String getTableName(Uri uri) {
        final int matchUri = sUriMatcher.match(uri);

        switch (matchUri) {
            case CHANNEL:
                return TVGuideContract.ChannelEntry.TABLE_NAME;
            case PROGRAM:
                return TVGuideContract.ProgramEntry.TABLE_NAME;
            case CHANNEL_PROGRAM:
                return TVGuideContract.ChannelProgramEntry.TABLE_NAME;
            case MY_CHANNELS:
                return TVGuideContract.MyChannelEntry.TABLE_NAME;
            case MY_WATCHLIST:
                return TVGuideContract.MyWatchListEntry.TABLE_NAME;
            case MY_REMINDERS:
                return TVGuideContract.MyRemindersEntry.TABLE_NAME;
            default:
                throw new UnsupportedOperationException("Unknown uri : " + uri);
        }
    }
}
