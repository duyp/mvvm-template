package com.duyp.architecture.mvvm.dagger;

import com.duyp.architecture.mvvm.local.RealmDatabase;
import com.duyp.architecture.mvvm.local.base_test.TestDao;
import com.duyp.architecture.mvvm.local.base_test.TestDaoImpl;
import com.duyp.architecture.mvvm.local.base_test.TestModel;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by duypham on 10/16/17.
 *
 */

public class TestRealmDatabase extends RealmDatabase {

    public TestRealmDatabase(RealmConfiguration realmConfiguration) {
        super(realmConfiguration);
    }

    public TestDao newTestDao() {
        return new TestDaoImpl(Realm.getInstance(mRealmConfiguration), TestModel.class);
    }
}
