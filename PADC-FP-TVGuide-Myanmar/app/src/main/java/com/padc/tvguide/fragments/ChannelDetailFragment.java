package com.padc.tvguide.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.padc.tvguide.R;
import com.padc.tvguide.TVGuideApp;
import com.padc.tvguide.activities.ChannelDetailActivity;
import com.padc.tvguide.activities.ProgramDetailActivity;
import com.padc.tvguide.activities.ProgramParentActivity;
import com.padc.tvguide.adapters.ChannelDetailAdapter;
import com.padc.tvguide.adapters.ProgramAdapter;
import com.padc.tvguide.data.vos.ProgramVO;
import com.padc.tvguide.views.holders.ProgramViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 9/10/2016.
 */
public class ChannelDetailFragment extends BaseFragment {

    @BindView(R.id.rv_channel_detail)
    RecyclerView rvChannelDetail;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout srLayout;

//    @BindView(R.id.cv_channel)
//    CardView cvChannel;

    private ChannelDetailAdapter mProgramAdapter;
    private ProgramViewHolder.ControllerProgramItem controllerProgramItem;

    public static ChannelDetailFragment newInstance() {
        ChannelDetailFragment fragment = new ChannelDetailFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ChannelDetailActivity)
            controllerProgramItem = (ProgramViewHolder.ControllerProgramItem) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_channel_detail, container, false);
        ButterKnife.bind(this, rootView);

/*        cvChannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = ProgramDetailActivity.newIntent("Program Detail");
                startActivity(intent);
            }
        });*/

        List<ProgramVO> programList = getProgramList();
        if(rvChannelDetail != null) {
            rvChannelDetail.removeAllViews();
        }
        mProgramAdapter = new ChannelDetailAdapter(programList, controllerProgramItem);
        rvChannelDetail.setAdapter(mProgramAdapter);
        rvChannelDetail.setLayoutManager(new LinearLayoutManager(TVGuideApp.getContext(), LinearLayoutManager.VERTICAL, false));
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
        String[] programListArray = getResources().getStringArray(R.array.dummy_channel_detail_list);
        for (int i = 0; i < programListArray.length; i++) {
            dummy.add(new ProgramVO(i, programListArray[i], ""));
        }
        return dummy;
    }
}
