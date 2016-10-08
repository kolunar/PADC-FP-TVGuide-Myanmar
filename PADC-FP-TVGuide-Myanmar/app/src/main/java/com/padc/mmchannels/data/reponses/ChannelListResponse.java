package com.padc.mmchannels.data.reponses;

import com.google.gson.annotations.SerializedName;
import com.padc.mmchannels.data.vos.ChannelVO;

import java.util.ArrayList;

/**
 * Created by user on 9/24/2016.
 */
public class ChannelListResponse {
    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("channel_list")
    private ArrayList<ChannelVO> channelList;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<ChannelVO> getChannelList() {
        return channelList;
    }
}
