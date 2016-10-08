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
import android.widget.Button;

import com.padc.tvguide.R;
import com.padc.tvguide.TVGuideApp;
import com.padc.tvguide.activities.ChannelDetailActivity;
import com.padc.tvguide.activities.ProgramDetailActivity;
import com.padc.tvguide.adapters.ChannelDetailAdapter;
import com.padc.tvguide.data.models.ChannelModel;
import com.padc.tvguide.data.persistence.TVGuideContract.ChannelProgramEntry;
import com.padc.tvguide.data.vos.ChannelDetailsVO;
import com.padc.tvguide.data.vos.ChannelProgramVO;
import com.padc.tvguide.data.vos.MyReminderVO;
import com.padc.tvguide.data.vos.ProgramVO;
import com.padc.tvguide.dialogs.TimePrefixDialog;
import com.padc.tvguide.events.DataEvent;
import com.padc.tvguide.utils.DateTimeUtils;
import com.padc.tvguide.utils.TVGuideConstants;
import com.padc.tvguide.views.holders.ChannelProgramViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by user on 9/10/2016.
 */
public class ChannelDetailFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<Cursor>,
ChannelProgramViewHolder.ControllerChannelProgramItem,
        TimePrefixDialog.AddRemoveAlarmDelegate{

    @BindView(R.id.rv_channel_detail)
    RecyclerView rvChannelDetail;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout srLayout;

//    @BindView(R.id.cv_channel)
//    CardView cvChannel;

    private ChannelDetailAdapter mChannelDetailsAdapter;
    private ChannelProgramViewHolder.ControllerChannelProgramItem controllerChannelProgramItem;
    private static List<ChannelProgramVO> mChannelProgramVOList;

/*    public static ChannelDetailFragment newInstance() {
        ChannelDetailFragment fragment = new ChannelDetailFragment();
        return fragment;
    }*/
    static final String IE_DAY_OF_WEEK = "DAY_OF_WEEK";
    static final String IE_CHANNEL_ID = "CHANNEL_ID";
    private String mDay = "Today";
    private long mChannelID = 0;

    public static ChannelDetailFragment newInstance(List<ChannelProgramVO> channelPrograms, String day, long channel_id) {
        Log.e(TVGuideApp.TAG, "ChannelDetailFragment.newInstance.channelDetails.getChannel_programs().size:" + channelPrograms.size());
        mChannelProgramVOList = channelPrograms;

        Bundle bundle = new Bundle();
        bundle.putString(IE_DAY_OF_WEEK, day);//From DayPagerFragment"
        bundle.putLong(IE_CHANNEL_ID, channel_id);
        ChannelDetailFragment fragment = new ChannelDetailFragment();
        fragment.setArguments(bundle);//set Fragmentclass Arguments
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ChannelDetailActivity)
            controllerChannelProgramItem = (ChannelProgramViewHolder.ControllerChannelProgramItem) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_channel_detail, container, false);
        ButterKnife.bind(this, rootView);

/*        Bundle extras = getActivity().getIntent().getExtras();
        if(extras != null) {
            day = extras.getString(IE_DAY_OF_WEEK, "Today");
        }*/
        mDay = getArguments().getString(IE_DAY_OF_WEEK);
        mChannelID = getArguments().getLong(IE_CHANNEL_ID);
        Log.e(TVGuideApp.TAG, "ChannelDetailFragment.onCreateView().mDay: " + mDay);
/*        cvChannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = ProgramDetailActivity.newIntent("Program Detail");
                startActivity(intent);
            }
        });*/
//        ChannelDetailsVO channelDetails = ChannelModel.getInstance().getChannelDetails();
        List<ChannelProgramVO> channelProgramList = mChannelProgramVOList;
//        List<ProgramVO> programList = getProgramList();
/*        if(rvChannelDetail != null) {
            rvChannelDetail.removeAllViews();
        }*/
        mChannelDetailsAdapter = new ChannelDetailAdapter(channelProgramList, controllerChannelProgramItem);
        rvChannelDetail.setAdapter(mChannelDetailsAdapter);
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().getSupportLoaderManager().initLoader(TVGuideConstants.CHANNEL_DETAIL_LOADER + DateTimeUtils.getDayNumber(mDay), null, this);
        if(TVGuideApp.hasInternet) {
//            ChannelModel.getInstance().loadChannelDetails();
        }
    }

    public void restartLoader(){
        getActivity().getSupportLoaderManager().restartLoader(TVGuideConstants.CHANNEL_DETAIL_LOADER, null, this);
    }

    public void notifyDataSetChanged() {
        mChannelDetailsAdapter.notifyDataSetChanged();
    }

    private List<ProgramVO> getProgramList(){
        List<ProgramVO> dummy = new ArrayList<ProgramVO>();
        String[] programListArray = getResources().getStringArray(R.array.dummy_channel_detail_list);
        for (int i = 0; i < programListArray.length; i++) {
            dummy.add(new ProgramVO(i, programListArray[i], ""));
        }
        return dummy;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus eventBus = EventBus.getDefault();
        if (!eventBus.isRegistered(this)) {
            eventBus.register(this);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus eventBus = EventBus.getDefault();
        eventBus.unregister(this);
    }


    @Override
    public void onTapChannelProgram(ChannelProgramVO channelProgramVO) {
//        Intent intent = ProgramDetailActivity.newIntent(channelProgramVO.getProgram().getProgram_title());
        Intent intent = ProgramDetailActivity.newIntent(channelProgramVO);
        startActivity(intent);
    }

    Button btnReminder;

    @Override
    public void onTapReminder(ChannelProgramVO channelProgramVO, Button button) {
        btnReminder = button;
        new TimePrefixDialog().newInstance(channelProgramVO.getChannel_program_id(), channelProgramVO.getTime_ahead())
                .show(getChildFragmentManager(), "TimePrefixPicker");
    }

    @Override
    public void onAddAlarm(long channel_program_id, int time_ahead) {
        if(btnReminder != null)
            btnReminder.setBackgroundResource(R.drawable.ic_notifications_black_24dp);
        MyReminderVO.saveMyReminder(new MyReminderVO(0, channel_program_id, time_ahead));
        ChannelProgramVO channelProgramVO = ChannelProgramVO.loadChannelProgramByID(channel_program_id);
        TimePrefixDialog.AddAlarm((int)channel_program_id, channelProgramVO.getAir_date(), channelProgramVO.getStart_time(), time_ahead, false);
        restartLoader();
    }

    @Override
    public void onRemoveAlarm(long channel_program_id) {
        if(btnReminder != null)
            btnReminder.setBackgroundResource(R.drawable.ic_notifications_none_black_24dp);
        MyReminderVO.deleteMyReminder(0, channel_program_id);
        TimePrefixDialog.AddAlarm((int)channel_program_id, "", "", 0, false);
        restartLoader();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String selection = ChannelProgramEntry.COLUMN_AIR_DAY + "=? AND " + ChannelProgramEntry.COLUMN_CHANNEL_ID + "=?";
//        String[] selectionArgs = { String.valueOf(btn_logbook_date.getText().toString()) };
        String[] selectionArgs = {mDay, String.valueOf(mChannelID)};
        return new CursorLoader(getContext(),
                ChannelProgramEntry.CONTENT_URI,
                null,
                selection,
                selectionArgs,
                ChannelProgramEntry.COLUMN_START_TIME + " ASC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        List<ChannelProgramVO> channelProgramVOList = new ArrayList<>();
        if (data != null && data.moveToFirst()) {
            do {
                ChannelProgramVO channelProgram = ChannelProgramVO.parseFromCursor(data);
                channelProgram.setProgram(ProgramVO.loadProgramByID(channelProgram.getProgram_id()));
                MyReminderVO reminder = MyReminderVO.getMyReminderItem(0, channelProgram.getChannel_program_id());
                channelProgram.setTime_ahead(reminder != null ? reminder.getTime_ahead() : 0);
                channelProgramVOList.add(channelProgram);
            } while (data.moveToNext());
        }
        if(mDay.equalsIgnoreCase("Sat")){
            Log.e(TVGuideApp.TAG, "ChannelDetailFragment.onLoadFinished()."+mDay+".channelProgramVOList.size(): " + channelProgramVOList.size());
        }
        mChannelDetailsAdapter.setNewData(channelProgramVOList, false, true);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public void onEventMainThread(DataEvent.ChannelDetailsLoadedEvent event) {
        Log.e(TVGuideApp.TAG, "ChannelDetailFragment.onEventMainThread().ChannelDetailsLoadedEvent");
        String extra = event.getExtraMessage();
//        Toast.makeText(getContext(), "ChannelDetailFragment:onEventMainThread:Extra : " + extra, Toast.LENGTH_SHORT).show();

        //List<ChannelVO> newChannelList = ChannelModel.getInstance().getChannelList();
        ChannelDetailsVO newChannelDetails = event.getChannelDetails();
//        mChannelDetailsAdapter.setNewData(newChannelDetails.getChannel_programs());
        if(mDay.equalsIgnoreCase("Sat")){
            Log.e(TVGuideApp.TAG, "ChannelDetailFragment.onEventMainThread().ChannelDetailsLoadedEvent: It's " + mDay);
        }
        mChannelDetailsAdapter.setNewData(newChannelDetails.getChannelProgramsByAirDay(mDay, true), true, false);

    }
}
