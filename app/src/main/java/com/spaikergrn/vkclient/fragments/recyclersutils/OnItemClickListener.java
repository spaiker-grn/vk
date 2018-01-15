package com.spaikergrn.vk_client.fragments.recyclersutils;

import android.view.View;

public interface OnItemClickListener {

    void onItemClick(View pView, int pPosition);

    void onLongItemClick(View pView, int pPosition);
}
