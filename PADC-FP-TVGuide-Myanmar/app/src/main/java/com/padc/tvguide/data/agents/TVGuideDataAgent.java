package com.padc.tvguide.data.agents;

/**
 * Created by user on 9/24/2016.
 */
public interface TVGuideDataAgent {
    void loadChannels();
    void loadChannelDetails(int channel_id);
    void loadProgramDetails(int program_id);
}
