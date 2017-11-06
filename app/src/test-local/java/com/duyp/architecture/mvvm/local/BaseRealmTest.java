package com.duyp.architecture.mvvm.local;

import android.content.Context;

import com.duyp.architecture.mvvm.BuildConfig;
import com.duyp.architecture.mvvm.local.dagger.DaggerTestComponent;
import com.duyp.architecture.mvvm.local.dagger.TestComponent;
import com.duyp.architecture.mvvm.local.dagger.TestDataModule;
import com.duyp.architecture.mvvm.local.dagger.TestRealmDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.internal.RealmCore;
import io.realm.log.RealmLog;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

/**
 * Created by duypham on 10/16/17.
 *
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 19)
//@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*"}) // not working when run multiple test classes
// https://github.com/powermock/powermock/issues/593
@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*", "org.powermock.*"})
@SuppressStaticInitializationFor("io.realm.internal.Util")
@PrepareForTest({RealmCore.class, Realm.class, RealmConfiguration.class, RealmLog.class,
        RealmQuery.class, RealmResults.class})
public abstract class BaseRealmTest {

    @Rule
    public PowerMockRule rule = new PowerMockRule();

    public TestComponent testComponent;

    @Inject
    protected Realm mockRealm;

    @Before
    public void setup() throws Exception{
        testComponent = DaggerTestComponent.builder()
                .testDataModule(new TestDataModule())
                .build();
    }

    @After
    public void teardown() {
        testComponent = null;
    }

    public void verifyRealmTransaction() {
        verify(mockRealm, times(1)).beginTransaction();
        verify(mockRealm, times(1)).commitTransaction();
    }
}
