package com.duyp.architecture.mvvm.data.dagger;

import com.duyp.architecture.mvvm.data.local.RealmDatabase;

import org.powermock.api.mockito.PowerMockito;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.log.RealmLog;

/**
 * Created by duypham on 10/16/17.
 *
 */

public class TestRealmDatabase extends RealmDatabase {

    public TestRealmDatabase(RealmConfiguration realmConfiguration) {
        super(realmConfiguration);
        initMockRealmInstance();
    }

    private void initMockRealmInstance() {
        PowerMockito.mockStatic(Realm.class);
        PowerMockito.mockStatic(RealmLog.class);
        Realm mockRealm = PowerMockito.mock(Realm.class);
        PowerMockito.when(Realm.getInstance(mRealmConfiguration)).thenReturn(mockRealm);
    }
}
