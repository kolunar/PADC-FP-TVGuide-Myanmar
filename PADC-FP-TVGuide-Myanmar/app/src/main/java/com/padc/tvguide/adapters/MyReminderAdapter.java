package com.padc.tvguide.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.padc.tvguide.R;
import com.padc.tvguide.TVGuideApp;
import com.padc.tvguide.data.vos.MyReminderVO;
import com.padc.tvguide.views.holders.MyReminderViewHolder;

import java.util.List;

/**
 * Created by Administrator's user on 09-Sep-16.
 */
public class MyReminderAdapter extends RecyclerView.Adapter<MyReminderViewHolder> {

    private LayoutInflater inflater;
    private List<MyReminderVO> mMyWatchListVOs;
    private MyReminderViewHolder.ControllerMyReminderItem mControllerProgramItem;

    public MyReminderAdapter(List<MyReminderVO> programList, MyReminderViewHolder.ControllerMyReminderItem controllerProgramItem) {
        inflater = LayoutInflater.from(TVGuideApp.getContext());
        mMyWatchListVOs = programList;
        mControllerProgramItem = controllerProgramItem;
    }

    @Override
    public MyReminderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.view_item_my_reminder,parent,false);
        MyReminderViewHolder programVH = new MyReminderViewHolder(view, mControllerProgramItem);
        return programVH;
    }

    @Override
    public void onBindViewHolder(MyReminderViewHolder holder, int position) {
        holder.bindData(mMyWatchListVOs.get(position));
    }

    @Override
    public int getItemCount() {
        return mMyWatchListVOs.size();
    }

    public void setNewData(List<MyReminderVO> newMyReminderVOs) {
        mMyWatchListVOs = newMyReminderVOs;
        notifyDataSetChanged();
        Log.e(TVGuideApp.TAG, "MyWatchListAdapter.setNewData.newMyWatchListVOs.size : " + newMyReminderVOs.size());
    }
}
