package com.spaikergrn.vkclient.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatDelegate;
import android.text.TextUtils;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;

import com.spaikergrn.vkclient.R;
import com.spaikergrn.vkclient.serviceclasses.Constants;

import java.util.List;

public class SettingsActivity extends PreferenceActivity {

    private AppCompatDelegate mDelegate;

    private static final Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {

        @Override
        public boolean onPreferenceChange(final Preference pPreference, final Object pValue) {
            final String stringValue = pValue.toString();

            if (pPreference instanceof ListPreference) {

                final ListPreference listPreference = (ListPreference) pPreference;
                final int index = listPreference.findIndexOfValue(stringValue);

                pPreference.setSummary(
                        index >= 0
                                ? listPreference.getEntries()[index]
                                : null);

            } else if (pPreference instanceof RingtonePreference) {
                if (TextUtils.isEmpty(stringValue)) {
                    pPreference.setSummary(R.string.pref_ringtone_silent);
                } else {
                    final Ringtone ringtone = RingtoneManager.getRingtone(
                            pPreference.getContext(), Uri.parse(stringValue));

                    if (ringtone == null) {
                        pPreference.setSummary(null);
                    } else {
                        final String name = ringtone.getTitle(pPreference.getContext());
                        pPreference.setSummary(name);
                    }
                }
            } else {
                pPreference.setSummary(stringValue);
            }
            return true;
        }
    };

    private static void bindPreferenceSummaryToValue(final Preference pReference) {

        pReference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

        sBindPreferenceSummaryToValueListener.onPreferenceChange(pReference,
                PreferenceManager
                        .getDefaultSharedPreferences(pReference.getContext())
                        .getString(pReference.getKey(), Constants.Parser.EMPTY_STRING));
    }

    @Override
    public boolean onMenuItemSelected(final int pFeatureId, final MenuItem pItem) {
        final int id = pItem.getItemId();
        if (id == android.R.id.home) {
            if (!super.onMenuItemSelected(pFeatureId, pItem)) {
                NavUtils.navigateUpFromSameTask(this);
            }
            return true;
        }
        return super.onMenuItemSelected(pFeatureId, pItem);
    }

    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void onBuildHeaders(final List<Header> pTarget) {
        loadHeadersFromResource(R.xml.pref_headers, pTarget);
    }

    @Override
    protected void onCreate(final Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);
        setupActionBar();
    }

    protected boolean isValidFragment(final String pFragmentName) {
        return PreferenceFragment.class.getName().equals(pFragmentName)
                || PhotoPreferenceFragment.class.getName().equals(pFragmentName)
                || NotificationPreferenceFragment.class.getName().equals(pFragmentName);
    }

    private void setupActionBar() {
        final ActionBar actionBar = getDelegate().getSupportActionBar();
        if (actionBar != null) {
            actionBar.show();

            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class PhotoPreferenceFragment extends PreferenceFragment {

        @Override
        public void onCreate(final Bundle pSavedInstanceState) {
            super.onCreate(pSavedInstanceState);
            addPreferencesFromResource(R.xml.pref_photo);
            setHasOptionsMenu(true);

            bindPreferenceSummaryToValue(findPreference(Constants.PreferencesKeys.PHOTO_SIZE));
        }

        @Override
        public boolean onOptionsItemSelected(final MenuItem pItem) {
            final int id = pItem.getItemId();
            if (id == android.R.id.home) {
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                return true;
            }
            return super.onOptionsItemSelected(pItem);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class NotificationPreferenceFragment extends PreferenceFragment {

        @Override
        public void onCreate(final Bundle pSavedInstanceState) {
            super.onCreate(pSavedInstanceState);
            addPreferencesFromResource(R.xml.pref_notification);
            setHasOptionsMenu(true);

            bindPreferenceSummaryToValue(findPreference(Constants.PreferencesKeys.NOTIFICATIONS_NEW_MESSAGE_RINGTONE));
        }

        @Override
        public boolean onOptionsItemSelected(final MenuItem pItem) {
            final int id = pItem.getItemId();
            if (id == android.R.id.home) {
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                return true;
            }
            return super.onOptionsItemSelected(pItem);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)

    private AppCompatDelegate getDelegate() {
        if (mDelegate == null) {
            mDelegate = AppCompatDelegate.create(this, null);
        }
        return mDelegate;
    }
}
