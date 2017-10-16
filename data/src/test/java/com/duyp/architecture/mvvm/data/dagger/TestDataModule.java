package com.duyp.architecture.mvvm.data.dagger;

import android.content.Context;

import com.duyp.architecture.mvvm.data.local.RealmDatabase;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
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


    public TestDataModule() {
        initRealm();
    }

    private void initRealm() {
        // reference: https://github.com/realm/realm-java/blob/master/examples/unitTestExample/src/test/java/io/realm/examples/unittesting/ExampleActivityTest.java
        mockStatic(RealmCore.class);
        mockStatic(RealmLog.class);
        mockStatic(Realm.class);
        mockStatic(RealmConfiguration.class);
        Realm.init(RuntimeEnvironment.application);

        final Realm mockRealm = PowerMockito.mock(Realm.class);
        final RealmConfiguration mockRealmConfig = PowerMockito.mock(RealmConfiguration.class);

        // TODO: Better solution would be just mock the RealmConfiguration.Builder class. But it seems there is some
        // problems for powermock to mock it (static inner class). We just mock the RealmCore.loadLibrary(Context) which
        // will be called by RealmConfiguration.Builder's constructor.
        doNothing().when(RealmCore.class);
        RealmCore.loadLibrary(any(Context.class));

        // TODO: Mock the RealmConfiguration's constructor. If the RealmConfiguration.Builder.build can be mocked, this
        // is not necessary anymore.
        try {
            whenNew(RealmConfiguration.class).withAnyArguments().thenReturn(mockRealmConfig);

            when(Realm.getDefaultConfiguration()).thenReturn(mockRealmConfig);

            // Anytime getInstance is called with any configuration, then return the mockRealm
            when(Realm.getDefaultInstance()).thenReturn(mockRealm);

        } catch (Exception e) {
            throw new IllegalStateException("Can't create new Realm configuration instance");
        }
    }

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
    RealmDatabase provideRealmDatabase(RealmConfiguration realmConfig) {
        return new TestRealmDatabase(realmConfig);
    }
}
