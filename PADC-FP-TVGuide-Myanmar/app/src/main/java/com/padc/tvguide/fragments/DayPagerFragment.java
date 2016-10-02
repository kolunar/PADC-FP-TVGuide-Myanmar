package com.padc.tvguide.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.padc.tvguide.R;
import com.padc.tvguide.TVGuideApp;
import com.padc.tvguide.adapters.DayPagerAdapter;
import com.padc.tvguide.data.vos.ChannelDetailsVO;
import com.padc.tvguide.data.vos.ChannelProgramVO;
import com.padc.tvguide.utils.DateTimeUtils;

import java.util.List;

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
        String[] days = DateTimeUtils.getDaysOfWeek();

        if(savedInstanceState == null)
            Toast.makeText(TVGuideApp.getContext(), "DayPagerFragment.onCreate():savedInstanceState==null ", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(TVGuideApp.getContext(), "DayPagerFragment.onCreate():savedInstanceState!=null ", Toast.LENGTH_LONG).show();

        List<ChannelProgramVO> channelProgramVOList;
        for (int i = 0, size = days.length; i < size; i++) {
            channelProgramVOList = mChannelDetailsVO.getChannelProgramsByAirDay(days[i], false);
            mDayPagerAdapter.addTab(
                    ChannelDetailFragment.newInstance(channelProgramVOList, days[i], mChannelDetailsVO.getChannel().getChannel_id()),
                    (i == 0 ? "Today" : days[i])
            );
            Log.e(TVGuideApp.TAG, "ChannelDetailsVO.getChannelProgramsByAirDay.size : " + channelProgramVOList);
        }
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