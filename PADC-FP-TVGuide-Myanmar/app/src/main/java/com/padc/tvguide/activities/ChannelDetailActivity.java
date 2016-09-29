package com.padc.tvguide.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.padc.tvguide.R;
import com.padc.tvguide.TVGuideApp;
import com.padc.tvguide.data.models.ChannelModel;
import com.padc.tvguide.data.vos.ChannelDetailsVO;
import com.padc.tvguide.data.vos.ChannelProgramVO;
import com.padc.tvguide.data.vos.ChannelVO;
import com.padc.tvguide.events.DataEvent;
import com.padc.tvguide.fragments.DayPagerFragment;
import com.padc.tvguide.utils.MMFontUtils;
import com.padc.tvguide.views.holders.ChannelProgramViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by user on 9/10/2016.
 */
public class ChannelDetailActivity extends BaseActivity
        implements ChannelProgramViewHolder.ControllerChannelProgramItem {

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

/*    public static Intent newIntent(){
        Intent intent = new Intent(TVGuideApp.getContext(),ChannelDetailActivity.class);
        return intent;
    }*/

    public static Intent newIntent(ChannelVO channel){
        Intent intent = new Intent(TVGuideApp.getContext(),ChannelDetailActivity.class);
        if(channel != null) {
//          intent.putExtra(IE_CHANNEL_NAME, channel.getChannel_name());
//          intent.putExtra(IE_IMG_SRC, channel.getChannel_id());
//          intent.putExtra(IE_IMG_SRC, channel.getChannel_name());
//          intent.putExtra(IE_IMG_SRC, channel.getChannel_desc());
//          intent.putExtra(IE_IMG_SRC, channel.getChannel_icon());
            mChannelVO = channel;
        }
        return intent;
    }

    public static Intent newIntent(int imgSRC){
        Intent intent = new Intent(TVGuideApp.getContext(),ChannelDetailActivity.class);
        intent.putExtra(IE_IMG_SRC, imgSRC);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_detail);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        ChannelModel.getInstance().loadChannelDetails();
        ChannelDetailsVO channelDetails = ChannelModel.getInstance().getChannelDetails();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_channel_detail_container, DayPagerFragment.newInstance(channelDetails))
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
    }

    private void bindChannelData(ChannelVO channel){
        collapsingToolbar.setTitle(channel.getChannel_name());

        Glide.with(ivChannelIcon.getContext())
                .load(channel.getChannel_icon())
                .fitCenter()
                .placeholder(R.drawable.ic_more_horiz_gray_24dp)
                .error(R.drawable.ic_more_horiz_gray_24dp)
                .into(ivChannelIcon);

        tvChannelName.setText(channel.getChannel_name());
        tvChannelStartTime.setText(channel.getStart_time() + " AM");
        tvChannelEndTime.setText(channel.getEnd_time() + " AM");
        tvChannelDesc.setText(channel.getChannel_desc());
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.top_right_menu_home, menu);
        return true;
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
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTapChannelProgram(ChannelProgramVO program) {
//        Intent intent = ProgramDetailActivity.newIntent(program.getProgram().getProgram_title());
        Intent intent = ProgramDetailActivity.newIntent(program);
        startActivity(intent);
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

    public void onEventMainThread(DataEvent.ChannelDetailsLoadedEvent event) {
        Log.e(TVGuideApp.TAG, "ChannelDetailActivity.onEventMainThread().ChannelDetailsLoadedEvent");
        String extra = event.getExtraMessage();
//        Toast.makeText(getContext(), "ChannelDetailFragment:onEventMainThread:Extra : " + extra, Toast.LENGTH_SHORT).show();
        ChannelDetailsVO newChannelDetails = event.getChannelDetails();
        mChannelVO = newChannelDetails.getChannel();
        Log.e(TVGuideApp.TAG, "ChannelDetailActivity.bindChannelData() via ChannelDetailsLoadedEvent");
        bindChannelData(mChannelVO);
    }
}
