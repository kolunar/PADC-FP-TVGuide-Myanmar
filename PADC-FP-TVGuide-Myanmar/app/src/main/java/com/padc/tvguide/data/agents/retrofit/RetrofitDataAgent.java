package com.padc.tvguide.data.agents.retrofit;

import android.util.Log;

import com.padc.tvguide.TVGuideApp;
import com.padc.tvguide.data.agents.TVGuideDataAgent;
import com.padc.tvguide.data.models.ChannelModel;
import com.padc.tvguide.data.models.ProgramModel;
import com.padc.tvguide.data.reponses.ChannelDetailsResponse;
import com.padc.tvguide.data.reponses.ChannelListResponse;
import com.padc.tvguide.data.reponses.ProgramDetailsResponse;
import com.padc.tvguide.utils.CommonInstances;
import com.padc.tvguide.utils.TVGuideConstants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by user on 9/24/2016.
 */
public class RetrofitDataAgent implements TVGuideDataAgent {

    private static RetrofitDataAgent objInstance;

    private final TVGuideApi theApi;

    private RetrofitDataAgent() {
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TVGuideConstants.TVGUIDE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(CommonInstances.getGsonInstance()))
                .client(okHttpClient)
                .build();

        theApi = retrofit.create(TVGuideApi.class);
    }

    public static RetrofitDataAgent getInstance() {
        if (objInstance == null) {
            objInstance = new RetrofitDataAgent();
        }
        return objInstance;
    }

    @Override
    public void loadChannels() {
        Call<ChannelListResponse> loadChannelCall = theApi.loadChannels(TVGuideConstants.ACCESS_TOKEN);
        loadChannelCall.enqueue(new Callback<ChannelListResponse>() {
            @Override
            public void onResponse(Call<ChannelListResponse> call, Response<ChannelListResponse> response) {
                Log.e(TVGuideApp.TAG, "RetrofitDataAgent.loadChannels().onResponse");
                ChannelListResponse channelListResponse = response.body();
                if(channelListResponse == null) {
                    Log.e(TVGuideApp.TAG, "RetrofitDataAgent.loadChannels().onResponse.channelListResponse==null");
                    ChannelModel.getInstance().notifyErrorInLoadingChannels(response.message());
                }
                else {
                    Log.e(TVGuideApp.TAG, "RetrofitDataAgent.loadChannels().onResponse.channelListResponse.getChannelList():"+channelListResponse.getChannelList());
                    ChannelModel.getInstance().notifyChannelsLoaded(channelListResponse.getChannelList());
                }
            }

            @Override
            public void onFailure(Call<ChannelListResponse> call, Throwable t) {
                ChannelModel.getInstance().notifyErrorInLoadingChannels(t.getMessage());
            }
        });
    }

    @Override
    public void loadChannelDetails(int channel_id) {
        Call<ChannelDetailsResponse> loadChannelDetailCall = theApi.loadChannelDetails(TVGuideConstants.ACCESS_TOKEN, channel_id);
        loadChannelDetailCall.enqueue(new Callback<ChannelDetailsResponse>() {
            @Override
            public void onResponse(Call<ChannelDetailsResponse> call, Response<ChannelDetailsResponse> response) {
                Log.e(TVGuideApp.TAG, "RetrofitDataAgent.loadChannelDetails().onResponse");
                ChannelDetailsResponse channelDetailsResponse = response.body();
                if(channelDetailsResponse == null) {
                    Log.e(TVGuideApp.TAG, "RetrofitDataAgent.loadChannelDetails().onResponse.channelDetailsResponse==null");
                    ChannelModel.getInstance().notifyErrorInLoadingChannelDetails(response.message());
                }
                else {
                    Log.e(TVGuideApp.TAG, "RetrofitDataAgent.loadChannelDetails().onResponse.channelDetailsResponse.getChannelDetails():"+channelDetailsResponse.getChannelDetails());
                    ChannelModel.getInstance().notifyChannelDetailsLoaded(channelDetailsResponse.getChannelDetails());
                }
            }

            @Override
            public void onFailure(Call<ChannelDetailsResponse> call, Throwable t) {
                ChannelModel.getInstance().notifyErrorInLoadingChannelDetails(t.getMessage());
            }
        });
    }

    @Override
    public void loadProgramDetails(int program_id) {
        Call<ProgramDetailsResponse> loadChannelDetailCall = theApi.loadProgramDetails(TVGuideConstants.ACCESS_TOKEN, program_id);
        loadChannelDetailCall.enqueue(new Callback<ProgramDetailsResponse>() {
            @Override
            public void onResponse(Call<ProgramDetailsResponse> call, Response<ProgramDetailsResponse> response) {
                Log.e(TVGuideApp.TAG, "RetrofitDataAgent.loadProgramDetails().onResponse");
                ProgramDetailsResponse programDetailsResponse = response.body();
                if(programDetailsResponse == null) {
                    Log.e(TVGuideApp.TAG, "RetrofitDataAgent.loadProgramDetails().onResponse.programDetailsResponse==null");
                    ProgramModel.getInstance().notifyErrorInLoadingProgramDetails(response.message());
                }
                else {
                    Log.e(TVGuideApp.TAG, "RetrofitDataAgent.loadProgramDetails().onResponse.programDetailsResponse.getProgramDetails():"+programDetailsResponse.getProgramDetails());
                    ProgramModel.getInstance().notifyProgramDetailsLoaded(programDetailsResponse.getProgramDetails());
                }
            }

            @Override
            public void onFailure(Call<ProgramDetailsResponse> call, Throwable t) {
                ProgramModel.getInstance().notifyErrorInLoadingProgramDetails(t.getMessage());
            }
        });
    }
}
