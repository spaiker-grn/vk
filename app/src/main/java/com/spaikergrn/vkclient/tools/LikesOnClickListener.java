package com.spaikergrn.vkclient.tools;

import android.util.Log;
import android.view.View;

import com.spaikergrn.vkclient.clients.HttpUrlClient;
import com.spaikergrn.vkclient.serviceclasses.Constants;
import com.spaikergrn.vkclient.vkapi.VkApiMethods;
import com.spaikergrn.vkclient.vkapi.vkapimodelskotlin.ILikeAbleK;

import org.json.JSONException;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LikesOnClickListener implements View.OnClickListener {

    private final int mItemId;
    private final int mOwnerId;
    private final String mType;
    private final ILikeAbleK mLikeAble;
    private final ExecutorService mExecutorService;

    public LikesOnClickListener(final String pType, final int pOwnerId, final int pItemId, final ILikeAbleK pLikeAble) {
        mLikeAble = pLikeAble;
        mType = pType;
        mOwnerId = pOwnerId;
        mItemId = pItemId;
        mExecutorService = Executors.newFixedThreadPool(1);
    }

    @Override
    public void onClick(final View pView) {
        mExecutorService.execute(new LikesRunnable());
    }

    class LikesRunnable implements Runnable {

        @Override
        public void run() {

            try {
                if (!mLikeAble.getUserLike()) {
                    new HttpUrlClient().getRequestWithErrorCheck(VkApiMethods.addLike(mType, mOwnerId, mItemId));
                    mLikeAble.setUserLike(true);

                } else {
                    new HttpUrlClient().getRequestWithErrorCheck(VkApiMethods.deleteLike(mType, mOwnerId, mItemId));
                    mLikeAble.setUserLike(false);
                }
            } catch (final IOException | JSONException pE) {
                Log.e(Constants.ERROR, pE.getMessage(), pE.initCause(pE.getCause()));
            }

        }
    }
}
