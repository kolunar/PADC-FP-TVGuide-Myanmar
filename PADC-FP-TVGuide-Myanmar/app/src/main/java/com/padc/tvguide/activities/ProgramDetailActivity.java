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

import com.padc.tvguide.R;
import com.padc.tvguide.TVGuideApp;
import com.padc.tvguide.dialogs.TimePrefixDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProgramDetailActivity extends AppCompatActivity {

    @BindView(R.id.cv_parent)
    CardView cvParent;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;

    @BindView(R.id.fab_share)
    FloatingActionButton fab;

    private String mProgramTitle;
    private static final String IE_PROGRAM_NAME = "IE_PROGRAM_NAME";

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
        collapsingToolbar.setTitle(mProgramTitle);
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
        }
        return super.onOptionsItemSelected(item);
    }

    private void showTimePrefixPicker() {
        DialogFragment newFragment = new TimePrefixDialog();
        newFragment.show(getSupportFragmentManager(), "TimePrefixPicker");
    }
}
