package com.padc.mmchannels.data.agents.retrofit;

import com.padc.mmchannels.data.reponses.ChannelDetailsResponse;
import com.padc.mmchannels.data.reponses.ChannelListResponse;
import com.padc.mmchannels.data.reponses.ProgramDetailsResponse;
import com.padc.mmchannels.utils.TVGuideConstants;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by user on 9/24/2016.
 */
public interface TVGuideApi {
    @FormUrlEncoded
    @POST(TVGuideConstants.API_GET_CHANNEL_LIST)
    Call<ChannelListResponse> loadChannels(
            @Field(TVGuideConstants.PARAM_ACCESS_TOKEN) String param);

    @FormUrlEncoded
    @POST(TVGuideConstants.API_GET_CHANNEL_DETAILS)
    Call<ChannelDetailsResponse> loadChannelDetails(
            @Field(TVGuideConstants.PARAM_ACCESS_TOKEN) String access_token,
            @Field(TVGuideConstants.PARAM_CHANNEL_ID) Long channel_id);

    @FormUrlEncoded
    @POST(TVGuideConstants.API_GET_PROGRAM_DETAILS)
    Call<ProgramDetailsResponse> loadProgramDetails(
            @Field(TVGuideConstants.PARAM_ACCESS_TOKEN) String access_token,
            @Field(TVGuideConstants.PARAM_PROGRAM_ID) Long program_id);
}
