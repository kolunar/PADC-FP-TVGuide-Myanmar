package com.padc.tvguide.data.models;

import android.util.Log;

import com.padc.tvguide.TVGuideApp;
import com.padc.tvguide.data.vos.MyChannelVO;
import com.padc.tvguide.events.DataEvent;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by user on 9/24/2016.
 */
public class MyChannelModel extends BaseModel {
    public static final String BROADCAST_DATA_LOADED = "BROADCAST_DATA_LOADED";

    private static final int INIT_DATA_AGENT_OFFLINE = 1;
    private static final int INIT_DATA_AGENT_HTTP_URL_CONNECTION = 2;
    private static final int INIT_DATA_AGENT_OK_HTTP = 3;
    private static final int INIT_DATA_AGENT_RETROFIT = 4;

    private static MyChannelModel objInstance;

    private List<MyChannelVO> mMyChannelList;

    private MyChannelModel() {
        super();
        mMyChannelList = new ArrayList<>();
//        initDataAgent(INIT_DATA_AGENT_RETROFIT);
//        loadChannels();
//        loadChannelDetails();
    }

    public static MyChannelModel getInstance() {
        if (objInstance == null) {
            objInstance = new MyChannelModel();
        }
        return objInstance;
    }

    public void loadMyChannels() {
        dataAgent.loadChannels();
    }

    public List<MyChannelVO> getMyChannelList() {
        return mMyChannelList;
    }

    public void setStoredData(List<MyChannelVO> myChannelList){
        mMyChannelList = myChannelList;
    }

    public void notifyMyChannelsLoaded(List<MyChannelVO> myChannelList) {
        Log.e(TVGuideApp.TAG, "MyChannelModel.notifyMyChannelsLoaded.myChannelList.size:" + myChannelList.size());
        //Notify that the data is ready - using LocalBroadcast
        mMyChannelList = myChannelList;

        //keep the data in persistent layer.
        MyChannelVO.saveMyChannels(mMyChannelList);

        broadcastMyChannelLoadedWithEventBus();
    }

    public void notifyErrorInLoadingMyChannels(String message) {
        broadcastConnectionErrorWithEventBus();
		Log.e(TVGuideApp.TAG, "ChannelModel.notifyErrorInLoadingChannels.message:" + message);
    }

    private void broadcastConnectionErrorWithEventBus() {
        EventBus.getDefault().post(new DataEvent.ChannelDataLoadedErrorEvent("extra-in-broadcast"));
    }

    private void broadcastMyChannelLoadedWithEventBus() {
        EventBus.getDefault().post(new DataEvent.MyChannelDataLoadedEvent("extra-in-broadcast", mMyChannelList));
    }
}
