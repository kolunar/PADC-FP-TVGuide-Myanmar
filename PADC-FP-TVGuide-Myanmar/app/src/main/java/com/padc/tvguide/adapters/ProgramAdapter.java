package com.padc.tvguide.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.padc.tvguide.R;
import com.padc.tvguide.TVGuideApp;
import com.padc.tvguide.data.vos.ProgramVO;
import com.padc.tvguide.views.holders.ProgramDetailViewHolder;
import com.padc.tvguide.views.holders.ProgramViewHolder;

import java.util.List;

/**
 * Created by Administrator's user on 09-Sep-16.
 */
public class ProgramAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater inflater;
    private List<ProgramVO> mProgramList;
    private ProgramViewHolder.ControllerProgramItem mControllerProgramItem;

    public ProgramAdapter(List<ProgramVO> programList, ProgramViewHolder.ControllerProgramItem controllerProgramItem) {
        inflater = LayoutInflater.from(TVGuideApp.getContext());
        mProgramList = programList;
        mControllerProgramItem = controllerProgramItem;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder programVH = null;
        switch (viewType) {
            case 0:
                view = inflater.inflate(R.layout.view_item_program_detail,parent,false);
                programVH = new ProgramDetailViewHolder(view);
                break;
            case 2:
            default:
                view = inflater.inflate(R.layout.view_item_program,parent,false);
                programVH = new ProgramViewHolder(view, mControllerProgramItem);
                break;
        }
        return programVH;
    }

    @Override
    public int getItemViewType(int position) {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        return position;//position % 2 * 2;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (position) {
            case 0:
                ((ProgramDetailViewHolder)holder).bindData(mProgramList.get(position));
                break;
            case 1:
            case 2:
            default:
                ((ProgramViewHolder)holder).bindData(mProgramList.get(position));
                break;
        }
//        holder.bindData(mProgramList.get(position));
    }

    @Override
    public int getItemCount() {
        return mProgramList.size();
    }

}
