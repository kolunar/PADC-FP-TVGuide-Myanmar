package com.padc.mmchannels.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.padc.mmchannels.R;
import com.padc.mmchannels.MMChannelsApp;
import com.padc.mmchannels.data.vos.ChannelVO;
import com.padc.mmchannels.views.holders.ChannelViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 9/10/2016.
 */
public class ChannelAdapter extends RecyclerView.Adapter<ChannelViewHolder> {

    private LayoutInflater mInflater;
    private List<ChannelVO> mChannelList;
    private ChannelViewHolder.ControllerChannelItem mControllerChannelItem;

    public ChannelAdapter(List<ChannelVO> channelList, ChannelViewHolder.ControllerChannelItem controllerChannelItem) {
        mInflater = LayoutInflater.from(MMChannelsApp.getContext());
        if (channelList != null) {
            this.mChannelList = channelList;
        } else {
            this.mChannelList = new ArrayList<>();
        }
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

    public void setNewData(List<ChannelVO> newChannelList) {
        mChannelList = newChannelList;
        notifyDataSetChanged();
    }
}
