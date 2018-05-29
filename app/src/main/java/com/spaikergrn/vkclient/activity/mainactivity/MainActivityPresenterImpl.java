package com.spaikergrn.vkclient.activity.mainactivity;

import android.view.MenuItem;

import com.spaikergrn.vkclient.R;
import com.spaikergrn.vkclient.clients.WebViewClientLogin;
import com.spaikergrn.vkclient.db.DbOperations;
import com.spaikergrn.vkclient.db.SQLHelper;
import com.spaikergrn.vkclient.serviceclasses.Constants;
import com.spaikergrn.vkclient.serviceclasses.ContextHolder;
import com.spaikergrn.vkclient.serviceclasses.ProfileInfoHolder;

public class MainActivityPresenterImpl implements MainActivityPresenter {

    private final MainActivity mView;

    MainActivityPresenterImpl(final MainActivity pMainActivity) {
        mView = pMainActivity;

    }

    @Override
    public void onItemMenuClick(final MenuItem pMenuItem) {
        switch (pMenuItem.getItemId()) {
            case R.id.refresh_options_menu:
                mView.refreshFragment();
                break;
            case R.id.reLogin_options_menu:
                reLogin();
                break;
            case R.id.settings_options_menu:
                mView.startSettingsActivity();
                break;
            case R.id.clear_db_options_menu:
                final SQLHelper UsersDb = new SQLHelper(ContextHolder.getContext());
                final DbOperations dbOperations = new DbOperations(UsersDb);
                dbOperations.delete(Constants.USERS_DB, null, null);
                break;
        }
    }

    private void reLogin() {
        WebViewClientLogin.deleteToken();
        ProfileInfoHolder.deleteProfileInfoPreferences();
        mView.startLoginActivity();
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {

    }
}
