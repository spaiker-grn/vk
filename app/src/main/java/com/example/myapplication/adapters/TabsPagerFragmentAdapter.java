package com.example.myapplication.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/*import com.example.myapplication.TabModel;*/
import com.example.myapplication.TabModel;
import com.example.myapplication.fragments.dialogsfragment.DialogsFragment;
import com.example.myapplication.fragments.NewsFeedFragment;
import com.example.myapplication.fragments.ProfileFragment;

import java.util.ArrayList;
import java.util.List;

public class TabsPagerFragmentAdapter extends FragmentPagerAdapter {

    private final List<TabModel> mTabModelList;

    public TabsPagerFragmentAdapter(final FragmentManager pFragmentManager) {
        super(pFragmentManager);

        mTabModelList = addTabs();

    }

    @Override
    public CharSequence getPageTitle(final int pPosition) {
        return mTabModelList.get(pPosition).getName();
    }

    @Override
    public Fragment getItem(final int pPosition) {

        return mTabModelList.get(pPosition).getFragment();

    }

    @Override
    public int getCount() {
        return mTabModelList.size();
    }

    private List<TabModel> addTabs() {
        final List<TabModel> tabModelList = new ArrayList<>();
        tabModelList.add(new TabModel("PROFILE", ProfileFragment.newInstance()));
        tabModelList.add(new TabModel("NEWS", NewsFeedFragment.newInstance()));
        tabModelList.add(new TabModel("MESSAGES", DialogsFragment.newInstance()));
        return tabModelList;
    }
}
