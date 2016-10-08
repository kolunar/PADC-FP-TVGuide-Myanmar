package com.padc.tvguide.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.padc.tvguide.R;
import com.padc.tvguide.TVGuideApp;
import com.padc.tvguide.data.models.ProgramModel;
import com.padc.tvguide.data.vos.ChannelProgramVO;
import com.padc.tvguide.data.vos.MyReminderVO;
import com.padc.tvguide.data.vos.MyWatchListVO;
import com.padc.tvguide.data.vos.ProgramDetailsVO;
import com.padc.tvguide.data.vos.ProgramVO;
import com.padc.tvguide.dialogs.TimePrefixDialog;
import com.padc.tvguide.events.DataEvent;
import com.padc.tvguide.utils.MMFontUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

public class ProgramDetailActivity extends BaseActivity implements TimePrefixDialog.AddRemoveAlarmDelegate {

    @BindView(R.id.cv_parent)
    CardView cvParent;

    @BindView(R.id.cv_root)
    CardView cvRoot;

    @BindView(R.id.ll_program_parent)
    LinearLayout llprogramParent;

    @BindView(R.id.ll_program_root)
    LinearLayout llprogramRoot;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;

    @BindView(R.id.fab_share)
    FloatingActionButton fab;

    @BindView(R.id.iv_program_photo)
    ImageView ivProgramPhoto;

    @BindView(R.id.tv_program_title)
    TextView tvProgramTitle;

    @BindView(R.id.tv_program_desc)
    TextView tvProgramDesc;

    @BindView(R.id.tv_program_duration)
    TextView tvProgramDuration;

    @BindView(R.id.iv_parent_photo)
    ImageView ivParentPhoto;

    @BindView(R.id.tv_parent_title)
    TextView tvParentTitle;

    @BindView(R.id.iv_root_photo)
    ImageView ivRootPhoto;

    @BindView(R.id.tv_root_title)
    TextView tvRootTitle;

    private String mProgramTitle;
    private static final String IE_PROGRAM_NAME = "IE_PROGRAM_NAME";

    private Boolean isChannelProgram;
    private static final String IE_IS_CHANNEL_PROGRAM = "IE_IS_CHANNEL_PROGRAM";

    static ChannelProgramVO mChannelProgramVO;
    static ProgramVO mProgramVO;
    MyReminderVO mMyReminderVO;

    public static Intent newIntent(ProgramVO program){
        Intent intent = new Intent(TVGuideApp.getContext(),ProgramDetailActivity.class);
        if(program != null){
            mProgramVO = program;
            intent.putExtra(IE_PROGRAM_NAME, program.getProgram_title());
            intent.putExtra(IE_IS_CHANNEL_PROGRAM, false);
        }
        return intent;
    }

    public static Intent newIntent(ChannelProgramVO channelProgram){
        Intent intent = new Intent(TVGuideApp.getContext(),ProgramDetailActivity.class);
        if(channelProgram != null){
            mChannelProgramVO = channelProgram;
            mChannelProgramVO.getProgram().setMyWatchList(MyWatchListVO.getIsMyWatchList(0, mChannelProgramVO.getProgram_id()));
            intent.putExtra(IE_PROGRAM_NAME, channelProgram.getProgram().getProgram_title());
            intent.putExtra(IE_IS_CHANNEL_PROGRAM, true);
        }
        else {
            Toast.makeText(TVGuideApp.getContext(), "ProgramDetailActivity.newIntent.ChannelProgramVO IS NULL", Toast.LENGTH_SHORT).show();
        }
        return intent;
    }

/*    public static Intent newIntent(String programName){
        Intent intent = new Intent(TVGuideApp.getContext(),ProgramDetailActivity.class);
        intent.putExtra(IE_PROGRAM_NAME, programName);
        return intent;
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_detail);
        ButterKnife.bind(this);

        mProgramTitle = getIntent().getStringExtra(IE_PROGRAM_NAME);
        isChannelProgram = getIntent().getBooleanExtra(IE_IS_CHANNEL_PROGRAM, true);

        setSupportActionBar(toolbar);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                sendViaShareIntent(mProgramTitle + " - " + "...");
            }
        });

        if (savedInstanceState == null) {
            Log.e(TVGuideApp.TAG, "ProgramDetailActivity:onCreate():savedInstanceState == null : isChannelProgram:" + isChannelProgram);
//            Toast.makeText(TVGuideApp.getContext(), "ProgramDetailActivity:onCreate():savedInstanceState == null ", Toast.LENGTH_LONG).show();

            if(isChannelProgram) {
                bindChannelProgramData(mChannelProgramVO);
            }
            else {
                bindProgramData(mProgramVO, false);
            }
        }

        cvParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = ProgramParentActivity.newIntent();
                startActivity(intent);
            }
        });

        cvRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = ProgramParentActivity.newIntent();
                startActivity(intent);
            }
        });

        collapsingToolbar.setCollapsedTitleTypeface(MMFontUtils.getMMTypeFace());
        collapsingToolbar.setExpandedTitleTypeface(MMFontUtils.getMMTypeFace());

        if(TVGuideApp.hasInternet) {
            if(isChannelProgram)
                ProgramModel.getInstance().loadProgramDetails(mChannelProgramVO.getProgram_id());
            else
                ProgramModel.getInstance().loadProgramDetails(mProgramVO.getProgram_id());
        }

        if(!isChannelProgram){
            tvProgramDuration.setVisibility(View.GONE);
        }
        else {
            mMyReminderVO = MyReminderVO.getMyReminderItem(0, mChannelProgramVO.getChannel_program_id());
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


//        ProgramDetailsVO programDetails = ProgramModel.getInstance().getProgramDetails();
    }

    private void bindChannelProgramData(ChannelProgramVO channelProgram){
        if(channelProgram != null && channelProgram.getProgram() != null) {
            bindProgramData(channelProgram.getProgram(), true);
        }
    }

    private void bindProgramData(ProgramVO program, boolean isChannelProgram){
/*        if(!isChannelProgram){
            tvProgramDuration.setVisibility(View.GONE);
            if(menu != null) {
                MenuItem item = menu.findItem(R.id.action_reminder);
                item.setVisible(false);
            }
        }*/
        collapsingToolbar.setTitle(program.getProgram_title());

        Glide.with(ivProgramPhoto.getContext())
                .load(program.getProgram_image())
				.diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .fitCenter()
                .placeholder(R.drawable.ic_more_horiz_gray_24dp)
                .error(R.drawable.ic_more_horiz_gray_24dp)
                .into(ivProgramPhoto);

        tvProgramTitle.setText(program.getProgram_title());
        tvProgramDesc.setText(program.getProgram_desc());

        updateMyWatchListCheckState();
    }

    private void bindProgramDetails(ProgramDetailsVO programDetails){

        if(programDetails.getProgram_parent() != null) {
            if(!llprogramParent.isShown())
                llprogramParent.setVisibility(View.VISIBLE);
            Glide.with(ivParentPhoto.getContext())
                    .load(programDetails.getProgram_parent().getProgram_image())
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .fitCenter()
                    .placeholder(R.drawable.ic_more_horiz_gray_24dp)
                    .error(R.drawable.ic_more_horiz_gray_24dp)
                    .into(ivParentPhoto);
            tvParentTitle.setText(programDetails.getProgram_parent().getProgram_title());
        }
        else {
            llprogramParent.setVisibility(View.GONE);
        }

        if(programDetails.getProgram_root() != null) {
            if(!llprogramRoot.isShown()){
                llprogramRoot.setVisibility(View.VISIBLE);
            }
            Glide.with(ivRootPhoto.getContext())
                    .load(programDetails.getProgram_root().getProgram_image())
					.diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .fitCenter()
                    .placeholder(R.drawable.ic_more_horiz_gray_24dp)
                    .error(R.drawable.ic_more_horiz_gray_24dp)
                    .into(ivRootPhoto);
            tvRootTitle.setText(programDetails.getProgram_root().getProgram_title());
        }
        else {
            llprogramRoot.setVisibility(View.GONE);
        }
    }

    Menu menu;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.top_right_menu_program_detail, menu);
        this.menu = menu;
        if(menu != null && !isChannelProgram) {
            MenuItem item = menu.findItem(R.id.action_reminder);
            item.setVisible(false);
        }
        updateMyWatchListCheckState();
        if(isChannelProgram){
            updateMyReminderCheckState(false);
        }
        return true;
    }

    private void updateMyWatchListCheckState() {
        if(menu != null) {
            MenuItem item = menu.findItem(R.id.action_watchlist);
            item.setChecked(isChannelProgram ? mChannelProgramVO.getProgram().isMyWatchList() > 0 : mProgramVO.isMyWatchList() > 0);
            updateMyWatchListIcon(item);
        }
    }

    private void updateMyWatchListIcon(MenuItem item){
        item.setIcon(item.isChecked() ? R.drawable.ic_gps_fixed_white_24dp : R.drawable.ic_gps_not_fixed_white_24dp);
    }

    private void updateMyReminderCheckState(boolean forceChecked) {
        if(menu != null && isChannelProgram) {
            MenuItem item = menu.findItem(R.id.action_reminder);
            item.setChecked((mMyReminderVO != null && mMyReminderVO.getTime_ahead() > 0) || forceChecked);
            updateMyReminderIcon(item);
        }
    }

    private void updateMyReminderIcon(MenuItem item){
        item.setIcon(item.isChecked() ? R.drawable.ic_notifications_gold_24dp : R.drawable.ic_add_alert_white_24dp);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_reminder:
                showTimePrefixPicker();
                return true;
            case R.id.action_watchlist:
                item.setChecked(!item.isChecked());
                updateMyWatchListIcon(item);
                if(item.isChecked()) {
                    MyWatchListVO.saveMyWatchList(
                            new MyWatchListVO(0,
                                    (isChannelProgram ? mChannelProgramVO.getProgram_id() : mProgramVO.getProgram_id()))
                    );
                }
                else {
                    MyWatchListVO.deleteMyWatchList(0,
                            (isChannelProgram ? mChannelProgramVO.getProgram_id() : mProgramVO.getProgram_id())
                    );
                }
                return true;
            case android.R.id.home:
                super.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus eventBus = EventBus.getDefault();
        if (!eventBus.isRegistered(this)) {
            eventBus.register(this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus eventBus = EventBus.getDefault();
        eventBus.unregister(this);
    }

    private void showTimePrefixPicker() {
        // Show only in isChannelProgram mode
        if(isChannelProgram) {
            new TimePrefixDialog().newInstance(mChannelProgramVO.getChannel_program_id(),
                    mMyReminderVO == null ? 0 : mMyReminderVO.getTime_ahead())
                    .show(getSupportFragmentManager(), "TimePrefixPicker");
        }
    }

    @Override
    public void onAddAlarm(long channel_program_id, int time_ahead) {
        if(mMyReminderVO != null)
            mMyReminderVO.setTime_ahead(time_ahead);
        MyReminderVO.saveMyReminder(new MyReminderVO(0, channel_program_id, time_ahead));
        mMyReminderVO = MyReminderVO.getMyReminderItem(0, channel_program_id);
        ChannelProgramVO channelProgramVO = ChannelProgramVO.loadChannelProgramByID(channel_program_id);
        TimePrefixDialog.AddAlarm((int)channel_program_id, channelProgramVO.getAir_date(), channelProgramVO.getStart_time(), time_ahead, false);
        updateMyReminderCheckState(true);
    }

    @Override
    public void onRemoveAlarm(long channel_program_id) {
        if(mMyReminderVO != null)
            mMyReminderVO.setTime_ahead(0);
        MyReminderVO.deleteMyReminder(0, channel_program_id);
        TimePrefixDialog.AddAlarm((int)channel_program_id, "", "", 0, false);
        updateMyReminderCheckState(false);
    }

    public void onEventMainThread(DataEvent.ProgramDetailsLoadedEvent event) {
        Log.e(TVGuideApp.TAG, "ProgramDetailActivity.onEventMainThread().ProgramDetailsLoadedEvent.isChannelProgram: " + isChannelProgram);
//        String extra = event.getExtraMessage();
        ProgramDetailsVO newProgramDetails = event.getProgramDetails();
        if(isChannelProgram) {
            ChannelProgramVO demo = newProgramDetails.getChannelProgramVOByID(mChannelProgramVO.getProgram_id(), mChannelProgramVO.getChannel_program_id());
            // mChannelProgramVO will be null with demo json except the program "Winter is Coming"
            Log.e(TVGuideApp.TAG, "ProgramDetailActivity.bindChannelProgramData() via ProgramDetailsLoadedEvent");
            if(demo != null) {
                mChannelProgramVO = demo;
            }
            mChannelProgramVO.getProgram().setMyWatchList(MyWatchListVO.getIsMyWatchList(0, mChannelProgramVO.getProgram_id()));
            bindChannelProgramData(mChannelProgramVO);
        }
        else {
            mProgramVO = newProgramDetails.getProgramVOByID(mProgramVO.getProgram_id());
            if(mProgramVO != null) {
                mProgramVO.setMyWatchList(MyWatchListVO.getIsMyWatchList(0, mProgramVO.getProgram_id()));
                Log.e(TVGuideApp.TAG, "ProgramDetailActivity.bindProgramData() via ProgramDetailsLoadedEvent");
                bindProgramData(mProgramVO, false);
            }
        }
        bindProgramDetails(newProgramDetails);
    }
}
