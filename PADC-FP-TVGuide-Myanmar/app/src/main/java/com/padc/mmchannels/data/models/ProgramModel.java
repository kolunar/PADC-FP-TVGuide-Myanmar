package com.padc.mmchannels.data.models;

import android.util.Log;

import com.padc.mmchannels.MMChannelsApp;
import com.padc.mmchannels.data.vos.ProgramDetailsVO;
import com.padc.mmchannels.data.vos.ProgramVO;
import com.padc.mmchannels.events.DataEvent;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by user on 9/24/2016.
 */
public class ProgramModel extends BaseModel {
    public static final String BROADCAST_DATA_LOADED = "BROADCAST_DATA_LOADED";

    private static final int INIT_DATA_AGENT_OFFLINE = 1;
    private static final int INIT_DATA_AGENT_HTTP_URL_CONNECTION = 2;
    private static final int INIT_DATA_AGENT_OK_HTTP = 3;
    private static final int INIT_DATA_AGENT_RETROFIT = 4;

    private static ProgramModel objInstance;

    private List<ProgramVO> mProgramList;
    private ProgramDetailsVO mProgramDetails;

    private ProgramModel() {
        super();
        mProgramList = new ArrayList<>();
        mProgramDetails = new ProgramDetailsVO();
//        initDataAgent(INIT_DATA_AGENT_RETROFIT);
//        loadPrograms();
//        loadProgramDetails();
    }

    public static ProgramModel getInstance() {
        if (objInstance == null) {
            objInstance = new ProgramModel();
        }
        return objInstance;
    }

//    public void loadPrograms() {
//        dataAgent.loadPrograms();
//    }

    public void loadProgramDetails(long program_id) {
        dataAgent.loadProgramDetails(program_id);
    }

    public List<ProgramVO> getProgramList() {
        return mProgramList;
    }

    public ProgramDetailsVO getProgramDetails() {
        return mProgramDetails;
    }

    public void setStoredData(List<ProgramVO> programList){
        mProgramList = programList;
    }

    public void notifyProgramDetailsLoaded(ProgramDetailsVO programDetails) {
        Log.e(MMChannelsApp.TAG, "ProgramModel.notifyProgramDetailsLoaded.programDetails:");
        mProgramDetails = programDetails;

        //keep the data in persistent layer.
        ProgramDetailsVO.saveProgramDetails(mProgramDetails);

        broadcastProgramDetailsLoadedWithEventBus();
//        broadcastChannelDetailsLoadedWithLocalBroadcastManager();
    }

    public void notifyErrorInLoadingProgramDetails(String message) {
        broadcastConnectionErrorWithEventBus();
        Log.e(MMChannelsApp.TAG, "ProgramModel.notifyErrorInLoadingProgramDetails.message:" + message);
    }

    private void broadcastConnectionErrorWithEventBus() {
        EventBus.getDefault().post(new DataEvent.ProgramDataLoadedErrorEvent("extra-in-broadcast"));
    }

    private void broadcastProgramDetailsLoadedWithEventBus() {
        EventBus.getDefault().post(new DataEvent.ProgramDetailsLoadedEvent("extra-in-broadcast", mProgramDetails));
    }
}
