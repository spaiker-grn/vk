package com.spaikergrn.vkclient.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.spaikergrn.vkclient.R;

public class ProfileFragment extends Fragment implements IAdapterRefresh {


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
        Toast.makeText(getContext(),"Profile",Toast.LENGTH_SHORT).show();
    }
}