package com.padc.tvguide.fragments;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
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
public class ChannelListFragment extends BaseFragment {

    @BindView(R.id.rv_channels)
    RecyclerView rvChannels;

    private ChannelAdapter mChannelAdapter;
    private ChannelViewHolder.ControllerChannelItem controllerChannelItem;

    public static ChannelListFragment newInstance() {
        ChannelListFragment fragment = new ChannelListFragment();
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
        mChannelAdapter = new ChannelAdapter(channelList, controllerChannelItem);
        rvChannels.setAdapter(mChannelAdapter);

        int gridColumnSpanCount = 2;
        rvChannels.setLayoutManager(new GridLayoutManager(getContext(), gridColumnSpanCount));

        return rootView;
    }

    private List<ChannelVO> getChannelList(){
        List<ChannelVO> dummy = new ArrayList<ChannelVO>();
        dummy.add(new ChannelVO(1, "", ""));
        dummy.add(new ChannelVO(2, "", ""));
        dummy.add(new ChannelVO(3, "", ""));
        dummy.add(new ChannelVO(4, "", ""));
        dummy.add(new ChannelVO(5, "", ""));
        dummy.add(new ChannelVO(6, "", ""));
        dummy.add(new ChannelVO(7, "", ""));
        dummy.add(new ChannelVO(8, "", ""));
        dummy.add(new ChannelVO(9, "", ""));
        dummy.add(new ChannelVO(10, "", ""));
        dummy.add(new ChannelVO(11, "", ""));
        dummy.add(new ChannelVO(12, "", ""));
        return dummy;
    }
}
