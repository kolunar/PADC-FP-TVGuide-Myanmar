package com.padc.tvguide.views.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.padc.tvguide.R;
import com.padc.tvguide.data.vos.MyChannelVO;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by user on 9/10/2016.
 */
public class MyChannelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.tv_my_channel_name)
    TextView tvChannelName;

    @BindView(R.id.iv_my_channel)
    ImageView ivChannel;

    @OnClick(R.id.btn_my_channel)
    public void onTapDeleteMyChannel(View view){
        mController.onTapDeleteMyChannel(mMyChannel);
    }

    private ControllerMyChannelItem mController;
    private MyChannelVO mMyChannel;

    public MyChannelViewHolder(View itemView, ControllerMyChannelItem controller) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
        mController = controller;
    }

    public void bindData(MyChannelVO myChannel) {
        mMyChannel = myChannel;
        tvChannelName.setText(myChannel.getChannelVO().getChannel_name());
//        ivChannel.setImageResource(getImageResourceById(channel.getChannel_id()));

        Glide.with(ivChannel.getContext())
                .load(myChannel.getChannelVO().getChannel_icon())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .fitCenter()
                .placeholder(R.drawable.ic_more_horiz_gray_24dp)
                .error(R.drawable.ic_more_horiz_gray_24dp)
                .into(ivChannel);
    }

    @Override
    public void onClick(View view) {
        mController.onTapMyChannel(mMyChannel);
    }

    public interface ControllerMyChannelItem {
        void onTapMyChannel(MyChannelVO myChannel);
        void onTapDeleteMyChannel(MyChannelVO myChannel);
//        void onLongPressChannel(ChannelVO channel, ImageView ivChannel);
    }
}
