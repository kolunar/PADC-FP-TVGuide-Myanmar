package com.padc.tvguide.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.padc.tvguide.R;
import com.padc.tvguide.TVGuideApp;
import com.padc.tvguide.adapters.ProgramAdapter;
import com.padc.tvguide.data.vos.ProgramVO;
import com.padc.tvguide.fragments.ProgramListFragment;
import com.padc.tvguide.views.holders.ProgramViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProgramParentActivity extends BaseActivity
        implements ProgramViewHolder.ControllerProgramItem {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

//    @BindView(R.id.rv_program_detail)
//    RecyclerView rvProgram;

    @BindView(R.id.fab_share)
    FloatingActionButton fab;

    private ProgramAdapter mProgramAdapter;
    private ProgramViewHolder.ControllerProgramItem controllerProgramItem;

    public static Intent newIntent(){
        Intent intent = new Intent(TVGuideApp.getContext(),ProgramParentActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_parent);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_programs_container, ProgramListFragment.newInstance())
                    .commit();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    ProgramListFragment mProgramListFragment;

    private void loadProgramListFragment() {
        if(mProgramListFragment == null) {
            mProgramListFragment = ProgramListFragment.newInstance();
        }
        if(mProgramListFragment != null){
//            myFragment.onDestroy();

//            getSupportFragmentManager().beginTransaction().remove(myFragment).commit();
//            mFrameLayout.removeAllViews();
//            mFrameLayout.refreshDrawableState();
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_programs_container, mProgramListFragment)
                .commit();
    }

    @Override
    public void onTapProgram(ProgramVO program, ImageView ivProgram) {
        Intent intent = ProgramDetailActivity.newIntent("Program Detail");
        startActivity(intent);
    }
}
