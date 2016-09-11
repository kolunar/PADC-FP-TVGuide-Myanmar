package com.padc.tvguide.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.padc.tvguide.R;
import com.padc.tvguide.TVGuideApp;
import com.padc.tvguide.views.holders.ProgramViewHolder;

/**
 * Created by Administrator's user on 09-Sep-16.
 */
public class ProgramAdapter extends RecyclerView.Adapter<ProgramViewHolder> {

    private LayoutInflater inflater;

    public ProgramAdapter() {
        inflater = LayoutInflater.from(TVGuideApp.getContext());
    }

    @Override
    public ProgramViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.view_item_program,parent,false);
        ProgramViewHolder programVH = new ProgramViewHolder(view);
        return programVH;
    }

    @Override
    public void onBindViewHolder(ProgramViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
