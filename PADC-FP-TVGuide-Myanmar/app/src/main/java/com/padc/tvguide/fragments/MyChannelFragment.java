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

import com.padc.tvguide.R;
import com.padc.tvguide.TVGuideApp;
import com.padc.tvguide.adapters.MyChannelAdapter;
import com.padc.tvguide.data.models.MyChannelModel;
import com.padc.tvguide.data.persistence.TVGuideContract.MyChannelEntry;
import com.padc.tvguide.data.vos.ChannelVO;
import com.padc.tvguide.data.vos.MyChannelVO;
import com.padc.tvguide.utils.TVGuideConstants;
import com.padc.tvguide.views.holders.MyChannelViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class MyChannelFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    @BindView(R.id.rv_my_channel)
    RecyclerView rvChannels;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout srLayout;

    private MyChannelAdapter mChannelAdapter;
    private MyChannelViewHolder.ControllerMyChannelItem mControllerMyChannelItem ;

    public static MyChannelFragment newInstance() {
        MyChannelFragment fragment = new MyChannelFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mControllerMyChannelItem = (MyChannelViewHolder.ControllerMyChannelItem) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_channel, container, false);
        ButterKnife.bind(this, rootView);

//        List<MyChannelVO> channelList = getChannelList();
        List<MyChannelVO> myChannelList = new ArrayList<>();
        if(rvChannels != null) {
            rvChannels.removeAllViews();
        }
        mChannelAdapter = new MyChannelAdapter(myChannelList, mControllerMyChannelItem);
        rvChannels.setAdapter(mChannelAdapter);
//        int gridColumnSpanCount = 1;
//        rvChannels.setLayoutManager(new GridLayoutManager(getContext(), gridColumnSpanCount));
        rvChannels.setLayoutManager(new LinearLayoutManager(TVGuideApp.getContext(), LinearLayoutManager.VERTICAL, false));
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().getSupportLoaderManager().initLoader(TVGuideConstants.MY_CHANNEL_LIST_LOADER, null, this);

    }

    int mUserID = 0;

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String selection = MyChannelEntry.COLUMN_USER_ID + "=?";
        String[] selectionArgs = {String.valueOf(mUserID)};
        return new CursorLoader(getContext(),
                MyChannelEntry.CONTENT_URI,
                null,
                selection,
                selectionArgs,
                MyChannelEntry.COLUMN_SORT_ORDER + " ASC");
    }

    List<MyChannelVO> mMyChannelVOList;

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
//        hideProgressBar();
        List<MyChannelVO> myChannelList = new ArrayList<>();
        if (data != null && data.moveToFirst()) {
            do {
                MyChannelVO myChannel = MyChannelVO.parseFromCursor(data);
                myChannel.setChannelVO(ChannelVO.loadChannelByID(myChannel.getChannel_id()));
                myChannelList.add(myChannel);
            } while (data.moveToNext());
        }

        Log.e(TVGuideApp.TAG, "Retrieved channelList.size : " + myChannelList.size());
        mMyChannelVOList = myChannelList;
        mChannelAdapter.setNewData(myChannelList);

        MyChannelModel.getInstance().setStoredData(myChannelList);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public void notifyDataSetChanged() {
        mChannelAdapter.notifyDataSetChanged();
    }

    private List<MyChannelVO> getChannelList(){
        List<MyChannelVO> dummy = new ArrayList<MyChannelVO>();
        String[] channelListArray = getResources().getStringArray(R.array.dummy_my_channel);
        for (int i = 0; i < channelListArray.length; i++) {
//            dummy.add(new MyChannelVO(i, channelListArray[i], ""));
        }
        return dummy;
    }
}
