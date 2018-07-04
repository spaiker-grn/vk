package com.spaikergrn.vkclient.activity;

import android.app.Activity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;

import java.lang.reflect.ParameterizedType;

import static junit.framework.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
public class BaseActivityTest<T extends Activity> {

    @Test
    public void activityTest(){
        final ActivityController<T> activityController = Robolectric.buildActivity(getActivityClass());
        final T activity = activityController
                .create()
                .start()
                .resume()
                .visible()
                .get();

        assertNotNull(activity);
        activityController.pause().destroy();
    }

    @SuppressWarnings("unchecked")
    private Class<T> getActivityClass(){
        return (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
    }
}