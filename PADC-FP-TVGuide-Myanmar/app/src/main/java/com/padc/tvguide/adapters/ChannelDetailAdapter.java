package com.padc.tvguide.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.padc.tvguide.R;
import com.padc.tvguide.TVGuideApp;
import com.padc.tvguide.data.vos.ChannelProgramVO;
import com.padc.tvguide.views.holders.ChannelProgramViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator's user on 09-Sep-16.
 */
public class ChannelDetailAdapter extends RecyclerView.Adapter<ChannelProgramViewHolder> {

    private LayoutInflater inflater;
    private List<ChannelProgramVO> mChannelProgramList;
    private ChannelProgramViewHolder.ControllerChannelProgramItem mControllerChannelProgramItem;

    public ChannelDetailAdapter(List<ChannelProgramVO> channel_programs, ChannelProgramViewHolder.ControllerChannelProgramItem controllerChannelProgramItem) {
        inflater = LayoutInflater.from(TVGuideApp.getContext());
        if (channel_programs != null) {
            this.mChannelProgramList = channel_programs;
        } else {
            this.mChannelProgramList = new ArrayList<>();
        }
        mControllerChannelProgramItem = controllerChannelProgramItem;
    }

    @Override
    public ChannelProgramViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.view_item_program,parent,false);
        ChannelProgramViewHolder programVH = new ChannelProgramViewHolder(view, mControllerChannelProgramItem);
        return programVH;
    }

    @Override
    public void onBindViewHolder(ChannelProgramViewHolder holder, int position) {
        holder.bindData(mChannelProgramList.get(position));
    }

    @Override
    public int getItemCount() {
        return mChannelProgramList.size();
    }

    public void setNewData(List<ChannelProgramVO> newChannelProgramList, Boolean isNetwork, Boolean isPersistence) {
        if(isNetwork){
            Log.e(TVGuideApp.TAG, "ChannelDetailAdapter.setNewData(isNetwork).newChannelProgramList.size(): " + newChannelProgramList.size());
        }
        else if(isPersistence){
            Log.e(TVGuideApp.TAG, "ChannelDetailAdapter.setNewData(isPersistence).newChannelProgramList.size(): " + newChannelProgramList.size());
        }
        mChannelProgramList = newChannelProgramList;
        notifyDataSetChanged();
    }
}
