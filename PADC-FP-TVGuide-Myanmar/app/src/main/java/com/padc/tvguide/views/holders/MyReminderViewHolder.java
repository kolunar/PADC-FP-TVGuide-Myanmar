package com.padc.tvguide.views.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.padc.tvguide.R;
import com.padc.tvguide.data.vos.MyReminderVO;
import com.padc.tvguide.utils.DateTimeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by user on 9/10/2016.
 */
public class MyReminderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.tv_rm_program_title)
    TextView tvReminderItemName;

    @BindView(R.id.iv_rm_program_photo)
    ImageView ivReminderImage;

    @BindView(R.id.tv_rm_duration)
    TextView tvDuration;

    @BindView(R.id.tv_rm_period)
    TextView tvPeriod;

    @BindView(R.id.btn_rm_set_reminder)
    Button btnSetReminder;

    @OnClick(R.id.btn_rm_set_reminder)
    public void onTapSetMyReminder(View view){
        mController.onTapSetMyReminder(mMyReminderVO);
    }

    @OnClick(R.id.btn_rm_delete)
    public void onTapDeleteMyReminder(View view){
        mController.onTapDeleteMyReminder(mMyReminderVO);
    }

    private ControllerMyReminderItem mController;
    private MyReminderVO mMyReminderVO;

    public MyReminderViewHolder(View itemView, ControllerMyReminderItem controller) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
        mController = controller;
    }

    public void bindData(MyReminderVO myReminderVO) {
        mMyReminderVO = myReminderVO;
        tvReminderItemName.setText(myReminderVO.getChannelProgramVO().getProgram().getProgram_title());

        tvPeriod.setText(DateTimeUtils.getTimePeriod(myReminderVO.getChannelProgramVO().getStart_time(),
                myReminderVO.getChannelProgramVO().getDuration()));
        tvDuration.setText(DateTimeUtils.getDuration(myReminderVO.getChannelProgramVO().getDuration()));
//        ivChannel.setImageResource(getImageResourceById(channel.getChannel_id()));

        Glide.with(ivReminderImage.getContext())
                .load(myReminderVO.getChannelProgramVO().getProgram().getProgram_image())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .fitCenter()
                .placeholder(R.drawable.ic_more_horiz_gray_24dp)
                .error(R.drawable.ic_more_horiz_gray_24dp)
                .into(ivReminderImage);
    }

    @Override
    public void onClick(View view) {
        mController.onTapMyReminder(mMyReminderVO);
    }

    public interface ControllerMyReminderItem {
        void onTapMyReminder(MyReminderVO myReminder);
        void onTapDeleteMyReminder(MyReminderVO myReminder);
        void onTapSetMyReminder(MyReminderVO myReminder);
//        void onLongPressChannel(ChannelVO channel, ImageView ivChannel);
    }
}
