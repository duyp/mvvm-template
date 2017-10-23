package com.duyp.architecture.mvvm.ui.modules;

import android.app.Activity;
import android.support.annotation.CallSuper;

import com.duyp.architecture.mvvm.BuildConfig;
import com.duyp.architecture.mvvm.dagger.TestApplication;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;

import java.lang.reflect.ParameterizedType;

/**
 * Created by duypham on 10/23/17.
 *
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, minSdk = 21, application = TestApplication.class)
public class BaseActivityTest<T extends Activity> {

    protected ActivityController<T> controller;

    @CallSuper
    @Before
    public void setup() {
        controller = Robolectric.buildActivity(getActivityClass());
    }

    protected Class<T> getActivityClass() {
        // noinspection unchecked
        return (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0]; // 1 is BaseViewModel
    }
}
