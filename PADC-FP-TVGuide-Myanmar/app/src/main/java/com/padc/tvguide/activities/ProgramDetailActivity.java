package com.padc.tvguide.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.padc.tvguide.R;
import com.padc.tvguide.TVGuideApp;
import com.padc.tvguide.data.vos.ChannelProgramVO;
import com.padc.tvguide.dialogs.TimePrefixDialog;
import com.padc.tvguide.utils.MMFontUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProgramDetailActivity extends BaseActivity {

    @BindView(R.id.cv_parent)
    CardView cvParent;

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

    private String mProgramTitle;
    private static final String IE_PROGRAM_NAME = "IE_PROGRAM_NAME";

    static ChannelProgramVO mChannelProgramVO;

    public static Intent newIntent(ChannelProgramVO channelProgram){
        Intent intent = new Intent(TVGuideApp.getContext(),ProgramDetailActivity.class);
        if(channelProgram != null){
            mChannelProgramVO = channelProgram;
            intent.putExtra(IE_PROGRAM_NAME, channelProgram.getProgram().getProgram_title());
        }
        return intent;
    }

    public static Intent newIntent(String programName){
        Intent intent = new Intent(TVGuideApp.getContext(),ProgramDetailActivity.class);
        intent.putExtra(IE_PROGRAM_NAME, programName);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_detail);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        cvParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = ProgramParentActivity.newIntent();
                startActivity(intent);
            }
        });

        mProgramTitle = getIntent().getStringExtra(IE_PROGRAM_NAME);
        collapsingToolbar.setCollapsedTitleTypeface(MMFontUtils.getMMTypeFace());
        collapsingToolbar.setExpandedTitleTypeface(MMFontUtils.getMMTypeFace());

        if(mChannelProgramVO != null){
            collapsingToolbar.setTitle(mChannelProgramVO.getProgram().getProgram_title());

            Glide.with(ivProgramPhoto.getContext())
                    .load(mChannelProgramVO.getProgram().getProgram_image())
                    .fitCenter()
                    .placeholder(R.drawable.ic_more_horiz_gray_24dp)
                    .error(R.drawable.ic_more_horiz_gray_24dp)
                    .into(ivProgramPhoto);

            tvProgramTitle.setText(mChannelProgramVO.getProgram().getProgram_title());
            tvProgramDesc.setText(mChannelProgramVO.getProgram().getProgram_desc());
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.top_right_menu_program_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_reminder:
                showTimePrefixPicker();
                return true;
            case R.id.action_watchlist:
                return true;
            case android.R.id.home:
                super.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showTimePrefixPicker() {
        DialogFragment newFragment = new TimePrefixDialog();
        newFragment.show(getSupportFragmentManager(), "TimePrefixPicker");
    }
}
