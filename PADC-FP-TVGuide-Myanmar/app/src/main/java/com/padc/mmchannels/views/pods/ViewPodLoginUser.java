package com.padc.mmchannels.views.pods;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.padc.mmchannels.R;
import com.padc.mmchannels.controllers.BaseController;
import com.padc.mmchannels.controllers.UserController;
import com.padc.mmchannels.controllers.ViewController;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by user on 9/10/2016.
 */
public class ViewPodLoginUser extends RelativeLayout implements ViewController {

    @BindView(R.id.iv_profile_cover)
    ImageView ivProfileCover;

    @BindView(R.id.iv_profile)
    ImageView ivProfile;

    @BindView(R.id.tv_name)
    TextView tvName;

    @BindView(R.id.tv_email)
    TextView tvEmail;

    private UserController mController;

    public ViewPodLoginUser(Context context) {
        super(context);
    }

    public ViewPodLoginUser(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewPodLoginUser(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this, this);
    }

    @Override
    public void setController(BaseController controller) {
        mController = (UserController) controller;
    }

    @OnClick(R.id.btn_logout)
    public void onTapLogout(Button btnLogout) {
        mController.onTapLogout();
    }
}
