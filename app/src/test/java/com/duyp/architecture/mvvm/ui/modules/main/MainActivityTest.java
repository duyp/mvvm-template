package com.duyp.architecture.mvvm.ui.modules.main;

import android.support.v4.app.FragmentManager;

import com.duyp.architecture.mvvm.BuildConfig;
import com.duyp.architecture.mvvm.dagger.TestApplication;
import com.duyp.architecture.mvvm.ui.modules.BaseActivityTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

/**
 * Created by duypham on 10/24/17.
 *
 */

//@RunWith(RobolectricTestRunner.class)
//@Config(constants = BuildConfig.class, minSdk = 21, maxSdk = 25, application = TestApplication.class)
public class MainActivityTest extends BaseActivityTest<MainActivity> {

    private MainActivity activity;
    private FragmentManager fragmentManager;

    @Override
    public void setup() {
        super.setup();
        activity = controller.create().start().resume().get();
        fragmentManager = activity.getSupportFragmentManager();
    }

    @Test
    public void onCreateShouldSelectFirstTab() {
        assertEquals(fragmentManager.getFragments().size(), 1);
    }
}