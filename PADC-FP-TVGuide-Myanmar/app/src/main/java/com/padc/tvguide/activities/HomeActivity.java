package com.padc.tvguide.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.padc.tvguide.R;
import com.padc.tvguide.TVGuideApp;
import com.padc.tvguide.controllers.UserController;
import com.padc.tvguide.data.vos.ChannelVO;
import com.padc.tvguide.events.DataEvent;
import com.padc.tvguide.fragments.ChannelListFragment;
import com.padc.tvguide.views.holders.ChannelViewHolder;
import com.padc.tvguide.views.pods.ViewPodAccountControl;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

public class HomeActivity extends BaseActivity
        implements ChannelViewHolder.ControllerChannelItem,
        UserController,
        NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.navigation_view)
    NavigationView navigationView;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    private ViewPodAccountControl vpAccountControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this, this);

        setSupportActionBar(toolbar);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Menu leftMenu = navigationView.getMenu();
        navigationView.setNavigationItemSelectedListener(this);

        vpAccountControl = (ViewPodAccountControl) navigationView.getHeaderView(0);
        vpAccountControl.setUserController(this);

        if (savedInstanceState == null) {
            navigateToHome();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTapChannel(ChannelVO channel, ImageView ivChannel) {
//        Toast.makeText(TVGuideApp.getContext(), "HomeActivity:onTapChannel(): ", Toast.LENGTH_LONG).show();
        Intent intent = ChannelDetailActivity.newIntent();
        startActivity(intent);
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
        fab.setVisibility(View.VISIBLE);
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
        }
        return false;
    }

    private void navigateToHome() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_container, ChannelListFragment.newInstance())
                .commit();
    }

    private void navigateToMyChannel() {

    }

    private void navigateToMyWatchlist() {

    }
}
