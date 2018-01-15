package com.spaikergrn.vk_client.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.spaikergrn.vk_client.R;
import com.spaikergrn.vk_client.adapters.TabsPagerFragmentAdapter;
import com.spaikergrn.vk_client.clients.WebViewClientLogin;
import com.spaikergrn.vk_client.db.DbOperations;
import com.spaikergrn.vk_client.db.SQLHelper;
import com.spaikergrn.vk_client.fragments.IAdapterRefresh;
import com.spaikergrn.vk_client.serviceclasses.Constants;
import com.spaikergrn.vk_client.services.LongPollService;

public class MainActivity extends AppCompatActivity {

    private TabsPagerFragmentAdapter mAdapter;
    IAdapterRefresh mAdapterRefresh;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTabs();
        startService(new Intent(this, LongPollService.class));
        getSupportActionBar().setElevation(0);
    }

    @Override
    protected void onActivityResult(final int pRequestCode, final int pResultCode, final Intent pData) {
        super.onActivityResult(pRequestCode, pResultCode, pData);
    }

    private void initTabs() {
        final TabLayout tabLayout = findViewById(R.id.tab_layout);
        final ViewPager viewPager = findViewById(R.id.view_pager);
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
                startActivity(new Intent(this, LoginActivity.class).putExtra(Constants.Parser.URL, Constants.HTTPS_VK_COM));
                break;
            case R.id.settings_options_menu:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
            case R.id.clear_db_options_menu:
                final SQLHelper UsersDb = new SQLHelper(this);
                final DbOperations dbOperations = new DbOperations(UsersDb);
                int count = dbOperations.delete(Constants.USERS_DB, null, null);
                Log.d(Constants.MY_TAG, "onOptionsItemSelected clear UserDB count cleared rows: " + count);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, LongPollService.class));
        super.onDestroy();
    }
}
