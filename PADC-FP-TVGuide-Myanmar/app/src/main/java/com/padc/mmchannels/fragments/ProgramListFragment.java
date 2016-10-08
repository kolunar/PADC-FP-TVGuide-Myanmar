package com.padc.mmchannels.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.padc.mmchannels.R;
import com.padc.mmchannels.MMChannelsApp;
import com.padc.mmchannels.adapters.ProgramAdapter;
import com.padc.mmchannels.data.vos.ProgramVO;
import com.padc.mmchannels.views.holders.ProgramViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 9/10/2016.
 */
public class ProgramListFragment extends BaseFragment {

    @BindView(R.id.rv_program_list)
    RecyclerView rvPrograms;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout srLayout;

    private ProgramAdapter mProgramAdapter;
    private ProgramViewHolder.ControllerProgramItem controllerProgramItem;

    public static ProgramListFragment newInstance() {
        ProgramListFragment fragment = new ProgramListFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        controllerProgramItem = (ProgramViewHolder.ControllerProgramItem) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_program_list, container, false);
        ButterKnife.bind(this, rootView);

        List<ProgramVO> programList = getProgramList();
        if(rvPrograms != null) {
            rvPrograms.removeAllViews();
        }
        mProgramAdapter = new ProgramAdapter(programList, controllerProgramItem);
        rvPrograms.setAdapter(mProgramAdapter);
        rvPrograms.setLayoutManager(new LinearLayoutManager(MMChannelsApp.getContext(), LinearLayoutManager.VERTICAL, false));
        srLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        srLayout.setRefreshing(false);
                    }
                }, 1500);
            }
        });

        return rootView;
    }

    public void notifyDataSetChanged() {
        mProgramAdapter.notifyDataSetChanged();
    }

    private List<ProgramVO> getProgramList(){
        List<ProgramVO> dummy = new ArrayList<ProgramVO>();
        String[] programListArray = getResources().getStringArray(R.array.dummy_program_list);
        dummy.add(new ProgramVO(0, "Detail information is not available", ""));
        for (int i = 0; i < programListArray.length; i++) {
            dummy.add(new ProgramVO(i+1, programListArray[i], ""));
        }
        return dummy;
    }
}
