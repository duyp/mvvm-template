package com.duyp.architecture.mvvm.local.base_test;

import com.duyp.androidutils.realm.BaseRealmDao;
import com.duyp.androidutils.realm.BaseRealmDaoImpl;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by duypham on 10/17/17.
 *
 */

public class TestDao extends BaseRealmDaoImpl<TestModel> implements BaseRealmDao<TestModel> {

    @Inject
    public TestDao(RealmConfiguration realmConfiguration) {
        super(Realm.getInstance(realmConfiguration), TestModel.class);
    }
}
