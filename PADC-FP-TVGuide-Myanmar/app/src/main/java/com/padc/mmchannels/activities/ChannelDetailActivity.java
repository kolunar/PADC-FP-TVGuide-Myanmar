package com.padc.mmchannels.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.padc.mmchannels.R;
import com.padc.mmchannels.MMChannelsApp;
import com.padc.mmchannels.data.models.ChannelModel;
import com.padc.mmchannels.data.vos.ChannelDetailsVO;
import com.padc.mmchannels.data.vos.ChannelProgramVO;
import com.padc.mmchannels.data.vos.ChannelVO;
import com.padc.mmchannels.data.vos.MyChannelVO;
import com.padc.mmchannels.data.vos.MyReminderVO;
import com.padc.mmchannels.dialogs.TimePrefixDialog;
import com.padc.mmchannels.events.DataEvent;
import com.padc.mmchannels.fragments.DayPagerFragment;
import com.padc.mmchannels.utils.MMFontUtils;
import com.padc.mmchannels.views.holders.ChannelProgramViewHolder;
import com.padc.mmchannels.views.holders.ChannelViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by user on 9/10/2016.
 */
public class ChannelDetailActivity extends BaseActivity
        implements ChannelProgramViewHolder.ControllerChannelProgramItem,
        TimePrefixDialog.AddRemoveAlarmDelegate {

//    @BindView(R.id.appbar)
//    AppBarLayout appbar;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;

    @BindView(R.id.iv_channel_icon)
    ImageView ivChannelIcon;

    @BindView(R.id.tv_channel_name)
    TextView tvChannelName;

    @BindView(R.id.tv_channel_start_time)
    TextView tvChannelStartTime;

    @BindView(R.id.tv_channel_end_time)
    TextView tvChannelEndTime;

    @BindView(R.id.tv_channel_desc)
    TextView tvChannelDesc;


//    @BindView(R.id.tv_channel_show_more)
//    TextView tvChannelDescShowMore;
//
//    @BindView(R.id.tv_channel_show_less)
//    TextView tvChannelDescShowLess;
/*

    @OnClick(R.id.tv_channel_show_more)
    public void onTapChannelDescShowMore(TextView tvChannelDescShowMore) {
        //toolbar.collapseActionView();
        //appbar.setMinimumHeight(400);
        tvChannelDescShowMore.setVisibility(View.INVISIBLE);
        tvChannelDescShowLess.setVisibility(View.VISIBLE);
        tvChannelDesc.setVisibility(View.VISIBLE);

*/
/*        float heightDp = (getResources().getDisplayMetrics().heightPixels * 2) / 3;
        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams)appbar.getLayoutParams();
        lp.height = (int)heightDp;
        appbar.setExpanded(true, true);*//*


*/
/*        appbar.setTranslationY((int)heightDp);
        appbar.animate()
                .translationY(appbarHeight)
                .setDuration(500).start();*//*

    }

    @OnClick(R.id.tv_channel_show_less)
    public void onTapChannelDescShowLess(TextView tvChannelDescShowLess) {
        tvChannelDescShowLess.setVisibility(View.INVISIBLE);
        tvChannelDesc.setVisibility(View.INVISIBLE);
        tvChannelDescShowMore.setVisibility(View.VISIBLE);
*/
/*
        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams)appbar.getLayoutParams();
        lp.height = appbarHeight;
        appbar.setExpanded(false, true);
*//*

    }
*/

    static ChannelVO mChannelVO;

    static final String IE_CHANNEL_ID = "CHANNEL_ID";
    static final String IE_CHANNEL_NAME = "CHANNEL_NAME";
    static final String IE_CHANNEL_DESC = "CHANNEL_DESC";
    static final String IE_IMG_SRC = "IMG_SRC";
    int imgSRC = 0;
    int appbarHeight = 0;
    private ChannelViewHolder.ControllerChannelItem mController;

/*    public static Intent newIntent(){
        Intent intent = new Intent(TVGuideApp.getContext(),ChannelDetailActivity.class);
        return intent;
    }*/

    public static Intent newIntent(ChannelVO channel){
        Intent intent = new Intent(MMChannelsApp.getContext(),ChannelDetailActivity.class);
        if(channel != null) {
//          intent.putExtra(IE_CHANNEL_NAME, channel.getChannel_name());
//          intent.putExtra(IE_IMG_SRC, channel.getChannel_id());
//          intent.putExtra(IE_IMG_SRC, channel.getChannel_name());
//          intent.putExtra(IE_IMG_SRC, channel.getChannel_desc());
//          intent.putExtra(IE_IMG_SRC, channel.getChannel_icon());
            mChannelVO = channel;
            mChannelVO.setMyChannel(MyChannelVO.getIsMyChannel(0, mChannelVO.getChannel_id()));
        }
        return intent;
    }

    public static Intent newIntent(int imgSRC){
        Intent intent = new Intent(MMChannelsApp.getContext(),ChannelDetailActivity.class);
        intent.putExtra(IE_IMG_SRC, imgSRC);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_detail);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_channel_detail_container, DayPagerFragment.newInstance(new ChannelDetailsVO(mChannelVO)))
                    .commit();

            Bundle extras = getIntent().getExtras();
            if(extras != null) {
                imgSRC = extras.getInt(IE_IMG_SRC, 0);
            }
        }
        else{
            imgSRC = savedInstanceState.getInt(IE_IMG_SRC, 0);
        }

        if(imgSRC != 0) {
            ivChannelIcon.setImageResource(imgSRC);
        }

        collapsingToolbar.setCollapsedTitleTypeface(MMFontUtils.getMMTypeFace());
        collapsingToolbar.setExpandedTitleTypeface(MMFontUtils.getMMTypeFace());

        if(mChannelVO != null) {
            bindChannelData(mChannelVO);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        appbarHeight = getAppBarLayoutHeight();
//        Toast.makeText(TVGuideApp.getContext(), "ChannelDetailActivity:onCreate(): ", Toast.LENGTH_LONG).show();

        if(MMChannelsApp.hasInternet) {
            ChannelModel.getInstance().loadChannelDetails(mChannelVO.getChannel_id());
        }
//        ChannelDetailsVO channelDetails = ChannelModel.getInstance().getChannelDetails();
    }


    private void bindChannelData(ChannelVO channel){
        collapsingToolbar.setTitle(channel.getChannel_name());

        Glide.with(ivChannelIcon.getContext())
                .load(channel.getChannel_icon())
				.diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .fitCenter()
                .placeholder(R.drawable.ic_more_horiz_gray_24dp)
                .error(R.drawable.ic_more_horiz_gray_24dp)
                .into(ivChannelIcon);

        tvChannelName.setText(channel.getChannel_name());
        tvChannelStartTime.setText(channel.getStart_time() + " AM");
        tvChannelEndTime.setText(channel.getEnd_time() + " AM");
        tvChannelDesc.setText(channel.getChannel_desc());

        updateMyChannelCheckState();
    }

    private int getAppBarLayoutHeight() {
/*        final TypedArray ta = TVGuideApp.getContext().getTheme().obtainStyledAttributes(
                new int[] {android.R.attr.actionBarSize});
        int actionBarHeight = (int) ta.getDimension(0, 0);
        return actionBarHeight;*/
//        int height = 200;

/*        TypedValue tv = new TypedValue();
        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams)appbar.getLayoutParams();

        return (int)lp.height;*/

        return 200;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(IE_IMG_SRC, imgSRC);
        super.onSaveInstanceState(outState);
    }

    Menu menu;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.top_right_menu_home, menu);
        this.menu = menu;
        updateMyChannelCheckState();
        return true;
    }

    private void updateMyChannelCheckState() {
        if(menu != null) {
            MenuItem item = menu.findItem(R.id.action_is_my_channel);
            item.setChecked(mChannelVO.isMyChannel());
            updateMyChannelIcon(item);
        }
    }

    private void updateMyChannelIcon(MenuItem item){
        item.setIcon(item.isChecked() ? R.drawable.ic_star_white_24dp : R.drawable.ic_star_border_white_24dp);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                return true;
            case android.R.id.home:
//                Toast.makeText(TVGuideApp.getContext(), "ChannelDetailActivity:onOptionsItemSelected():android.R.id.home ", Toast.LENGTH_LONG).show();
                super.onBackPressed();
                return true;
            case R.id.action_is_my_channel:
                item.setChecked(!item.isChecked());
                updateMyChannelIcon(item);
                if(item.isChecked()) {
                    MyChannelVO.saveMyChannel(new MyChannelVO(0, mChannelVO.getChannel_id()));
                }
                else {
                    MyChannelVO.deleteMyChannel(0, mChannelVO.getChannel_id());
                }
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
                .show(getSupportFragmentManager(), "TimePrefixPicker");
    }

    @Override
    public void onAddAlarm(long channel_program_id, int time_ahead) {
        if(btnReminder != null)
            btnReminder.setBackgroundResource(R.drawable.ic_notifications_black_24dp);
        MyReminderVO.saveMyReminder(new MyReminderVO(0, channel_program_id, time_ahead));
        ChannelProgramVO channelProgramVO = ChannelProgramVO.loadChannelProgramByID(channel_program_id);
        TimePrefixDialog.AddAlarm((int)channel_program_id, channelProgramVO.getAir_date(), channelProgramVO.getStart_time(), time_ahead, false);
        refreshFragment();
    }

    @Override
    public void onRemoveAlarm(long channel_program_id) {
        if(btnReminder != null)
            btnReminder.setBackgroundResource(R.drawable.ic_notifications_none_black_24dp);
        MyReminderVO.deleteMyReminder(0, channel_program_id);
        TimePrefixDialog.AddAlarm((int)channel_program_id, "", "", 0, false);
        refreshFragment();
    }

    private void refreshFragment() {
//    xxxxxxxxxxxxxx
//        ChannelDetailFragment.restartLoader();
    }

    public void onEventMainThread(DataEvent.ChannelDetailsLoadedEvent event) {
        Log.e(MMChannelsApp.TAG, "ChannelDetailActivity.onEventMainThread().ChannelDetailsLoadedEvent");
        String extra = event.getExtraMessage();
//        Toast.makeText(getContext(), "ChannelDetailFragment:onEventMainThread:Extra : " + extra, Toast.LENGTH_SHORT).show();
        ChannelDetailsVO newChannelDetails = event.getChannelDetails();
        mChannelVO = newChannelDetails.getChannel();
        mChannelVO.setMyChannel(MyChannelVO.getIsMyChannel(0, mChannelVO.getChannel_id()));
        Log.e(MMChannelsApp.TAG, "ChannelDetailActivity.bindChannelData() via ChannelDetailsLoadedEvent");
        bindChannelData(mChannelVO);
    }
}
