package com.spaikergrn.vk_client.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.spaikergrn.vk_client.TabModel;
import com.spaikergrn.vk_client.fragments.IAdapterRefresh;
import com.spaikergrn.vk_client.fragments.ViewPagerFragment;
import com.spaikergrn.vk_client.fragments.dialogsfragment.DialogsFragment;
import com.spaikergrn.vk_client.fragments.ProfileFragment;
import com.spaikergrn.vk_client.fragments.newsfragment.NewsFragment;

import java.util.ArrayList;
import java.util.List;

public class TabsPagerFragmentAdapter extends FragmentPagerAdapter {

    private final List<TabModel> mTabModelList;
    private IAdapterRefresh mCurrentFragment;

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
            mCurrentFragment = ((IAdapterRefresh) pObject);
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
        tabModelList.add(new TabModel("PROFILE", ProfileFragment.newInstance()));
        tabModelList.add(new TabModel("NEWS", NewsFragment.newInstance()));
        tabModelList.add(new TabModel("MESSAGES", DialogsFragment.newInstance()));
        tabModelList.add(new TabModel("EX", ViewPagerFragment.newInstance()));
        return tabModelList;
    }
    public IAdapterRefresh getCurrentFragment() {
        return mCurrentFragment;
    }
}
