package com.duyp.architecture.mvvm.local.dagger;

import android.content.Context;

import org.powermock.api.mockito.PowerMockito;
import org.robolectric.RuntimeEnvironment;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.internal.RealmCore;
import io.realm.log.RealmLog;

import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

/**
 * Created by duypham on 10/16/17.
 *
 */

@Module
public class TestDataModule {


    public TestDataModule() {}

//    @Provides
//    @Singleton
//    UserDataStore provideUserRepo(CustomSharedPreferences sharedPreferences, Gson gson, RealmDatabase database) {
//        return new UserDataStore(sharedPreferences, gson, database);
//    }

    @Provides
    @Singleton
    RealmConfiguration provideRealmConfiguration() {
        return Realm.getDefaultConfiguration();
    }

    @Provides
    @Singleton
    TestRealmDatabase provideRealmDatabase(RealmConfiguration realmConfig) {
        return new TestRealmDatabase(realmConfig);
    }
}
