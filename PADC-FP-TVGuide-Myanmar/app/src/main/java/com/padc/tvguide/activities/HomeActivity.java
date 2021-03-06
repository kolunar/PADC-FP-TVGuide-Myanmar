package com.padc.tvguide.activities;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.padc.tvguide.R;
import com.padc.tvguide.TVGuideApp;
import com.padc.tvguide.controllers.UserController;
import com.padc.tvguide.data.models.ChannelModel;
import com.padc.tvguide.data.vos.ChannelVO;
import com.padc.tvguide.data.vos.MyChannelVO;
import com.padc.tvguide.data.vos.ProgramVO;
import com.padc.tvguide.events.DataEvent;
import com.padc.tvguide.fragments.BaseFragment;
import com.padc.tvguide.fragments.ChannelListFragment;
import com.padc.tvguide.fragments.MyChannelFragment;
import com.padc.tvguide.fragments.MyReminderFragment;
import com.padc.tvguide.fragments.MyWatchListFragment;
import com.padc.tvguide.views.holders.ChannelViewHolder;
import com.padc.tvguide.views.holders.MyChannelViewHolder;
import com.padc.tvguide.views.holders.ProgramViewHolder;
import com.padc.tvguide.views.pods.ViewPodAccountControl;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

public class HomeActivity extends BaseActivity
        implements ChannelViewHolder.ControllerChannelItem,
        ProgramViewHolder.ControllerProgramItem,
        MyChannelViewHolder.ControllerMyChannelItem,
        UserController,
        NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.navigation_view)
    NavigationView navigationView;

    @BindView(R.id.fl_container)
    FrameLayout mFrameLayout;

    private ViewPodAccountControl vpAccountControl;

    private boolean isInChoiceMode;
    public static List<Integer> selectedItemIdList;

    @BindView(R.id.toolbar_custom_layout_container)
    LinearLayout llToolbarContainer;

//    @BindView(R.id.tv_toolbar_counter)
//    TextView tvToolbarCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this, this);

        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();

//      Menu leftMenu = navigationView.getMenu();
        navigationView.setNavigationItemSelectedListener(this);

        vpAccountControl = (ViewPodAccountControl) navigationView.getHeaderView(0);
        vpAccountControl.setUserController(this);

        if (savedInstanceState == null) {
            Toast.makeText(TVGuideApp.getContext(), "HomeActivity:onCreate():savedInstanceState==null ", Toast.LENGTH_LONG).show();
            selectedItemIdList = new ArrayList<>();
            isInChoiceMode = false;
            navigateToHome();
        }
        else {
            Toast.makeText(TVGuideApp.getContext(), "HomeActivity:onCreate():savedInstanceState!=null ", Toast.LENGTH_LONG).show();
            selectedItemIdList = savedInstanceState.getIntegerArrayList("selectedItemIdList");
            isInChoiceMode = savedInstanceState.getBoolean("isInChoiceMode");
        }

        llToolbarContainer.setVisibility(View.INVISIBLE);
        if(actionBar != null){
            actionBar.setDisplayShowTitleEnabled(true);
/*            String label = null;
            try {
                label = getResources().getString(
                        getPackageManager().getActivityInfo(getComponentName(), 0).labelRes);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            actionBar.setTitle("TV Guide");*/
            if(isInChoiceMode){
                actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
            }
            else {
                actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
            }
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        if (isInChoiceMode) {
            /*inflate the action mode menu if we're in choice mode*/
            getMenuInflater().inflate(R.menu.action_mode_menu, menu);
            /*show the save icon in the toolbar*/
            MenuItem saveSelectedItemsMenuItem = toolbar.getMenu().findItem(R.id.action_mode_menu_save);
            saveSelectedItemsMenuItem.setIcon(R.drawable.ic_done_white_24dp);
            /*update the selected items counter in the toolbar*/
            updateToolbar();
        } else {
            getMenuInflater().inflate(R.menu.menu_home, menu);
        }
        return true;
    }

    /*updates the counter on toolbar to show
    * how many items have been selected*/
    private void updateToolbar() {
        int selectedItemsCounter = selectedItemIdList.size();
        /*show the counter if there are selected items*/
        if (selectedItemsCounter > 0) {
//            tvToolbarCounter.setVisibility(View.VISIBLE);
//            tvToolbarCounter.setText(String.valueOf(selectedItemsCounter));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, as long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings:

                return true;
            case android.R.id.home:
                if(isInChoiceMode){

                }
                else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putIntegerArrayList("selectedItemIdList", (ArrayList<Integer>) selectedItemIdList);
        outState.putBoolean("isInChoiceMode", isInChoiceMode);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onTapProgram(ProgramVO program, ImageView ivProgram) {
        Intent intent = ProgramDetailActivity.newIntent(program);//"Program Detail");
        startActivity(intent);
    }

    @Override
    public void onTapChannel(ChannelVO channel, int drawableID) {
//        Toast.makeText(TVGuideApp.getContext(), "HomeActivity:onTapChannel(): ", Toast.LENGTH_LONG).show();
//        Intent intent = ChannelDetailActivity.newIntent(drawableID);
        Intent intent = ChannelDetailActivity.newIntent(channel);
        startActivity(intent);
    }

    @Override
    public void onTapMyChannel(MyChannelVO myChannel) {
        Intent intent = ChannelDetailActivity.newIntent(myChannel.getChannelVO());
        startActivity(intent);
    }

    @Override
    public void onLongPressChannel(ChannelVO channel, ImageView ivChannel) {

    }

    @Override
    public void onTapSaveMyChannel(ChannelVO channel){
        MyChannelVO.saveMyChannel(new MyChannelVO(0, channel.getChannel_id()));
    }

    @Override
    public void onTapDeleteMyChannel(ChannelVO channel){
        MyChannelVO.deleteMyChannel(0, channel.getChannel_id());
    }

    boolean isUserLogin = false;
    @Override
    public void onTapLogin() {
        if(!isUserLogin) {
            DataEvent.RefreshUserLoginStatusEvent event = new DataEvent.RefreshUserLoginStatusEvent();
            EventBus.getDefault().post(event);
            isUserLogin = true;
        }
    }

    @Override
    public void onTapLogout() {
        if(isUserLogin) {
            DataEvent.RefreshUserLoginStatusEvent event = new DataEvent.RefreshUserLoginStatusEvent();
            EventBus.getDefault().post(event);
            isUserLogin = false;
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);

        switch (item.getItemId()) {
            case R.id.nav_tv_guide_home:
                navigateToHome();
                return true;
            case R.id.nav_tv_guide_my_channels:
                navigateToMyChannel();
                return true;
            case R.id.nav_tv_guide_my_watchlist:
                navigateToMyWatchlist();
                return true;
            case R.id.nav_tv_guide_my_reminders:
                navigateToMyReminder();
                return true;
        }
        return false;
    }

    ChannelListFragment mChannelListFragment = null;
    MyChannelFragment mMyChannelFragment = null;
    MyWatchListFragment mMyWatchListFragment = null;
    MyReminderFragment mMyReminderFragment = null;
    BaseFragment mActiveFragment = null;

    private void navigateToHome() {
        if(mChannelListFragment == null) {
            mChannelListFragment = ChannelListFragment.newInstance();
/*            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_container, mChannelListFragment)
                    .commit(); */
/*            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fl_container, mChannelListFragment, "HOME")
                    .commit();*/
        }
        if(mChannelListFragment != null){
//            myFragment.onDestroy();
//            getSupportFragmentManager().beginTransaction().remove(mChannelListFragment).commit();
//            mFrameLayout.removeAllViews();
//            mFrameLayout.refreshDrawableState();
        }
//        mActiveFragment = mChannelListFragment;

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_container, mChannelListFragment, "HOME")
                .commit();

        toolbar.setTitle(R.string.app_name);
    }

    private void navigateToMyChannel() {
        if(mMyChannelFragment == null) {
            mMyChannelFragment = MyChannelFragment.newInstance();
        }
        if(mMyChannelFragment != null){
//            mFrameLayout.refreshDrawableState();
        }
        toolbar.setTitle("My Channels");
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_container, mMyChannelFragment)
                .commit();
    }

    private void navigateToMyWatchlist() {
        if(mMyWatchListFragment == null) {
            mMyWatchListFragment = MyWatchListFragment.newInstance();
        }
        if(mMyWatchListFragment != null){
//            mFrameLayout.refreshDrawableState();
        }
        toolbar.setTitle("My Watchlist");
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_container, mMyWatchListFragment)
                .commit();
    }

    private void navigateToMyReminder() {
        if(mMyReminderFragment == null) {
            mMyReminderFragment = MyReminderFragment.newInstance();
        }
        if(mMyReminderFragment != null){
//            mFrameLayout.refreshDrawableState();
        }
        toolbar.setTitle("My Reminder");
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_container, mMyReminderFragment)
                .commit();
    }

    /*cancel any selections in long pressed mode*/
    private void cancelSelections() {
        /*empty the list of selected ids*/
        selectedItemIdList.clear();
        /*notify adapter so we can reset the item display to normal
        * -LayoutManagers will be forced to fully rebind and relayout all visible views*/
        if(mChannelListFragment != null) {
            mChannelListFragment.notifyDataSetChanged();
        }
    }


}
