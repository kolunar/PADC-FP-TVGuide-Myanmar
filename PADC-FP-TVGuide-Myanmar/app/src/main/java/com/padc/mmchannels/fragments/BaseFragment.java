package com.padc.mmchannels.fragments;

import android.support.v4.app.Fragment;

/**
 * Created by user on 9/10/2016.
 */
public abstract class BaseFragment extends Fragment {

    @Override
    public void onStart() {
        super.onStart();
    }

    public interface ControllerFragment {
        void onSwipeRefresh(int fragmentNo);
        void onSwitchFragment(int fragmentNo);
    }
}
