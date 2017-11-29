package com.example.myapplication.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.serviceclasses.Constants;
import com.example.myapplication.vkapi.VkApiBuilder;
import com.example.myapplication.vkapi.vkapimodels.VkModelUser;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class ViewPagerFragment extends Fragment {

    public static ViewPagerFragment newInstance() {
        final Bundle bundle = new Bundle();
        final ViewPagerFragment fragment = new ViewPagerFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.view_pager_fragment_layout, container, false);

        final Button button = view.findViewById(R.id.get);
        final TextView textViewUrl = view.findViewById(R.id.url_text_view);
        final TextView textViewResponse = view.findViewById(R.id.json_text_view);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View view) {

            }
        });

        return view;

    }
}
