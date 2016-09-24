package com.padc.tvguide.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.padc.tvguide.R;
import com.padc.tvguide.TVGuideApp;
import com.padc.tvguide.data.vos.ProgramVO;
import com.padc.tvguide.fragments.DayPagerFragment;
import com.padc.tvguide.views.holders.ProgramViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 9/10/2016.
 */
public class ChannelDetailActivity extends BaseActivity
        implements ProgramViewHolder.ControllerProgramItem {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.iv_channel_icon)
    ImageView ivChannelIcon;

    static final String IE_IMG_SRC = "IMG_SRC";
    int imgSRC = 0;

/*    public static Intent newIntent(){
        Intent intent = new Intent(TVGuideApp.getContext(),ChannelDetailActivity.class);
        return intent;
    }*/

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

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_channel_detail_container, DayPagerFragment.newInstance())
                    .commit();

            Bundle extras = getIntent().getExtras();
            if(extras != null) {
                imgSRC = extras.getInt(IE_IMG_SRC, 0);
            }
        }
        else{
            imgSRC = savedInstanceState.getInt(IE_IMG_SRC, 0);
        }
        if(imgSRC != 0)
            ivChannelIcon.setImageResource(imgSRC);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        Toast.makeText(TVGuideApp.getContext(), "ChannelDetailActivity:onCreate(): ", Toast.LENGTH_LONG).show();
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
    public void onTapProgram(ProgramVO program, ImageView ivProgram) {
        Intent intent = ProgramDetailActivity.newIntent(program.getName());
        startActivity(intent);
    }
}
