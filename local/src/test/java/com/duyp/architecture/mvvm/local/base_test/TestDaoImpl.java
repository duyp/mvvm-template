package com.duyp.architecture.mvvm.local.base_test;

import com.duyp.androidutils.realm.BaseRealmDaoImpl;

import io.realm.Realm;

/**
 * Created by duypham on 10/17/17.
 *
 */

public class TestDaoImpl extends BaseRealmDaoImpl<TestModel> implements TestDao {

    public TestDaoImpl(Realm realm, Class<TestModel> testModelClass) {
        super(realm, testModelClass);
    }
}
