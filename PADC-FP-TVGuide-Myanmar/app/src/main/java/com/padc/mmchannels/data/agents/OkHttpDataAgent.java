package com.padc.mmchannels.data.agents;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by user on 9/24/2016.
 */
public class OkHttpDataAgent implements TVGuideDataAgent {

    private static OkHttpDataAgent objInstance;

    private OkHttpClient mHttpClient;

    private OkHttpDataAgent() {
        mHttpClient = new OkHttpClient.Builder() //1.
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();
    }

    public static OkHttpDataAgent getInstance() {
        if (objInstance == null) {
            objInstance = new OkHttpDataAgent();
        }
        return objInstance;
    }

    @Override
    public void loadChannels() {

    }

    @Override
    public void loadChannelDetails(long channel_id) {

    }

    @Override
    public void loadProgramDetails(long program_id) {

    }
}
