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
        dummy.add(new ChannelVO(1, "4 TV", ""));
        dummy.add(new ChannelVO(2, "4 Comedy", ""));
        dummy.add(new ChannelVO(3, "4 Edu", ""));
        dummy.add(new ChannelVO(4, "4 Info", ""));
        dummy.add(new ChannelVO(5, "4 Ladies", ""));
        dummy.add(new ChannelVO(6, "4 Sports", ""));
        dummy.add(new ChannelVO(7, "4 Tunes", ""));
        dummy.add(new ChannelVO(8, "5 Cartoons", ""));
        dummy.add(new ChannelVO(9, "5 Movies", ""));
        dummy.add(new ChannelVO(10, "5 Plus", ""));
        dummy.add(new ChannelVO(11, "5 Special", ""));
        dummy.add(new ChannelVO(12, "Buddha tv", ""));
        return dummy;
    }
}
