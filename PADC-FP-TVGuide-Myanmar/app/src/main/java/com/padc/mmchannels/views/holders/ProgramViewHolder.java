package com.padc.mmchannels.views.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.padc.mmchannels.R;
import com.padc.mmchannels.data.vos.ProgramVO;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator's user on 09-Sep-16.
 */
public class ProgramViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.tv_program_title)
    TextView tvProgramName;

    @BindView(R.id.iv_program_photo)
    ImageView ivProgram;

    private ControllerProgramItem mController;
    private ProgramVO mProgram;

    public ProgramViewHolder(View itemView, ControllerProgramItem controller) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
        mController = controller;
    }

    public void bindData(ProgramVO program) {
        mProgram = program;
        tvProgramName.setText(program.getProgram_title());
        ivProgram.setImageResource(getImageResourceById((int)program.getProgram_id()));
    }

    private int getImageResourceById(int id){
        switch(id){
            case 0:
                return R.drawable.img_en_program1;
            case 1:
                return R.drawable.img_mm_program1;
            case 2:
                return R.drawable.img_mm_program2;
            case 3:
                return R.drawable.img_mm_program3;
            case 4:
                return R.drawable.img_mm_program4;
            case 5:
                return R.drawable.img_kr_program1;
            case 6:
                return R.drawable.img_kr_program2;
        }
        return R.drawable.drawer_background;
    }

    @Override
    public void onClick(View view) {
        mController.onTapProgram(mProgram, ivProgram);
    }

    public interface ControllerProgramItem {
        void onTapProgram(ProgramVO program, ImageView ivProgram);
//        void onTapReminder(ProgramVO program);
    }
}
