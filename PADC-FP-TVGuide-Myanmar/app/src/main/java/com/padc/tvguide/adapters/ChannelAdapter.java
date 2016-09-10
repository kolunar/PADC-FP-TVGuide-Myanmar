package com.padc.tvguide.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.padc.tvguide.R;
import com.padc.tvguide.TVGuideApp;
import com.padc.tvguide.data.vos.ChannelVO;
import com.padc.tvguide.views.holders.ChannelViewHolder;

import java.util.List;

/**
 * Created by user on 9/10/2016.
 */
public class ChannelAdapter extends RecyclerView.Adapter<ChannelViewHolder> {

    private LayoutInflater mInflater;
    private List<ChannelVO> mChannelList;
    private ChannelViewHolder.ControllerChannelItem mControllerChannelItem;

    public ChannelAdapter(List<ChannelVO> channelList, ChannelViewHolder.ControllerChannelItem controllerChannelItem) {
        mInflater = LayoutInflater.from(TVGuideApp.getContext());
        mChannelList = channelList;
        mControllerChannelItem = controllerChannelItem;
    }

    @Override
    public ChannelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.view_item_channel, parent, false);
        return new ChannelViewHolder(itemView, mControllerChannelItem);
    }

    @Override
    public void onBindViewHolder(ChannelViewHolder holder, int position) {
        holder.bindData(mChannelList.get(position));
    }

    @Override
    public int getItemCount() {
        return mChannelList.size();
    }
}
