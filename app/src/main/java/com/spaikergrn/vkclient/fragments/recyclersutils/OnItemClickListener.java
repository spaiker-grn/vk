package com.spaikergrn.vkclient.fragments.recyclersutils;

import android.view.View;

public interface OnItemClickListener {

    void onItemClick(View pView, int pPosition);

    void onLongItemClick(View pView, int pPosition);
}
