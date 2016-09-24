package com.padc.tvguide.views.holders;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.padc.tvguide.R;
import com.padc.tvguide.data.vos.ChannelVO;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 9/10/2016.
 */
public class ChannelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.tv_channel_name)
    TextView tvChannelName;

    @BindView(R.id.iv_channel)
    ImageView ivChannel;

    private ControllerChannelItem mController;
    private ChannelVO mChannel;

    public ChannelViewHolder(View itemView, ControllerChannelItem controller) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
        mController = controller;
    }

    public void bindData(ChannelVO channel) {
        mChannel = channel;
        tvChannelName.setText(channel.getName());
        ivChannel.setImageResource(getImageResourceById(channel.getId()));
    }

    private int getImageResourceById(int id){
        switch(id){
            case 0:
                return R.drawable.logo_4_tv_mm;
            case 1:
                return R.drawable.logo_4_tv_mm_comedy;
            case 2:
                return R.drawable.logo_4_tv_mm_edu;
            case 3:
                return R.drawable.logo_4_tv_mm_info;
            case 4:
                return R.drawable.logo_4_tv_mm_ladies;
            case 5:
                return R.drawable.logo_4_tv_mm_sports;
            case 6:
                return R.drawable.logo_4_tv_mm_tunes;
            case 7:
                return R.drawable.logo_5_cartoons;
            case 8:
                return R.drawable.logo_5_movies;
            case 9:
                return R.drawable.logo_5_plus_mm;
            case 10:
                return R.drawable.logo_5_special_mm;
            case 11:
                return R.drawable.logo_buddha_tv_mm;
        }
        return R.drawable.drawer_background;
    }

    @Override
    public void onClick(View view) {
        mController.onTapChannel(mChannel, getImageResourceById(mChannel.getId()));
    }

    public interface ControllerChannelItem {
        void onTapChannel(ChannelVO channel, int drawableID);
        void onLongPressChannel(ChannelVO channel, ImageView ivChannel);
    }
}
