package com.padc.mmchannels.events;

import com.padc.mmchannels.data.vos.ChannelDetailsVO;
import com.padc.mmchannels.data.vos.ChannelVO;
import com.padc.mmchannels.data.vos.MyChannelVO;
import com.padc.mmchannels.data.vos.ProgramDetailsVO;
import com.padc.mmchannels.data.vos.ProgramVO;

import java.util.List;

/**
 * Created by user on 9/10/2016.
 */
public class DataEvent {

    public static class ChannelDataLoadedEvent {

        private String extraMessage;
        private List<ChannelVO> channelList;

        public ChannelDataLoadedEvent(String extraMessage, List<ChannelVO> channelList) {
            this.extraMessage = extraMessage;
            this.channelList = channelList;
        }

        public String getExtraMessage() {
            return extraMessage;
        }

        public List<ChannelVO> getChannelList() {
            return channelList;
        }
    }

    public static class MyChannelDataLoadedEvent {

        private String extraMessage;
        private List<MyChannelVO> myChannelList;

        public MyChannelDataLoadedEvent(String extraMessage, List<MyChannelVO> myChannelList) {
            this.extraMessage = extraMessage;
            this.myChannelList = myChannelList;
        }

        public String getExtraMessage() {
            return extraMessage;
        }

        public List<MyChannelVO> getChannelList() {
            return myChannelList;
        }
    }

    public static class ChannelDetailsLoadedEvent {

        private String extraMessage;
        private ChannelDetailsVO channelDetails;

        public ChannelDetailsLoadedEvent(String extraMessage, ChannelDetailsVO channelDetails) {
            this.extraMessage = extraMessage;
            this.channelDetails = channelDetails;
        }

        public String getExtraMessage() {
            return extraMessage;
        }

        public ChannelDetailsVO getChannelDetails() {
            return channelDetails;
        }
    }

    public static class ChannelDataLoadedErrorEvent {

        private String extraMessage;

        public ChannelDataLoadedErrorEvent(String extraMessage) {
            this.extraMessage = extraMessage;
        }

        public String getExtraMessage() {
            return extraMessage;
        }
    }

    public static class ProgramDataLoadedEvent {

        private String extraMessage;
        private List<ProgramVO> programList;

        public ProgramDataLoadedEvent(String extraMessage, List<ProgramVO> programList) {
            this.extraMessage = extraMessage;
            this.programList = programList;
        }

        public String getExtraMessage() {
            return extraMessage;
        }

        public List<ProgramVO> getProgramList() {
            return programList;
        }
    }

    public static class ProgramDetailsLoadedEvent {

        private String extraMessage;
        private ProgramDetailsVO programDetails;

        public ProgramDetailsLoadedEvent(String extraMessage, ProgramDetailsVO programDetails) {
            this.extraMessage = extraMessage;
            this.programDetails = programDetails;
        }

        public String getExtraMessage() {
            return extraMessage;
        }

        public ProgramDetailsVO getProgramDetails() {
            return programDetails;
        }
    }

    public static class ProgramDataLoadedErrorEvent {

        private String extraMessage;

        public ProgramDataLoadedErrorEvent(String extraMessage) {
            this.extraMessage = extraMessage;
        }

        public String getExtraMessage() {
            return extraMessage;
        }
    }

    public static class RefreshUserLoginStatusEvent {

    }
}
