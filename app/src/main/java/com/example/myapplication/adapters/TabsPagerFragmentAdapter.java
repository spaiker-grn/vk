package com.example.myapplication.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.myapplication.TabModel;
import com.example.myapplication.fragments.DialogsFragment.DialogsFragment;
import com.example.myapplication.fragments.NewsFeedFragment;
import com.example.myapplication.fragments.ProfileFragment;
import com.example.myapplication.fragments.ViewPagerFragment;

/**
 * Created by Дмитрий on 04.11.2017.
 */

public class TabsPagerFragmentAdapter extends FragmentPagerAdapter {


    private final String[] mTabs;

    public TabsPagerFragmentAdapter(final FragmentManager pFragmentManager) {
        super(pFragmentManager);

        List<TabModel> tabModelList = .......;
        mTabs = new String[]{"ПРОФИЛЬ", "НОВОСТИ", "СООБЩЕНИЯ", "Example"};

    }


    @Override
    public CharSequence getPageTitle(final int pPosition) {
        return mTabs[pPosition];
    }

    @Override
    public Fragment getItem(final int pPosition) {
        switch (pPosition){
            case 0:
                return ProfileFragment.getInstance();

            case 1:
                return NewsFeedFragment.getInstance();

            case 2:
                return DialogsFragment.getInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return mTabs.length;
    }
}
