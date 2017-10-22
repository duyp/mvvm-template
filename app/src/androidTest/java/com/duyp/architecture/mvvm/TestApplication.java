package com.duyp.architecture.mvvm;

import android.app.Application;

import com.duyp.architecture.mvvm.dagger.DaggerTestAppComponent;
import com.duyp.architecture.mvvm.dagger.TestAppComponent;
import com.duyp.architecture.mvvm.dagger.TestAppModule;
import com.duyp.architecture.mvvm.dagger.TestDataModule;

/**
 * Created by duypham on 9/21/17.
 * Test application
 */

public class TestApplication extends Application {

    TestAppComponent appComponent;

    protected void setupAppComponent() {
        appComponent = DaggerTestAppComponent.builder()
                .testAppModule(new TestAppModule(this))
                .testDataModule(new TestDataModule(this))
                .build();
    }

    public TestAppComponent getAppComponent() {
        if (appComponent == null) {
            setupAppComponent();
        }
        return (TestAppComponent)appComponent;
    }

    public void clearAppComponent() {
        appComponent = null;
    }
}
