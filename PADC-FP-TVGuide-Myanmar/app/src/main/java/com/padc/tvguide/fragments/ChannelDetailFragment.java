package com.padc.tvguide.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.padc.tvguide.R;
import com.padc.tvguide.TVGuideApp;
import com.padc.tvguide.activities.ProgramDetailActivity;
import com.padc.tvguide.activities.ProgramParentActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 9/10/2016.
 */
public class ChannelDetailFragment extends BaseFragment {

    @BindView(R.id.cv_channel)
    CardView cvChannel;

    public static ChannelDetailFragment newInstance() {
        ChannelDetailFragment fragment = new ChannelDetailFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_channel_detail, container, false);
        ButterKnife.bind(this, rootView);

        cvChannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = ProgramDetailActivity.newIntent("Program Detail");
                startActivity(intent);
            }
        });
//        Toast.makeText(TVGuideApp.getContext(), "ChannelDetailFragment:onCreateView(): ", Toast.LENGTH_LONG).show();
        return rootView;
    }
}
