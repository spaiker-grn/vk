package com.spaikergrn.vkclient;

import android.support.v4.app.Fragment;

public class TabModel {

    private String mName;
    private final Fragment mFragment;

    public TabModel(final String pName, final Fragment pFragment) {
        mName = pName;
        mFragment = pFragment;
    }

    public Fragment getFragment() {
        return mFragment;
    }

    public void setName(final String pName) {
        mName = pName;
    }

    public String getName() {
        return mName;
    }
}
