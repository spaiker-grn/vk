package com.spaikergrn.vkclient.activity.mainactivity;

import android.view.MenuItem;

import com.spaikergrn.vkclient.activity.Presenter;

interface MainActivityPresenter extends Presenter {

    void onItemMenuClick(final MenuItem pItem);
}
