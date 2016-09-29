package com.padc.tvguide.events;

import com.padc.tvguide.data.vos.ChannelDetailsVO;
import com.padc.tvguide.data.vos.ChannelVO;

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

    public static class RefreshUserLoginStatusEvent {

    }
}
