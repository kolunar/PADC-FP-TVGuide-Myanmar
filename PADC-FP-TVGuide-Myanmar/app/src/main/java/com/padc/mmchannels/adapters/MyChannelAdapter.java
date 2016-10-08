package com.padc.mmchannels.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.padc.mmchannels.R;
import com.padc.mmchannels.MMChannelsApp;
import com.padc.mmchannels.data.vos.MyChannelVO;
import com.padc.mmchannels.views.holders.MyChannelViewHolder;

import java.util.List;

/**
 * Created by user on 9/10/2016.
 */
public class MyChannelAdapter extends RecyclerView.Adapter<MyChannelViewHolder> {

    private LayoutInflater mInflater;
    private List<MyChannelVO> mMyChannelList;
    private MyChannelViewHolder.ControllerMyChannelItem mControllerMyChannelItem;

    public MyChannelAdapter(List<MyChannelVO> channelList, MyChannelViewHolder.ControllerMyChannelItem controllerMyChannelItem) {
        mInflater = LayoutInflater.from(MMChannelsApp.getContext());
        mMyChannelList = channelList;
        mControllerMyChannelItem = controllerMyChannelItem;
    }

    @Override
    public MyChannelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.view_item_my_channel, parent, false);
        return new MyChannelViewHolder(itemView, mControllerMyChannelItem);
    }

    @Override
    public void onBindViewHolder(MyChannelViewHolder holder, int position) {
        holder.bindData(mMyChannelList.get(position));
    }

    @Override
    public int getItemCount() {
        return mMyChannelList.size();
    }

    public void setNewData(List<MyChannelVO> newMyChannelList) {
        mMyChannelList = newMyChannelList;
        notifyDataSetChanged();
        Log.e(MMChannelsApp.TAG, "MyChannelAdapter.setNewData.newMyChannelList.size : " + newMyChannelList.size());
    }
}
