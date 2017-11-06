package com.duyp.architecture.mvvm.local.dagger;

import com.duyp.architecture.mvvm.data.local.RealmDatabase;
import com.duyp.architecture.mvvm.local.base_test.TestDao;
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

}
