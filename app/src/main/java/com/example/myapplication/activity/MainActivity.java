package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.activity.LoginActivity;
import com.example.myapplication.adapters.TabsPagerFragmentAdapter;
import com.example.myapplication.serviceclasses.Constants;
import com.example.myapplication.services.LongPollService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(this, LongPollService.class));
        initTabs();
        startActivityForResult(new Intent(this, LoginActivity.class), Constants.LOGIN_ACTIVITY_REQUEST_CODE);

    }

    private void initTabs() {
        final TabLayout tabLayout = findViewById(R.id.tab_layout);
        final ViewPager viewPager = findViewById(R.id.view_pager);
        final TabsPagerFragmentAdapter adapter = new TabsPagerFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, LongPollService.class));
        super.onDestroy();
    }
}
