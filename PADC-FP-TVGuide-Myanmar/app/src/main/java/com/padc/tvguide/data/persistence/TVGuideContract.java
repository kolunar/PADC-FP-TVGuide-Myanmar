package com.padc.tvguide.data.persistence;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import com.padc.tvguide.TVGuideApp;

/**
 * Created by user on 9/24/2016.
 */
public class TVGuideContract {
    public static final String CONTENT_AUTHORITY = TVGuideApp.class.getPackage().getName();
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_CHANNELS = "channels";

    public static final class ChannelEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_CHANNELS).build();

        public static final String DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CHANNELS;

        public static final String ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CHANNELS;

        public static final String TABLE_NAME = "channels";

        public static final String COLUMN_CHANNEL_ID = "channel_id";
        public static final String COLUMN_CHANNEL_NAME = "channel_name";
        public static final String COLUMN_CHANNEL_DESC = "channel_desc";
        public static final String COLUMN_CHANNEL_ICON = "channel_icon";
        public static final String COLUMN_CHANNEL_BANNER = "channel_banner";
        public static final String COLUMN_CHANNEL_START_TIME = "start_time";
        public static final String COLUMN_CHANNEL_END_TIME = "end_time";
        public static final String COLUMN_CHANNEL_SORT_ORDER = "sort_order";
        public static final String COLUMN_CHANNEL_ROW_TIMESTAMP = "row_timestamp";
        public static final String COLUMN_CHANNEL_RECORD_STATUS = "record_status";

        public static Uri buildChannelUri(long id) {
            //content://com.padc.tvguide/channels/1
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
