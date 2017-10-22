package com.duyp.architecture.mvvm;

import android.support.annotation.CallSuper;

import com.duyp.architecture.mvvm.dagger.DaggerTestAppComponent;
import com.duyp.architecture.mvvm.dagger.TestAppComponent;
import com.duyp.architecture.mvvm.data.user.UserManager;

import org.junit.Before;

import javax.inject.Inject;
/**
 * Created by duypham on 10/21/17.
 * Base test class with dagger injection (test modules) and mock injected userManager
 */

public abstract class BaseTest {

    @Inject
    protected UserManager userManager;

    @Before
    @CallSuper
    public void setup() {
        inject(DaggerTestAppComponent.builder().build());
    }

    protected abstract void inject(TestAppComponent testAppComponent);
}
