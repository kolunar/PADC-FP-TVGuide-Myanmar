package com.padc.tvguide.views.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.padc.tvguide.R;
import com.padc.tvguide.data.vos.ChannelProgramVO;
import com.padc.tvguide.utils.DateTimeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator's user on 09-Sep-16.
 */
public class ChannelProgramViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.tv_program_title)
    TextView tvProgramName;

    @BindView(R.id.tv_period)
    TextView tvPeriod;

    @BindView(R.id.tv_duration)
    TextView tvDuration;

    @BindView(R.id.iv_program_photo)
    ImageView ivProgram;

    private ControllerChannelProgramItem mController;
    private ChannelProgramVO mChannelProgram;

    public ChannelProgramViewHolder(View itemView, ControllerChannelProgramItem controller) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
        tvProgramName.setSelected(true);
        mController = controller;
    }

    public void bindData(ChannelProgramVO channelProgram) {
        mChannelProgram = channelProgram;
        tvProgramName.setText(channelProgram.getProgram().getProgram_title());
        tvPeriod.setText(DateTimeUtils.getTimePeriod(channelProgram.getStart_time(), channelProgram.getDuration()));
        tvDuration.setText(DateTimeUtils.getDuration(channelProgram.getDuration()));
//        ivProgram.setImageResource(getImageResourceById(channelProgram.getProgram().getProgram_id()));

        Glide.with(ivProgram.getContext())
                .load(channelProgram.getProgram().getProgram_image())
                .fitCenter()
                .placeholder(R.drawable.ic_more_horiz_gray_24dp)
                .error(R.drawable.ic_more_horiz_gray_24dp)
                .into(ivProgram);
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
        mController.onTapChannelProgram(mChannelProgram);
    }

    public interface ControllerChannelProgramItem {
        void onTapChannelProgram(ChannelProgramVO channelProgram);
//        void onTapReminder(ChannelProgramVO channelProgram);
    }
}
