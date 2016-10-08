package com.padc.mmchannels.data.agents;

/**
 * Created by user on 9/24/2016.
 */
public interface TVGuideDataAgent {
    void loadChannels();
    void loadChannelDetails(long channel_id);
    void loadProgramDetails(long program_id);
}
