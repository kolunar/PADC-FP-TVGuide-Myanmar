package com.padc.tvguide.utils;

/**
 * Created by user on 9/24/2016.
 */
public class TVGuideConstants {
//    public static final String TVGUIDE_BASE_URL = "http://aungpyaephyo.xyz/padc-fp/tv-guide/";
    public static final String TVGUIDE_BASE_URL = "http://128.199.252.46/";

    public static final String API_GET_CHANNEL_LIST = "GetChannelList.php";
    public static final String API_GET_CHANNEL_DETAILS = "GetChannelDetails.php";
    public static final String API_GET_PROGRAM_DETAILS = "GetProgramDetails.php";

    public static final String PARAM_ACCESS_TOKEN = "access_token";
    public static final String PARAM_CHANNEL_ID = "channel_id";
    public static final String PARAM_PROGRAM_ID = "program_id";

    public static final String ACCESS_TOKEN = "b002c7e1a528b7cb460933fc2875e916";

    //Loader ID
    public static final int CHANNEL_LIST_LOADER = 1;
    public static final int MY_CHANNEL_LIST_LOADER = 2;
    public static final int CHANNEL_DETAIL_LOADER = 200; // 200 to 206 for 7 week days Fragments {"Sat", "Sun", ... , "Fri"}
    public static final int RESPONSE_CODE_FAILED = 401;

    public static final String ENCRYPT_MD5 = "MD5";
}
