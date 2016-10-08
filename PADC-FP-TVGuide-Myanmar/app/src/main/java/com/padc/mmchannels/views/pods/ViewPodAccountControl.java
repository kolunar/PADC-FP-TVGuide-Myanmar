package com.padc.mmchannels.views.pods;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.padc.mmchannels.R;
import com.padc.mmchannels.controllers.UserController;
import com.padc.mmchannels.events.DataEvent;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by user on 9/10/2016.
 */
public class ViewPodAccountControl extends FrameLayout {
    @BindView(R.id.vp_login_user)
    ViewPodLoginUser vpLoginUser;

    @BindView(R.id.vp_logout_user)
    ViewPodLogoutUser vpLogoutUser;

    public ViewPodAccountControl(Context context) {
        super(context);
    }

    public ViewPodAccountControl(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewPodAccountControl(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this, this);

        EventBus eventBus = EventBus.getDefault();
        if (!eventBus.isRegistered(this)) {
            eventBus.register(this);
        }
    }

    boolean isUserLogin = false;
    private void refreshUserLoginStatus() {
        isUserLogin = isUserLogin ? false : true;
        vpLogoutUser.setVisibility(isUserLogin ? View.GONE : View.VISIBLE);
        vpLoginUser.setVisibility(isUserLogin ? View.VISIBLE : View.GONE);

    }

    public void setUserController(UserController userController) {
        vpLogoutUser.setController(userController);
        vpLoginUser.setController(userController);
    }

    public void onEventMainThread(DataEvent.RefreshUserLoginStatusEvent event) {
        refreshUserLoginStatus();
    }
}
