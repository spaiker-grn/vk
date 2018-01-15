package com.spaikergrn.vkclient.activity;

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
import com.spaikergrn.vkclient.adapters.TabsPagerFragmentAdapter;
import com.spaikergrn.vkclient.clients.WebViewClientLogin;
import com.spaikergrn.vkclient.db.DbOperations;
import com.spaikergrn.vkclient.db.SQLHelper;
import com.spaikergrn.vkclient.fragments.IFragmentAdapterRefresh;
import com.spaikergrn.vkclient.serviceclasses.Constants;
import com.spaikergrn.vkclient.serviceclasses.ProfileInfoHolder;
import com.spaikergrn.vkclient.services.LongPollService;
import com.spaikergrn.vkclient.tools.NetworkUtil;

public class MainActivity extends AppCompatActivity {

    private TabsPagerFragmentAdapter mAdapter;
    IFragmentAdapterRefresh mAdapterRefresh;
    private TextView mCheckConnectionTextView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTabs();
        registerReceiver(networkStateReceiver,getIntentFilter());
        startService(new Intent(this, LongPollService.class));
        if (getSupportActionBar() != null){
            getSupportActionBar().setElevation(0);
        }
    }

    @NonNull
    private IntentFilter getIntentFilter() {
        final IntentFilter networkStateFilter = new IntentFilter(Constants.ANDROID_NET_CONN_CONNECTIVITY_CHANGE);
        networkStateFilter.addAction(Constants.ANDROID_NET_WIFI_WIFI_STATE_CHANGED);
        return networkStateFilter;
    }

    private void initTabs() {
        final TabLayout tabLayout = findViewById(R.id.tab_layout);
        final ViewPager viewPager = findViewById(R.id.view_pager);
        mCheckConnectionTextView = findViewById(R.id.tv_check_connection);
        mAdapter = new TabsPagerFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()){
            case R.id.refresh_options_menu:
                mAdapterRefresh = mAdapter.getCurrentFragment();
                mAdapterRefresh.refresh();
                break;
            case R.id.reLogin_options_menu:
                WebViewClientLogin.deleteToken();
                ProfileInfoHolder.deleteProfileInfoPreferences();
                startActivity(new Intent(this, LoginActivity.class).putExtra(Constants.Parser.URL, Constants.HTTPS_VK_COM));
                break;
            case R.id.settings_options_menu:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
            case R.id.clear_db_options_menu:
                final SQLHelper UsersDb = new SQLHelper(this);
                final DbOperations dbOperations = new DbOperations(UsersDb);
                dbOperations.delete(Constants.USERS_DB, null, null);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    BroadcastReceiver networkStateReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(final Context pContext, final Intent pIntent) {
            final boolean isOnline = NetworkUtil.getConnectivityStatus(pContext);
            if(isOnline){
                pContext.startService(new Intent(pContext, LongPollService.class));
            }
            checkInternetDialog(isOnline);
        }
    };

    private void checkInternetDialog(final boolean pValue){

        if(!pValue){
            mCheckConnectionTextView.setVisibility(View.VISIBLE);
            mCheckConnectionTextView.setText(Constants.COULD_NOT_CONNECT_TO_INTERNET);
            mCheckConnectionTextView.setBackgroundColor(Color.RED);
            mCheckConnectionTextView.setTextColor(Color.WHITE);
        } else {
            mCheckConnectionTextView.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, LongPollService.class));
        unregisterReceiver(networkStateReceiver);
        super.onDestroy();
    }
}