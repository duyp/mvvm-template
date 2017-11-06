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

    /**
     * Init realm configuration and instance
     * @return
     * @throws Exception
     */
    @Provides
    @Singleton
    RealmConfiguration provideRealmConfiguration(){
        mockStatic(RealmCore.class);
        mockStatic(RealmLog.class);
        mockStatic(Realm.class);
        mockStatic(RealmConfiguration.class);
        Realm.init(RuntimeEnvironment.application);

        // TODO: Better solution would be just mock the RealmConfiguration.Builder class. But it seems there is some
        // problems for powermock to mock it (static inner class). We just mock the RealmCore.loadLibrary(Context) which
        // will be called by RealmConfiguration.Builder's constructor.
        doNothing().when(RealmCore.class);
        RealmCore.loadLibrary(any(Context.class));

        final RealmConfiguration mockRealmConfig = PowerMockito.mock(RealmConfiguration.class);

        try {
            whenNew(RealmConfiguration.class).withAnyArguments().thenReturn(mockRealmConfig);
        } catch (Exception e) {
            e.printStackTrace();
        }

        when(Realm.getDefaultConfiguration()).thenReturn(mockRealmConfig);

        // init mock realm
        Realm mockRealm = PowerMockito.mock(Realm.class);;
        // Anytime getInstance is called with any configuration, then return the mockRealm
        when(Realm.getDefaultInstance()).thenReturn(mockRealm);

        when(Realm.getInstance(mockRealmConfig)).thenReturn(mockRealm);

        return mockRealmConfig;
    }

    @Provides
    @Singleton
    Realm provideRealm(RealmConfiguration realmConfiguration) {
        return Realm.getInstance(realmConfiguration);
    }

    @Provides
    @Singleton
    TestRealmDatabase provideRealmDatabase(RealmConfiguration realmConfig) {
        return new TestRealmDatabase(realmConfig);
    }
}
