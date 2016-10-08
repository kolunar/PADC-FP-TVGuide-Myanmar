package com.padc.mmchannels.views.holders;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.padc.mmchannels.R;
import com.padc.mmchannels.MMChannelsApp;
import com.padc.mmchannels.data.vos.ChannelProgramVO;
import com.padc.mmchannels.utils.DateTimeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @BindView(R.id.btn_set_reminder)
    Button cbAddToReminder;

    @OnClick(R.id.btn_set_reminder)
    public void onTapAddToReminder(Button button){
        mController.onTapReminder(mChannelProgram, button);
    }

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
				.diskCacheStrategy(DiskCacheStrategy.SOURCE)
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
        Log.e(MMChannelsApp.TAG, "ChannelProgramViewHolder.onClick.onTapChannelProgram");
        mController.onTapChannelProgram(mChannelProgram);
    }

    public interface ControllerChannelProgramItem {
        void onTapChannelProgram(ChannelProgramVO channelProgram);
        void onTapReminder(ChannelProgramVO channelProgram, Button button);
    }
}
