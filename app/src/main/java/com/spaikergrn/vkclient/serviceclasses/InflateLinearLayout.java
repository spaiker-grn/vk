package com.spaikergrn.vkclient.serviceclasses;

import android.content.Context;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public abstract class InflateLinearLayout extends LinearLayout {

    public InflateLinearLayout(final Context pContext) {
        super(pContext);
        initLayout(pContext, null);
    }

    public InflateLinearLayout(final Context pContext, @Nullable final AttributeSet pAttributeSet) {
        super(pContext, pAttributeSet);
        initLayout(pContext, pAttributeSet);
    }

    public InflateLinearLayout(final Context pContext, @Nullable final AttributeSet pAttributeSet, final int pDefStyleAttr) {
        super(pContext, pAttributeSet, pDefStyleAttr);
        initLayout(pContext,pAttributeSet);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public InflateLinearLayout(final Context pContext, final AttributeSet pAttributeSet, final int pDefStyleAttr, final int pDefStyleRes) {
        super(pContext, pAttributeSet, pDefStyleAttr, pDefStyleRes);
        initLayout(pContext, pAttributeSet);
    }

    protected void initLayout(final Context pContext, final AttributeSet pAttributeSet){
        View.inflate(pContext, getLayout(), this);
        onCreateView(pContext, pAttributeSet);
    }

    protected abstract void onCreateView(final Context pContext, final AttributeSet pAttributeSet);

    @LayoutRes
    protected abstract int getLayout();
}
