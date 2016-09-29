package com.padc.tvguide.data.vos;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.google.gson.annotations.SerializedName;
import com.padc.tvguide.TVGuideApp;
import com.padc.tvguide.data.persistence.TVGuideContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 9/10/2016.
 */
public class ChannelDetailsVO {

    private ChannelVO channel;
    private ArrayList<CategoryVO> categories;
    @SerializedName("programs")
    private ArrayList<ChannelProgramVO> channel_programs;

    public ChannelVO getChannel() {
        return channel;
    }

    public void setChannel(ChannelVO channel) {
        this.channel = channel;
    }

    public ArrayList<CategoryVO> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<CategoryVO> categories) {
        this.categories = categories;
    }

    public ArrayList<ChannelProgramVO> getChannel_programs() {
        return channel_programs;
    }

    public void setChannel_programs(ArrayList<ChannelProgramVO> channel_programs) {
        this.channel_programs = channel_programs;
    }


    public ChannelDetailsVO(int channel_id, String channel_name, String channel_icon) {
        this.channel = new ChannelVO(channel_id, channel_name, channel_icon);
        this.categories = new ArrayList<>();
        this.channel_programs = new ArrayList<>();
    }

    public static void saveChannelDetails(List<ChannelDetailsVO> channelDetailList) {
        Context context = TVGuideApp.getContext();
        ContentValues[] channelCVs = new ContentValues[channelDetailList.size()];
        for (int index = 0; index < channelDetailList.size(); index++) {
            ChannelDetailsVO channel = channelDetailList.get(index);
//            channelCVs[index] = channel.parseToContentValues();

            //Bulk insert into channel_details.
            //TODO::ChannelVO.saveChannelDetails(channel.getId(), channel.getDetails());
        }

        //Bulk insert into channels.
        int insertedCount = context.getContentResolver().bulkInsert(TVGuideContract.ChannelEntry.CONTENT_URI, channelCVs);

        Log.d(TVGuideApp.TAG, "Bulk inserted into channel table : " + insertedCount);
    }

}
