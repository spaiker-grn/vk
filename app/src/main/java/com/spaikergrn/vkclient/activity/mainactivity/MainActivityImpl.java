package com.spaikergrn.vkclient.activity.mainactivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.spaikergrn.vkclient.R;
import com.spaikergrn.vkclient.activity.LoginActivity;
import com.spaikergrn.vkclient.activity.SettingsActivity;
import com.spaikergrn.vkclient.adapters.TabsPagerFragmentAdapter;
import com.spaikergrn.vkclient.serviceclasses.Constants;
import com.spaikergrn.vkclient.services.LongPollService;
import com.spaikergrn.vkclient.tools.NetworkUtil;

public class MainActivityImpl extends AppCompatActivity implements MainActivity {

    private TextView mCheckConnectionTextView;
    private TabsPagerFragmentAdapter mTabsPagerFragmentAdapter;
    private final MainActivityPresenter mPresenter = new MainActivityPresenterImpl(this);

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTabsPagerFragmentAdapter = new TabsPagerFragmentAdapter(getSupportFragmentManager());
        initTabs();
        mPresenter.onCreate();
    }

    private void initTabs() {
        final TabLayout tabLayout = findViewById(R.id.tab_layout);
        final ViewPager viewPager = findViewById(R.id.view_pager);
        mCheckConnectionTextView = findViewById(R.id.tv_check_connection);
        viewPager.setAdapter(mTabsPagerFragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);
        registerReceiver(networkStateReceiver, getIntentFilter());
        startService(new Intent(this, LongPollService.class));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setElevation(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        mPresenter.onItemMenuClick(item);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void refreshFragment() {
        mTabsPagerFragmentAdapter.getCurrentFragment().refresh();
    }

    @Override
    public void startLoginActivity() {
        startActivity(new Intent(this, LoginActivity.class).putExtra(Constants.Parser.URL, Constants.HTTPS_VK_COM));
    }

    @Override
    public void startSettingsActivity() {
       startActivity(new Intent(this, SettingsActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(getApplicationContext(), LongPollService.class));
        unregisterReceiver(networkStateReceiver);
        mPresenter.onDestroy();
    }

    @NonNull
    private IntentFilter getIntentFilter() {
        final IntentFilter networkStateFilter = new IntentFilter(Constants.ANDROID_NET_CONN_CONNECTIVITY_CHANGE);
        networkStateFilter.addAction(Constants.ANDROID_NET_WIFI_WIFI_STATE_CHANGED);
        return networkStateFilter;
    }

    private final BroadcastReceiver networkStateReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(final Context pContext, final Intent pIntent) {
            final boolean isOnline = NetworkUtil.getConnectivityStatus(pContext);

            if (isOnline) {
                pContext.startService(new Intent(pContext, LongPollService.class));
                hideViewOffline();
            } else {
                pContext.stopService(new Intent(pContext, LongPollService.class));
                showViewOffline();
            }

        }
    };

    public void showViewOffline() {
        mCheckConnectionTextView.setVisibility(View.VISIBLE);
        mCheckConnectionTextView.setText(Constants.COULD_NOT_CONNECT_TO_INTERNET);
        mCheckConnectionTextView.setBackgroundColor(Color.RED);
        mCheckConnectionTextView.setTextColor(Color.WHITE);
    }

    public void hideViewOffline() {
        mCheckConnectionTextView.setVisibility(View.GONE);
    }

}