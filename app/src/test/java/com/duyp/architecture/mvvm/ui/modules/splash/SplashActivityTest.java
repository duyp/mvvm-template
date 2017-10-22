package com.duyp.architecture.mvvm.ui.modules.splash;

import com.duyp.architecture.mvvm.BuildConfig;
import com.duyp.architecture.mvvm.dagger.TestApplication;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static com.duyp.architecture.mvvm.utils.test.ModelTestUtils.sampleUser;
import static org.mockito.Mockito.verify;

/**
 * Created by duypham on 10/21/17.
 *
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, minSdk = 21, application = TestApplication.class)
public class SplashActivityTest {

    @Test
    public void hasUserTest() {
//        User user = sampleUser(1L);
//        userManager.startUserSession(user, "abc");

        SplashActivity activity = Robolectric.setupActivity(SplashActivity.class);
//        verify(activity).

    }
}