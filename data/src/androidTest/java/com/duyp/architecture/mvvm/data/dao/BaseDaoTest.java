package com.duyp.architecture.mvvm.data.dao;

import android.support.annotation.NonNull;
import android.util.Log;

import com.duyp.architecture.mvvm.data.BaseTest;
import com.duyp.architecture.mvvm.data.dagger.TestAppComponent;
import com.duyp.architecture.mvvm.data.local.RealmDatabase;
import com.duyp.architecture.mvvm.data.model.Issue;
import com.duyp.architecture.mvvm.data.model.Repository;
import com.duyp.architecture.mvvm.data.model.User;
import com.google.gson.Gson;

import org.junit.After;
import org.junit.Before;

import java.util.Date;

import javax.inject.Inject;

import io.realm.RealmList;

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
        realmDatabase.clearAll();
        getTestApplication().clearAppComponent();
    }

    public abstract void inject(TestAppComponent appComponent);
}
