package com.padc.tvguide.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.padc.tvguide.R;
import com.padc.tvguide.TVGuideApp;
import com.padc.tvguide.adapters.ProgramAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProgramParentActivity extends AppCompatActivity {

    private ProgramAdapter programAdapter;

    public static Intent newIntent(){
        Intent intent = new Intent(TVGuideApp.getContext(),ProgramParentActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_parent);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        programAdapter = new ProgramAdapter();

        RecyclerView rvProgram = (RecyclerView) findViewById(R.id.rv_programs);
        rvProgram.setAdapter(programAdapter);
        rvProgram.setLayoutManager(new LinearLayoutManager(TVGuideApp.getContext(), LinearLayoutManager.VERTICAL, false));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_search);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
