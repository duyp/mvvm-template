package com.duyp.architecture.mvvm.dagger;

import com.duyp.architecture.mvvm.App;

/**
 * Created by duypham on 10/21/17.
 *
 */

public class TestApplication extends App {

    private static TestApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    @Override
    protected void initAppComponent() {
        sAppComponent = TestAppInjector.init(this);
    }

    public static TestAppComponent getAppComponent() {
        return (TestAppComponent)sAppComponent;
    }

    public static TestApplication getInstance() {
        return sInstance;
    }
}
