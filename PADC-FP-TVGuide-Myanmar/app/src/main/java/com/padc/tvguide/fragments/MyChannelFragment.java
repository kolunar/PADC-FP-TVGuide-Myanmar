package com.padc.tvguide.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.padc.tvguide.R;
import com.padc.tvguide.adapters.ChannelAdapter;
import com.padc.tvguide.data.vos.ChannelVO;
import com.padc.tvguide.views.holders.ChannelViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class MyChannelFragment extends BaseFragment {

    @BindView(R.id.rv_channel_list)
    RecyclerView rvChannels;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout srLayout;

    private ChannelAdapter mChannelAdapter;
    private ChannelViewHolder.ControllerChannelItem controllerChannelItem;

    public static MyChannelFragment newInstance() {
        MyChannelFragment fragment = new MyChannelFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        controllerChannelItem = (ChannelViewHolder.ControllerChannelItem) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_channel_list, container, false);
        ButterKnife.bind(this, rootView);

        List<ChannelVO> channelList = getChannelList();
        if(rvChannels != null) {
            rvChannels.removeAllViews();
        }
        mChannelAdapter = new ChannelAdapter(channelList, controllerChannelItem);
        rvChannels.setAdapter(mChannelAdapter);

        int gridColumnSpanCount = 2;
        rvChannels.setLayoutManager(new GridLayoutManager(getContext(), gridColumnSpanCount));
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
        mChannelAdapter.notifyDataSetChanged();
    }

    private List<ChannelVO> getChannelList(){
        List<ChannelVO> dummy = new ArrayList<ChannelVO>();
        String[] channelListArray = getResources().getStringArray(R.array.dummy_my_channel);
        for (int i = 0; i < channelListArray.length; i++) {
            dummy.add(new ChannelVO(i, channelListArray[i], ""));
        }
        return dummy;
    }
}
