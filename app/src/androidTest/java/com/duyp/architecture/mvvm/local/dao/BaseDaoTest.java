package com.duyp.architecture.mvvm.local.dao;

import android.util.Log;

import com.duyp.architecture.mvvm.data.local.RealmDatabase;
import com.duyp.architecture.mvvm.BaseTest;
import com.duyp.architecture.mvvm.dagger.TestAppComponent;
import com.google.gson.Gson;

import org.junit.After;
import org.junit.Before;

import javax.inject.Inject;

/**
 * Created by duypham on 9/21/17.
 *
 */
public abstract class BaseDaoTest extends BaseTest {

    public static final String TAG = "test";

    @Inject
    RealmDatabase realmDatabase;

    @Inject
    Gson gson;

    @Before
    public void setUp() throws Exception {
        Log.d(TAG, "setUp: ");
        inject(getTestApplication().getAppComponent());
    }

    @After
    public void tearDown() throws Exception {
        Log.d(TAG, "tearDown: ");
//        realmDatabase.clearAll();
        getTestApplication().clearAppComponent();
    }

    public abstract void inject(TestAppComponent appComponent);
}
