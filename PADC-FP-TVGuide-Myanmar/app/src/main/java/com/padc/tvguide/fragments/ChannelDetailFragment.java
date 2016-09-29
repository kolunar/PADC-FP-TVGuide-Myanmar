package com.padc.tvguide.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.padc.tvguide.R;
import com.padc.tvguide.TVGuideApp;
import com.padc.tvguide.activities.ChannelDetailActivity;
import com.padc.tvguide.activities.ProgramDetailActivity;
import com.padc.tvguide.activities.ProgramParentActivity;
import com.padc.tvguide.adapters.ChannelDetailAdapter;
import com.padc.tvguide.adapters.ProgramAdapter;
import com.padc.tvguide.data.models.ChannelModel;
import com.padc.tvguide.data.vos.ChannelDetailsVO;
import com.padc.tvguide.data.vos.ChannelProgramVO;
import com.padc.tvguide.data.vos.ChannelVO;
import com.padc.tvguide.data.vos.ProgramVO;
import com.padc.tvguide.events.DataEvent;
import com.padc.tvguide.views.holders.ChannelProgramViewHolder;
import com.padc.tvguide.views.holders.ProgramViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by user on 9/10/2016.
 */
public class ChannelDetailFragment extends BaseFragment {

    @BindView(R.id.rv_channel_detail)
    RecyclerView rvChannelDetail;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout srLayout;

//    @BindView(R.id.cv_channel)
//    CardView cvChannel;

    private ChannelDetailAdapter mChannelDetailsAdapter;
    private ChannelProgramViewHolder.ControllerChannelProgramItem controllerChannelProgramItem;
    private static ChannelDetailsVO mChannelDetailsVO;

/*    public static ChannelDetailFragment newInstance() {
        ChannelDetailFragment fragment = new ChannelDetailFragment();
        return fragment;
    }*/

    public static ChannelDetailFragment newInstance(ChannelDetailsVO channelDetails) {
        Log.e(TVGuideApp.TAG, "ChannelDetailFragment.newInstance.channelDetails.getChannel_programs().size:" + channelDetails.getChannel_programs().size());
        mChannelDetailsVO = channelDetails;
        ChannelDetailFragment fragment = new ChannelDetailFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ChannelDetailActivity)
            controllerChannelProgramItem = (ChannelProgramViewHolder.ControllerChannelProgramItem) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_channel_detail, container, false);
        ButterKnife.bind(this, rootView);

/*        cvChannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = ProgramDetailActivity.newIntent("Program Detail");
                startActivity(intent);
            }
        });*/
//        ChannelDetailsVO channelDetails = ChannelModel.getInstance().getChannelDetails();
        List<ChannelProgramVO> programList = mChannelDetailsVO.getChannel_programs();
//        List<ProgramVO> programList = getProgramList();
/*        if(rvChannelDetail != null) {
            rvChannelDetail.removeAllViews();
        }*/
        mChannelDetailsAdapter = new ChannelDetailAdapter(programList, controllerChannelProgramItem);
        rvChannelDetail.setAdapter(mChannelDetailsAdapter);
        rvChannelDetail.setLayoutManager(new LinearLayoutManager(TVGuideApp.getContext(), LinearLayoutManager.VERTICAL, false));
        srLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        srLayout.setRefreshing(false);
                    }
                }, 1500);
            }
        });

        return rootView;
    }

    public void notifyDataSetChanged() {
        mChannelDetailsAdapter.notifyDataSetChanged();
    }

    private List<ProgramVO> getProgramList(){
        List<ProgramVO> dummy = new ArrayList<ProgramVO>();
        String[] programListArray = getResources().getStringArray(R.array.dummy_channel_detail_list);
        for (int i = 0; i < programListArray.length; i++) {
            dummy.add(new ProgramVO(i, programListArray[i], ""));
        }
        return dummy;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus eventBus = EventBus.getDefault();
        if (!eventBus.isRegistered(this)) {
            eventBus.register(this);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus eventBus = EventBus.getDefault();
        eventBus.unregister(this);
    }

    public void onEventMainThread(DataEvent.ChannelDetailsLoadedEvent event) {
        Log.e(TVGuideApp.TAG, "ChannelDetailFragment.onEventMainThread().ChannelDetailsLoadedEvent");
        String extra = event.getExtraMessage();
//        Toast.makeText(getContext(), "ChannelDetailFragment:onEventMainThread:Extra : " + extra, Toast.LENGTH_SHORT).show();

        //List<ChannelVO> newChannelList = ChannelModel.getInstance().getChannelList();
        ChannelDetailsVO newChannelDetails = event.getChannelDetails();
        mChannelDetailsAdapter.setNewData(newChannelDetails.getChannel_programs());
    }
}
