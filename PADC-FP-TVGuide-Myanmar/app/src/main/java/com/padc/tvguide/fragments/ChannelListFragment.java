package com.padc.tvguide.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.padc.tvguide.R;
import com.padc.tvguide.TVGuideApp;
import com.padc.tvguide.activities.HomeActivity;
import com.padc.tvguide.adapters.ChannelAdapter;
import com.padc.tvguide.data.models.ChannelModel;
import com.padc.tvguide.data.persistence.TVGuideContract;
import com.padc.tvguide.data.vos.ChannelVO;
import com.padc.tvguide.events.DataEvent;
import com.padc.tvguide.utils.TVGuideConstants;
import com.padc.tvguide.views.holders.ChannelViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * A placeholder fragment containing a simple view.
 */
public class ChannelListFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    @BindView(R.id.rv_channel_list)
    RecyclerView rvChannels;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout srLayout;

    private ChannelAdapter mChannelAdapter;
    private ChannelViewHolder.ControllerChannelItem controllerChannelItem;

    public static ChannelListFragment newInstance() {
        ChannelListFragment fragment = new ChannelListFragment();
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().getSupportLoaderManager().initLoader(TVGuideConstants.CHANNEL_DETAIL_LOADER, null, this);
        if(TVGuideApp.hasInternet){
            ChannelModel.getInstance().loadChannels();
        }
    }

    /*    private BroadcastReceiver mDataLoadedBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //TODO instructions when the new data is ready.
            String extra = intent.getStringExtra("key-for-extra");
            Toast.makeText(getContext(), "Extra : " + extra, Toast.LENGTH_SHORT).show();

            List<ChannelVO> newAttractionList = ChannelModel.getInstance().getChannelList();
            mChannelAdapter.setNewData(newAttractionList);
        }
    };*/

    @Override
    public void onStart() {
        super.onStart();
//        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mDataLoadedBroadcastReceiver, new IntentFilter(ChannelModel.BROADCAST_DATA_LOADED));

        EventBus eventBus = EventBus.getDefault();
        if (!eventBus.isRegistered(this)) {
            eventBus.register(this);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
//        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mDataLoadedBroadcastReceiver);

        EventBus eventBus = EventBus.getDefault();
        eventBus.unregister(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof HomeActivity)
            controllerChannelItem = (ChannelViewHolder.ControllerChannelItem) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_channel_list, container, false);
        ButterKnife.bind(this, rootView);

//      List<ChannelVO> channelList = getChannelList();
        List<ChannelVO> channelList = new ArrayList<>();
//        List<ChannelVO> channelList = ChannelModel.getInstance().getChannelList();
/*        if(rvChannels != null) {
            rvChannels.removeAllViews();
        }*/
        Log.e(TVGuideApp.TAG, "ChannelListFragment.onCreateView.channelList.size:" + channelList.size());
        mChannelAdapter = new ChannelAdapter(channelList, controllerChannelItem);
        rvChannels.setAdapter(mChannelAdapter);

        int gridColumnSpanCount = 2;
        rvChannels.setLayoutManager(new GridLayoutManager(getContext(), gridColumnSpanCount));

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

    private List<ChannelVO> getChannelList(){
        List<ChannelVO> dummy = new ArrayList<ChannelVO>();
        String[] channelListArray = getResources().getStringArray(R.array.dummy_channel_list);
        for (int i = 0; i < channelListArray.length; i++) {
            dummy.add(new ChannelVO(i, channelListArray[i], ""));
        }
        return dummy;
    }

    public void notifyDataSetChanged() {
        Log.e(TVGuideApp.TAG, "ChannelListFragment.notifyDataSetChanged()");
        mChannelAdapter.notifyDataSetChanged();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getContext(),
                TVGuideContract.ChannelEntry.CONTENT_URI,
                null,
                null,
                null,
                TVGuideContract.ChannelEntry.COLUMN_CHANNEL_SORT_ORDER + " ASC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        List<ChannelVO> channelList = new ArrayList<>();
        if (data != null && data.moveToFirst()) {
            do {
                ChannelVO channel = ChannelVO.parseFromCursor(data);
                channelList.add(channel);
            } while (data.moveToNext());
        }

        Log.d(TVGuideApp.TAG, "Retrieved attractions DESC : " + channelList.size());
        mChannelAdapter.setNewData(channelList);

        ChannelModel.getInstance().setStoredData(channelList);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public void onEventMainThread(DataEvent.ChannelDataLoadedEvent event) {
        Log.e(TVGuideApp.TAG, "ChannelListFragment.onEventMainThread().ChannelDataLoadedEvent");
        String extra = event.getExtraMessage();
//        Toast.makeText(getContext(), "ChannelListFragement:onEventMainThread:Extra : " + extra, Toast.LENGTH_SHORT).show();

        //List<ChannelVO> newChannelList = ChannelModel.getInstance().getChannelList();
        List<ChannelVO> newChannelList = event.getChannelList();
        mChannelAdapter.setNewData(newChannelList);
    }
}
