package com.spaikergrn.vkclient.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.spaikergrn.vkclient.TabModel;
import com.spaikergrn.vkclient.fragments.IFragmentAdapterRefresh;
import com.spaikergrn.vkclient.fragments.dialogsfragment.DialogsFragment;
import com.spaikergrn.vkclient.fragments.profilefragment.ProfileFragment;
import com.spaikergrn.vkclient.fragments.newsfragment.NewsFragment;
import com.spaikergrn.vkclient.serviceclasses.Constants;

import java.util.ArrayList;
import java.util.List;

public class TabsPagerFragmentAdapter extends FragmentPagerAdapter {

    private final List<TabModel> mTabModelList;
    private IFragmentAdapterRefresh mCurrentFragment;

    public TabsPagerFragmentAdapter(final FragmentManager pFragmentManager) {
        super(pFragmentManager);
        mTabModelList = addTabs();

    }

    @Override
    public CharSequence getPageTitle(final int pPosition) {
        return mTabModelList.get(pPosition).getName();
    }

    @Override
    public void setPrimaryItem(final ViewGroup pContainer, final int pPosition, final Object pObject) {
        if (getCurrentFragment() != pObject) {
            mCurrentFragment = ((IFragmentAdapterRefresh) pObject);
        }
        super.setPrimaryItem(pContainer, pPosition, pObject);
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
        tabModelList.add(new TabModel(Constants.Tabs.PROFILE, ProfileFragment.newInstance()));
        tabModelList.add(new TabModel(Constants.Tabs.NEWS, NewsFragment.newInstance()));
        tabModelList.add(new TabModel(Constants.Tabs.MESSAGES, DialogsFragment.newInstance()));
        return tabModelList;
    }
    public IFragmentAdapterRefresh getCurrentFragment() {
        return mCurrentFragment;
    }
}
