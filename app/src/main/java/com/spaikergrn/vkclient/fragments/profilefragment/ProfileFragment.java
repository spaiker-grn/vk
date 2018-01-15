package com.spaikergrn.vkclient.fragments.profilefragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spaikergrn.vkclient.R;
import com.spaikergrn.vkclient.fragments.IFragmentAdapterRefresh;
import com.spaikergrn.vkclient.serviceclasses.ProfileInfoHolder;

public class ProfileFragment extends Fragment implements IFragmentAdapterRefresh {

    public static ProfileFragment newInstance() {
        final Bundle bundle = new Bundle();
        final ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);

    }

    @Override
    public void refresh() {
        ProfileInfoHolder.deleteProfileInfoPreferences();
        ProfileInfoHolder.initVkModelUser();
    }
}