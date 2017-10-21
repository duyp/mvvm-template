package com.duyp.architecture.mvvm.dagger;

import com.duyp.architecture.mvvm.MyApplication;
import com.duyp.architecture.mvvm.injection.AppComponent;
import com.duyp.architecture.mvvm.injection.AppInjector;

/**
 * Created by duypham on 10/21/17.
 *
 */

public class TestApplication extends MyApplication {

    @Override
    protected void initAppComponent() {
        sAppComponent = TestAppInjector.init(this);
    }

    public static TestAppComponent getAppComponent() {
        return (TestAppComponent)sAppComponent;
    }

}
