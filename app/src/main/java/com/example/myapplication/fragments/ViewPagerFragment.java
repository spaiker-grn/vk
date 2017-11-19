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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Дмитрий on 04.11.2017.
 */

public class ViewPagerFragment extends Fragment {

    private static final int LAYOUT = R.layout.view_pager_fragment_layout;

    public static ViewPagerFragment getInstance() {
        final Bundle bundle = new Bundle();
        final ViewPagerFragment fragment = new ViewPagerFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View view = inflater.inflate(LAYOUT, container, false);

        final Button button = view.findViewById(R.id.get);
        final TextView textViewUrl = view.findViewById(R.id.url_text_view);
        final TextView textViewResponse = view.findViewById(R.id.json_text_view);


        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View view) {
                final String[] pStrings = {"157176651", "157176659", "157176654", "157176650"};
                final Map<String, String> map = new HashMap<>();
                final String response = Arrays.toString(pStrings).replaceAll("\\[|]", "");
                final String code = (VkApiBuilder.GET_USERS_START + response) + VkApiBuilder.GET_USERS_END;
                final String request;
                final VkApiBuilder vkApiBuilder = new VkApiBuilder();
                map.put("code", code);
                Log.d(Constants.MY_TAG, vkApiBuilder.buildUrl(VkApiBuilder.EXECUTE, map));
                textViewUrl.setText(vkApiBuilder.buildUrl(VkApiBuilder.EXECUTE, map));



            }
        });

        return view;

    }
}
