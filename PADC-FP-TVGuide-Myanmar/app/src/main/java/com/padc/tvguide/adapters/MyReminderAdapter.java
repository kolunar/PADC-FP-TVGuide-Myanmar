package com.padc.tvguide.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.padc.tvguide.R;
import com.padc.tvguide.TVGuideApp;
import com.padc.tvguide.data.vos.ProgramVO;
import com.padc.tvguide.views.holders.ProgramViewHolder;

import java.util.List;

/**
 * Created by Administrator's user on 09-Sep-16.
 */
public class MyReminderAdapter extends RecyclerView.Adapter<ProgramViewHolder> {

    private LayoutInflater inflater;
    private List<ProgramVO> mProgramList;
    private ProgramViewHolder.ControllerProgramItem mControllerProgramItem;

    public MyReminderAdapter(List<ProgramVO> programList, ProgramViewHolder.ControllerProgramItem controllerProgramItem) {
        inflater = LayoutInflater.from(TVGuideApp.getContext());
        mProgramList = programList;
        mControllerProgramItem = controllerProgramItem;
    }

    @Override
    public ProgramViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.view_item_my_reminder,parent,false);
        ProgramViewHolder programVH = new ProgramViewHolder(view, mControllerProgramItem);
        return programVH;
    }

    @Override
    public void onBindViewHolder(ProgramViewHolder holder, int position) {
        holder.bindData(mProgramList.get(position));
    }

    @Override
    public int getItemCount() {
        return mProgramList.size();
    }
}
