package com.duyp.architecture.mvvm.ui.modules.splash;

import com.duyp.architecture.mvvm.R;
import com.duyp.architecture.mvvm.dagger.TestApplication;
import com.duyp.architecture.mvvm.data.local.user.UserManager;
import com.duyp.architecture.mvvm.ui.modules.BaseActivityTest;
import com.duyp.architecture.mvvm.ui.modules.login.LoginActivity;
import com.duyp.architecture.mvvm.ui.modules.main.MainActivity;

import org.junit.Test;

import javax.inject.Inject;

import static com.duyp.architecture.mvvm.RobolectricHelper.assertNextActivity;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by duypham on 10/21/17.
 *
 */

public class SplashActivityTest extends BaseActivityTest<SplashActivity>{

    @Inject
    UserManager userManager;

    @Override
    public void setup() {
        super.setup();
        TestApplication.getAppComponent().inject(this);
    }

    @Test
    public void isActivityNotNull() {
        assertThat(controller.get(), is(notNullValue()));
        assertThat(userManager, is(notNullValue()));
    }

    @Test
    public void haveCorrectResourceAppName() {
        assertEquals("GitHub", controller.get().getResources().getString(R.string.app_name));
    }

    @Test
    public void hasUserShouldNavigateToMainActivity() {
        when(userManager.checkForSavedUserAndStartSessionIfHas()).thenReturn(true);

        controller.create().resume();
        assertNextActivity(controller.get(), MainActivity.class);
    }

    @Test
    public void noUserShouldNavigateToLoginActivity() {
        controller.create().resume();
        assertNextActivity(controller.get(), LoginActivity.class);
    }
}