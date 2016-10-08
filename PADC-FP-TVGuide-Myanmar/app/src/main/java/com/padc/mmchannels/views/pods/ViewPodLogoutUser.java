package com.padc.mmchannels.views.pods;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.padc.mmchannels.R;
import com.padc.mmchannels.controllers.BaseController;
import com.padc.mmchannels.controllers.UserController;
import com.padc.mmchannels.controllers.ViewController;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by user on 9/10/2016.
 */
public class ViewPodLogoutUser extends RelativeLayout implements ViewController {

    private UserController mController;

    public ViewPodLogoutUser(Context context) {
        super(context);
    }

    public ViewPodLogoutUser(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewPodLogoutUser(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this, this);
    }

    @OnClick(R.id.btn_login)
    public void onTapLogin(View view) {
        mController.onTapLogin();
    }

    @Override
    public void setController(BaseController controller) {
        mController = (UserController) controller;
    }
}
