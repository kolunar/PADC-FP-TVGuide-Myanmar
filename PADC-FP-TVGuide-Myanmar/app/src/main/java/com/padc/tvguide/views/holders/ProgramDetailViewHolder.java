package com.padc.tvguide.views.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.padc.tvguide.R;
import com.padc.tvguide.data.vos.ProgramVO;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator's user on 09-Sep-16.
 */
public class ProgramDetailViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_program_title)
    TextView tvProgramName;

    private ProgramVO mProgram;

    public ProgramDetailViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bindData(ProgramVO program) {
        mProgram = program;
        tvProgramName.setText(program.getProgram_title());
    }

}
