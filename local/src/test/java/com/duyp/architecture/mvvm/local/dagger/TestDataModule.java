package com.duyp.architecture.mvvm.local.dagger;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;

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
