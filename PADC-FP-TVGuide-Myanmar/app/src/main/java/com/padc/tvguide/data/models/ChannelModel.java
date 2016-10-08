package com.padc.tvguide.data.models;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.padc.tvguide.TVGuideApp;
import com.padc.tvguide.data.agents.OkHttpDataAgent;
import com.padc.tvguide.data.agents.retrofit.RetrofitDataAgent;
import com.padc.tvguide.data.vos.ChannelDetailsVO;
import com.padc.tvguide.data.vos.ChannelVO;
import com.padc.tvguide.events.DataEvent;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by user on 9/24/2016.
 */
public class ChannelModel extends BaseModel {
    public static final String BROADCAST_DATA_LOADED = "BROADCAST_DATA_LOADED";

    private static final int INIT_DATA_AGENT_OFFLINE = 1;
    private static final int INIT_DATA_AGENT_HTTP_URL_CONNECTION = 2;
    private static final int INIT_DATA_AGENT_OK_HTTP = 3;
    private static final int INIT_DATA_AGENT_RETROFIT = 4;

    private static ChannelModel objInstance;

    private List<ChannelVO> mChannelList;
    private ChannelDetailsVO mChannelDetails;

    private ChannelModel() {
        super();
        mChannelList = new ArrayList<>();
        mChannelDetails = new ChannelDetailsVO(1, "no name", "");
//        initDataAgent(INIT_DATA_AGENT_RETROFIT);
//        loadChannels();
//        loadChannelDetails();
    }

    public static ChannelModel getInstance() {
        if (objInstance == null) {
            objInstance = new ChannelModel();
        }
        return objInstance;
    }

    public void loadChannels() {
        dataAgent.loadChannels();
    }

    public void loadChannelDetails(long channel_id) {
        dataAgent.loadChannelDetails(channel_id);
    }

    public List<ChannelVO> getChannelList() {
        return mChannelList;
    }

    public ChannelDetailsVO getChannelDetails() {
        return mChannelDetails;
    }

    public void setStoredData(List<ChannelVO> channelList){
        mChannelList = channelList;
    }

    public void notifyChannelsLoaded(List<ChannelVO> channelList) {
        Log.e(TVGuideApp.TAG, "ChannelModel.notifyChannelsLoaded.channelList.size:" + channelList.size());
        //Notify that the data is ready - using LocalBroadcast
        mChannelList = channelList;

        //keep the data in persistent layer.
        ChannelVO.saveChannels(mChannelList);

        broadcastChannelLoadedWithEventBus();
//        broadcastChannelLoadedWithLocalBroadcastManager();
    }

    public void notifyChannelDetailsLoaded(ChannelDetailsVO channelDetails) {
        Log.e(TVGuideApp.TAG, "ChannelModel.notifyChannelDetailsLoaded.channelDetails.channel:" + channelDetails.getChannel().getChannel_name());
        mChannelDetails = channelDetails;

        //keep the data in persistent layer.
        ChannelDetailsVO.saveChannelDetails(mChannelDetails);

        broadcastChannelDetailsLoadedWithEventBus();
//        broadcastChannelDetailsLoadedWithLocalBroadcastManager();
    }

    public void notifyErrorInLoadingChannels(String message) {
        broadcastConnectionErrorWithEventBus();
		Log.e(TVGuideApp.TAG, "ChannelModel.notifyErrorInLoadingChannels.message:" + message);
    }

    public void notifyErrorInLoadingChannelDetails(String message) {
        broadcastConnectionErrorWithEventBus();
        Log.e(TVGuideApp.TAG, "ChannelModel.notifyErrorInLoadingChannelDetails.message:" + message);
    }

    private void broadcastChannelLoadedWithLocalBroadcastManager() {
		Log.e(TVGuideApp.TAG, "broadcastAttractionLoadedWithLocalBroadcastManager");
        Intent intent = new Intent(BROADCAST_DATA_LOADED);
        intent.putExtra("key-for-extra", "extra-in-broadcast");
        LocalBroadcastManager.getInstance(TVGuideApp.getContext()).sendBroadcast(intent);
    }

    private void broadcastConnectionErrorWithEventBus() {
        EventBus.getDefault().post(new DataEvent.ChannelDataLoadedErrorEvent("extra-in-broadcast"));
    }

    private void broadcastChannelLoadedWithEventBus() {
        EventBus.getDefault().post(new DataEvent.ChannelDataLoadedEvent("extra-in-broadcast", mChannelList));
    }

    private void broadcastChannelDetailsLoadedWithEventBus() {
        EventBus.getDefault().post(new DataEvent.ChannelDetailsLoadedEvent("extra-in-broadcast", mChannelDetails));
    }
}
