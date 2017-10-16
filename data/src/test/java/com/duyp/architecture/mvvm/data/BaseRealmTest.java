package com.duyp.architecture.mvvm.data;

import com.duyp.architecture.mvvm.data.BuildConfig;
import com.duyp.architecture.mvvm.data.dagger.DaggerTestComponent;
import com.duyp.architecture.mvvm.data.dagger.TestComponent;
import com.duyp.architecture.mvvm.data.dagger.TestDataModule;
import com.duyp.architecture.mvvm.data.local.RealmDatabase;

import org.junit.Before;
import org.junit.Rule;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.powermock.reflect.Whitebox;
import org.robolectric.annotation.Config;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.internal.Collection;
import io.realm.internal.RealmCore;
import io.realm.log.RealmLog;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mock;

/**
 * Created by duypham on 10/16/17.
 *
 */

@Config(constants = BuildConfig.class, sdk = 19)
@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*"})
@SuppressStaticInitializationFor("io.realm.internal.Util")
@PrepareForTest({RealmCore.class, Realm.class, RealmConfiguration.class, RealmLog.class})
public abstract class BaseRealmTest {

    @Rule
    public PowerMockRule rule = new PowerMockRule();

    public TestComponent testComponent;

    @Inject
    public RealmDatabase realmDatabase;

    @Before
    public void setup() {
        testComponent = DaggerTestComponent.builder()
                .testDataModule(new TestDataModule())
                .build();
    }

    public <T extends RealmObject> RealmQuery<T> initRealmQuery(Realm realm, Class<T> tClass) {
        RealmQuery<T> query = mockRealmQuery();
        when(realm.where(tClass)).thenReturn(query);
        return query;
    }

    public <T extends RealmObject> RealmResults<T> initFindAllSorted(RealmQuery<T> query) {
        RealmResults<T> realmResults = mockRealmResults();
        when(query.findAll()).thenReturn(realmResults);
        when(query.findAllSorted(anyString(), any())).thenReturn(realmResults);
        return realmResults;
    }

    @SuppressWarnings("unchecked")
    protected  <T extends RealmObject> RealmQuery<T> mockRealmQuery() {
        return Mockito.mock(RealmQuery.class);
    }

    @SuppressWarnings("unchecked")
    protected <T extends RealmObject> RealmResults<T> mockRealmResults() {
        return Mockito.mock(RealmResults.class);
    }

    @SuppressWarnings("unchecked")
    protected Collection mockRealmCollection() {
        return Mockito.mock(Collection.class);
    }
}
