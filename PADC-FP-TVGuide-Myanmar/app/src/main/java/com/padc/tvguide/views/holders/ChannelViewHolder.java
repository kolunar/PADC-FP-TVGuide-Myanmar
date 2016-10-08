package com.padc.tvguide.views.holders;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.padc.tvguide.R;
import com.padc.tvguide.TVGuideApp;
import com.padc.tvguide.data.vos.ChannelVO;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by user on 9/10/2016.
 */
public class ChannelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.tv_channel_name)
    TextView tvChannelName;

    @BindView(R.id.iv_channel)
    ImageView ivChannel;

    @BindView(R.id.cb_channel)
    CheckBox cbChannel;

    @OnClick(R.id.cb_channel)
    public void onTapMyChannelCheckBox(View view){
        CheckBox checkBox = (CheckBox)view;
        if(checkBox.isChecked()){
            mChannel.setMyChannel(true);
            mController.onTapSaveMyChannel(mChannel);
        }
        else{
//            Toast.makeText(TVGuideApp.getContext(), "ChannelViewHolder:onTapMyChannelCheckBox(): unchecked", Toast.LENGTH_SHORT).show();
            mChannel.setMyChannel(false);
            mController.onTapDeleteMyChannel(mChannel);
        }
    }

    private ControllerChannelItem mController;
    private ChannelVO mChannel;

    public ChannelViewHolder(View itemView, ControllerChannelItem controller) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
        mController = controller;
    }

    public void bindData(ChannelVO channel) {
        Log.e(TVGuideApp.TAG, "ChannelViewHolder.bindData().channel_id = " + channel.getChannel_id());
        mChannel = channel;
        tvChannelName.setText(channel.getChannel_name());
//        ivChannel.setImageResource(getImageResourceById(channel.getChannel_id()));
        cbChannel.setChecked(channel.isMyChannel());
        Glide.with(ivChannel.getContext())
                .load(channel.getChannel_icon())
				.diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .fitCenter()
                .placeholder(R.drawable.ic_more_horiz_gray_24dp)
                .error(R.drawable.ic_more_horiz_gray_24dp)
                .into(ivChannel);
    }

    private int getImageResourceById(int id){
        switch(id){
            case 100:
                return R.drawable.logo_4_tv_mm;
            case 101:
                return R.drawable.logo_4_tv_mm_comedy;
            case 102:
                return R.drawable.logo_4_tv_mm_edu;
            case 103:
                return R.drawable.logo_4_tv_mm_info;
            case 104:
                return R.drawable.logo_4_tv_mm_ladies;
            case 105:
                return R.drawable.logo_4_tv_mm_sports;
            case 106:
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
        return R.drawable.ic_more_horiz_gray_24dp;
    }

    @Override
    public void onClick(View view) {
        mController.onTapChannel(mChannel, getImageResourceById((int)mChannel.getChannel_id()));
    }

    public interface ControllerChannelItem {
        void onTapChannel(ChannelVO channel, int drawableID);
        void onTapSaveMyChannel(ChannelVO myChannel);
        void onTapDeleteMyChannel(ChannelVO myChannel);
        void onLongPressChannel(ChannelVO channel, ImageView ivChannel);
    }
}
