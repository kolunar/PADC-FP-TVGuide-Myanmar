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

    public static DayPagerFragment newInstance() {
        DayPagerFragment fragment = new DayPagerFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDayPagerAdapter = new DayPagerAdapter(getActivity().getSupportFragmentManager());
        mDayPagerAdapter.addTab(ChannelDetailFragment.newInstance(), "Today");
        mDayPagerAdapter.addTab(ChannelDetailFragment.newInstance(), "Sun");
        mDayPagerAdapter.addTab(ChannelDetailFragment.newInstance(), "Mon");
        mDayPagerAdapter.addTab(ChannelDetailFragment.newInstance(), "Tue");
        mDayPagerAdapter.addTab(ChannelDetailFragment.newInstance(), "Wed");
        mDayPagerAdapter.addTab(ChannelDetailFragment.newInstance(), "Thu");
        mDayPagerAdapter.addTab(ChannelDetailFragment.newInstance(), "Fri");
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