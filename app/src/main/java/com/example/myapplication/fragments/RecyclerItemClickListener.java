package com.example.myapplication.fragments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {

    private final OnItemClickListener mListener;
    private final GestureDetector mGestureDetector;


    public interface OnItemClickListener {

        public void onItemClick(View pView, int pPosition);
        public void onLongItemClick(View pView, int pPosition);
    }

    public RecyclerItemClickListener(final Context pContext, final RecyclerView pRecyclerView, final OnItemClickListener pListener){
        mListener = pListener;
        mGestureDetector = new GestureDetector(pContext, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(final MotionEvent pE) {
                return true;
            }

            @Override
            public void onLongPress(final MotionEvent pE) {
                final View child = pRecyclerView.findChildViewUnder(pE.getX(), pE.getY());
                if (child != null && mListener != null) {
                    mListener.onLongItemClick(child, pRecyclerView.getChildAdapterPosition(child));
                }
            }
        });

    }



    @Override
    public boolean onInterceptTouchEvent(final RecyclerView pView, final MotionEvent pE) {
        final View childView = pView.findChildViewUnder(pE.getX(), pE.getY());
        if (childView != null && mListener != null && mGestureDetector.onTouchEvent(pE)) {
            mListener.onItemClick(childView, pView.getChildAdapterPosition(childView));
            return true;
        }
        return false;
    }


    @Override
    public void onTouchEvent(final RecyclerView pView, final MotionEvent pE) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(final boolean disallowIntercept) {

    }
}
