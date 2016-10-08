package com.padc.mmchannels.views.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.padc.mmchannels.R;
import com.padc.mmchannels.data.vos.MyWatchListVO;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by user on 9/10/2016.
 */
public class MyWatchListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.tv_wl_program_title)
    TextView tvWatchListItemName;

    @BindView(R.id.iv_wl_program_photo)
    ImageView ivWatchListImage;

    @BindView(R.id.tv_wl_desc)
    TextView tvDescription;

    @OnClick(R.id.btn_wl_delete)
    public void onTapDeleteMyWatchList(View view){
        mController.onTapDeleteMyWatchList(mMyWatchListVO);
    }

    private ControllerMyWatchListItem mController;
    private MyWatchListVO mMyWatchListVO;

    public MyWatchListViewHolder(View itemView, ControllerMyWatchListItem controller) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
        mController = controller;
    }

    public void bindData(MyWatchListVO myWatchListVO) {
        mMyWatchListVO = myWatchListVO;
        tvWatchListItemName.setText(myWatchListVO.getProgram().getProgram_title());
        tvDescription.setText(myWatchListVO.getProgram().getProgram_desc());

//        ivChannel.setImageResource(getImageResourceById(channel.getChannel_id()));

        Glide.with(ivWatchListImage.getContext())
                .load(myWatchListVO.getProgram().getProgram_image())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .fitCenter()
                .placeholder(R.drawable.ic_more_horiz_gray_24dp)
                .error(R.drawable.ic_more_horiz_gray_24dp)
                .into(ivWatchListImage);
    }

    @Override
    public void onClick(View view) {
        mController.onTapMyWatchList(mMyWatchListVO);
    }

    public interface ControllerMyWatchListItem {
        void onTapMyWatchList(MyWatchListVO myChannel);
        void onTapDeleteMyWatchList(MyWatchListVO myChannel);
//        void onLongPressChannel(ChannelVO channel, ImageView ivChannel);
    }
}
