package com.duyp.architecture.mvvm;

import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;

/**
 * Created by duypham on 9/21/17.
 *
 */

public abstract class BaseTest {

    protected Instrumentation getInstrumentation() {
        return InstrumentationRegistry.getInstrumentation();
    }

    protected TestApplication getTestApplication() {
        return (TestApplication)getInstrumentation().getTargetContext().getApplicationContext();
    }
}
