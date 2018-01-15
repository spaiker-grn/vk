package com.spaikergrn.vk_client;

import android.support.v4.app.Fragment;

public class TabModel {

    private String mName;
    private Fragment mFragment;

    public TabModel(final String pName, final Fragment pFragment) {
        mName = pName;
        mFragment = pFragment;
    }

    public Fragment getFragment() {
        return mFragment;
    }

    public void setFragment(final Fragment pFragment) {
        mFragment = pFragment;
    }

    public void setName(final String pName) {
        mName = pName;
    }

    public String getName() {
        return mName;
    }
}
