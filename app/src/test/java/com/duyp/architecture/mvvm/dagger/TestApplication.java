package com.duyp.architecture.mvvm.dagger;

import com.duyp.architecture.mvvm.App;

/**
 * Created by duypham on 10/21/17.
 *
 */

public class TestApplication extends App {

    @Override
    protected void initAppComponent() {
        sAppComponent = TestAppInjector.init(this);
    }

    public static TestAppComponent getAppComponent() {
        return (TestAppComponent)sAppComponent;
    }

}
