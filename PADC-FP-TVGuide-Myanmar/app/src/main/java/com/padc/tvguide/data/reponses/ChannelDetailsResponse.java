package com.padc.tvguide.data.reponses;

import com.google.gson.annotations.SerializedName;
import com.padc.tvguide.data.vos.ChannelDetailsVO;

/**
 * Created by user on 9/24/2016.
 */
public class ChannelDetailsResponse {
    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("channel_details")
    private ChannelDetailsVO channelDetails;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public ChannelDetailsVO getChannelDetails() {
        return channelDetails;
    }
}
