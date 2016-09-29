package com.padc.tvguide.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.padc.tvguide.R;
import com.padc.tvguide.adapters.DayPagerAdapter;
import com.padc.tvguide.data.vos.ChannelDetailsVO;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 9/10/2016.
 */
public class DayPagerFragment extends BaseFragment {

    @BindView(R.id.tl_days)
    TabLayout tlDays;

    @BindView(R.id.pager_days)
    ViewPager pagerDays;

    private DayPagerAdapter mDayPagerAdapter;
    private static ChannelDetailsVO mChannelDetailsVO;

    public static DayPagerFragment newInstance(ChannelDetailsVO channelDetails) {
        mChannelDetailsVO = channelDetails;
        DayPagerFragment fragment = new DayPagerFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDayPagerAdapter = new DayPagerAdapter(getActivity().getSupportFragmentManager());
        mDayPagerAdapter.addTab(ChannelDetailFragment.newInstance(mChannelDetailsVO), "Today");
        mDayPagerAdapter.addTab(ChannelDetailFragment.newInstance(mChannelDetailsVO), "Sun");
        mDayPagerAdapter.addTab(ChannelDetailFragment.newInstance(mChannelDetailsVO), "Mon");
        mDayPagerAdapter.addTab(ChannelDetailFragment.newInstance(mChannelDetailsVO), "Tue");
        mDayPagerAdapter.addTab(ChannelDetailFragment.newInstance(mChannelDetailsVO), "Wed");
        mDayPagerAdapter.addTab(ChannelDetailFragment.newInstance(mChannelDetailsVO), "Thu");
        mDayPagerAdapter.addTab(ChannelDetailFragment.newInstance(mChannelDetailsVO), "Fri");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pager_days, container, false);
        ButterKnife.bind(this, rootView);

        pagerDays.setAdapter(mDayPagerAdapter);
        pagerDays.setOffscreenPageLimit(mDayPagerAdapter.getCount());

        tlDays.setupWithViewPager(pagerDays);

        return rootView;
    }
}