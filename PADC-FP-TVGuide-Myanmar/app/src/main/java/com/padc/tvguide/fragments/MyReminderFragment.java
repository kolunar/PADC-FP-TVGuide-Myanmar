package com.padc.tvguide.fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.padc.tvguide.R;
import com.padc.tvguide.TVGuideApp;
import com.padc.tvguide.activities.HomeActivity;
import com.padc.tvguide.activities.ProgramDetailActivity;
import com.padc.tvguide.adapters.MyReminderAdapter;
import com.padc.tvguide.data.persistence.TVGuideContract.MyRemindersEntry;
import com.padc.tvguide.data.vos.ChannelProgramVO;
import com.padc.tvguide.data.vos.MyReminderVO;
import com.padc.tvguide.data.vos.ProgramVO;
import com.padc.tvguide.dialogs.TimePrefixDialog;
import com.padc.tvguide.utils.TVGuideConstants;
import com.padc.tvguide.views.holders.MyReminderViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by user on 9/10/2016.
 */
public class MyReminderFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    @BindView(R.id.rv_my_reminder)
    RecyclerView rvChannelDetail;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout srLayout;


    @BindView(R.id.ll_no_data_my_reminder)
    LinearLayout llDataStatus;

    private ControllerFragment mControllerFragment;

    @OnClick(R.id.ll_no_data_my_reminder)
    public void onTapNoDataStatus(){
        mControllerFragment.onSwitchFragment(HomeActivity.MY_CHANNEL_FRAGMENT);
    }

    private void checkNoDataStatus(boolean hasData){
        if(hasData)
            showNoDataStatus();
        else
            hideNoDataStatus();
    }

    private void showNoDataStatus(){
        llDataStatus.setVisibility(View.VISIBLE);
    }

    private void hideNoDataStatus(){
        llDataStatus.setVisibility(View.GONE);
    }

    private MyReminderAdapter mMyReminderAdapter;
    private MyReminderViewHolder.ControllerMyReminderItem mControllerMyReminderItemItem;

    public static MyReminderFragment newInstance() {
        MyReminderFragment fragment = new MyReminderFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof HomeActivity) {
            mControllerMyReminderItemItem = (MyReminderViewHolder.ControllerMyReminderItem) context;
            mControllerFragment = (ControllerFragment) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_reminder, container, false);
        ButterKnife.bind(this, rootView);

//        List<ProgramVO> programList = getProgramList();
        List<MyReminderVO> myReminderVOs = new ArrayList<>();
        if(rvChannelDetail != null) {
            rvChannelDetail.removeAllViews();
        }
        mMyReminderAdapter = new MyReminderAdapter(myReminderVOs, new MyReminderViewHolder.ControllerMyReminderItem() {
            @Override
            public void onTapMyReminder(MyReminderVO myReminder) {
				Intent intent = ProgramDetailActivity.newIntent(myReminder.getChannelProgramVO().getProgram());//"Program Detail");
				startActivity(intent);
            }

            @Override
            public void onTapDeleteMyReminder(MyReminderVO myReminder) {
				MyReminderVO.deleteMyReminder(0, myReminder.getChannel_program_id());
            }

            @Override
            public void onTapSetMyReminder(MyReminderVO myReminder) {
                TimePrefixDialog.newInstance(myReminder.getChannel_program_id(), myReminder.getTime_ahead())
                        .show(getChildFragmentManager(), "TimePrefixPicker");

            }
        });
        rvChannelDetail.setAdapter(mMyReminderAdapter);
        rvChannelDetail.setLayoutManager(new LinearLayoutManager(TVGuideApp.getContext(), LinearLayoutManager.VERTICAL, false));
        srLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mControllerFragment.onSwipeRefresh(HomeActivity.MY_REMINDER_FRAGMENT);
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().getSupportLoaderManager().initLoader(TVGuideConstants.MY_REMINDER_LOADER, null, this);
    }

    int mUserID = 0;

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String selection = MyRemindersEntry.COLUMN_USER_ID + "=?";
        String[] selectionArgs = {String.valueOf(mUserID)};
        return new CursorLoader(getContext(),
                MyRemindersEntry.CONTENT_URI,
                null,
                selection,
                selectionArgs,
                MyRemindersEntry.COLUMN_SORT_ORDER + " ASC");
    }

    List<MyReminderVO> mMyReminderVOList;

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        List<MyReminderVO> myMyReminderVOs = new ArrayList<>();
        if (data != null && data.moveToFirst()) {
            do {
                MyReminderVO myReminderVO = MyReminderVO.parseFromCursor(data);
                myReminderVO.setChannelProgramVO(ChannelProgramVO.loadChannelProgramByID(myReminderVO.getChannel_program_id()));
                myReminderVO.getChannelProgramVO().setProgram(ProgramVO.loadProgramByID(myReminderVO.getChannelProgramVO().getProgram_id()));
                myMyReminderVOs.add(myReminderVO);
            } while (data.moveToNext());
        }

        Log.e(TVGuideApp.TAG, "MyChannelFragment.onLoadFinished.Retrieved MyChannelList.size : " + myMyReminderVOs.size());
        mMyReminderVOList = myMyReminderVOs;
        mMyReminderAdapter.setNewData(myMyReminderVOs);

//        ProgramModel.getInstance().setStoredData(myWatchListVOs);

        checkNoDataStatus(mMyReminderVOList.size() < 1);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public void notifyDataSetChanged() {
        mMyReminderAdapter.notifyDataSetChanged();
    }

    private List<ProgramVO> getProgramList(){
        List<ProgramVO> dummy = new ArrayList<ProgramVO>();
        String[] programListArray = getResources().getStringArray(R.array.dummy_my_reminder_list);
        for (int i = 0; i < programListArray.length; i++) {
            dummy.add(new ProgramVO(i, programListArray[i], ""));
        }
        return dummy;
    }
}
