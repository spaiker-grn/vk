package com.spaikergrn.vkclient.activity.mainactivity;

import android.view.MenuItem;

public interface MainActivityContract {

    interface MainActivityView {

        void startLoginActivity();

        void refreshFragment();

        void startSettingsActivity();
    }

    interface MainActivityPresenter {

        void onItemMenuClick(final MenuItem pItem);
    }

}
