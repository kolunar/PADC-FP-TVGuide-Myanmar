package com.padc.tvguide.fragments;

import android.content.Context;
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
import com.padc.tvguide.adapters.ChannelDetailAdapter;
import com.padc.tvguide.adapters.MyWatchListAdapter;
import com.padc.tvguide.data.persistence.TVGuideContract.MyWatchListEntry;
import com.padc.tvguide.data.vos.MyWatchListVO;
import com.padc.tvguide.data.vos.ProgramVO;
import com.padc.tvguide.utils.TVGuideConstants;
import com.padc.tvguide.views.holders.MyWatchListViewHolder;
import com.padc.tvguide.views.holders.ProgramViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by user on 9/10/2016.
 */
public class MyWatchListFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    @BindView(R.id.rv_my_watchlist)
    RecyclerView rvChannelDetail;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout srLayout;

    @BindView(R.id.ll_no_data_my_watchlist)
    LinearLayout llDataStatus;

    private ControllerFragment mControllerFragment;

    @OnClick(R.id.ll_no_data_my_watchlist)
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

    private MyWatchListAdapter mMyWatchListAdapter;
    private MyWatchListViewHolder.ControllerMyWatchListItem mControllerMyWatchListItem;

    public static MyWatchListFragment newInstance() {
        MyWatchListFragment fragment = new MyWatchListFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof HomeActivity) {
            mControllerMyWatchListItem = (MyWatchListViewHolder.ControllerMyWatchListItem) context;
            mControllerFragment = (ControllerFragment) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_watchlist, container, false);
        ButterKnife.bind(this, rootView);

//        List<ProgramVO> programList = getProgramList();
        List<MyWatchListVO> myWatchListVOs = new ArrayList<>();
        if(rvChannelDetail != null) {
            rvChannelDetail.removeAllViews();
        }
        mMyWatchListAdapter = new MyWatchListAdapter(myWatchListVOs, mControllerMyWatchListItem);
        rvChannelDetail.setAdapter(mMyWatchListAdapter);
        rvChannelDetail.setLayoutManager(new LinearLayoutManager(TVGuideApp.getContext(), LinearLayoutManager.VERTICAL, false));
        srLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mControllerFragment.onSwipeRefresh(HomeActivity.MY_WATCHLIST_FRAGMENT);
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
        getActivity().getSupportLoaderManager().initLoader(TVGuideConstants.MY_WATCHLIST_LOADER, null, this);
    }

    int mUserID = 0;

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String selection = MyWatchListEntry.COLUMN_USER_ID + "=?";
        String[] selectionArgs = {String.valueOf(mUserID)};
        return new CursorLoader(getContext(),
                MyWatchListEntry.CONTENT_URI,
                null,
                selection,
                selectionArgs,
                MyWatchListEntry.COLUMN_SORT_ORDER + " ASC");
    }

    List<MyWatchListVO> myWatchListVOList;

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        List<MyWatchListVO> myWatchListVOs = new ArrayList<>();
        if (data != null && data.moveToFirst()) {
            do {
                MyWatchListVO myWatchListVO = MyWatchListVO.parseFromCursor(data);
                myWatchListVO.setProgram(ProgramVO.loadProgramByID(myWatchListVO.getProgram_id()));
                myWatchListVO.getProgram().setMyWatchList(MyWatchListVO.getIsMyWatchList(0, myWatchListVO.getProgram_id()));
                myWatchListVOs.add(myWatchListVO);
            } while (data.moveToNext());
        }

        Log.e(TVGuideApp.TAG, "MyChannelFragment.onLoadFinished.Retrieved MyChannelList.size : " + myWatchListVOs.size());
        myWatchListVOList = myWatchListVOs;
        mMyWatchListAdapter.setNewData(myWatchListVOs);

//        ProgramModel.getInstance().setStoredData(myWatchListVOs);

        checkNoDataStatus(myWatchListVOs.size() < 1);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public void notifyDataSetChanged() {
        mMyWatchListAdapter.notifyDataSetChanged();
    }

    private List<MyWatchListVO> getProgramList(){
        List<MyWatchListVO> dummy = new ArrayList<MyWatchListVO>();
        String[] programListArray = getResources().getStringArray(R.array.dummy_my_watch_list);
        for (int i = 0; i < programListArray.length; i++) {
            dummy.add(new MyWatchListVO(i, programListArray[i], ""));
        }
        return dummy;
    }
}
