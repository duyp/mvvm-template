package com.duyp.architecture.mvvm.data;

import android.arch.lifecycle.MutableLiveData;

import com.duyp.architecture.mvvm.data.dagger.DaggerTestComponent;
import com.duyp.architecture.mvvm.data.dagger.DataModule;
import com.duyp.architecture.mvvm.data.dagger.TestComponent;
import com.duyp.architecture.mvvm.data.remote.GithubService;
import com.duyp.architecture.mvvm.local.RealmDatabase;
import com.duyp.architecture.mvvm.model.User;
import com.duyp.architecture.mvvm.test_utils.RxSchedulersOverrideRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.inject.Inject;

import io.realm.RealmQuery;
import io.realm.RealmResults;

import static com.duyp.architecture.mvvm.model.ModelUtils.sampleUser;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Created by duypham on 10/17/17.
 *
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({RealmResults.class, RealmQuery.class, MutableLiveData.class})
public abstract class BaseDataModuleTest {

    @Rule
    public RxSchedulersOverrideRule rxSchedulersOverrideRule = new RxSchedulersOverrideRule();

    TestComponent testComponent;

    protected RealmDatabase mockRealmDatabase;

    protected UserDataStore mockUserDataStore;

    protected User mUser;

    protected MutableLiveData<User> mMockUserLiveData;

    @Inject
    protected GithubService githubService;

    @Before
    public void setup() throws Exception {
        // init realm database
        mockRealmDatabase = PowerMockito.mock(RealmDatabase.class);

        // ============ init user data store ==============
        mUser = sampleUser(1L);
        // noinspection unchecked
        mMockUserLiveData = PowerMockito.mock(MutableLiveData.class);
        mockUserDataStore = PowerMockito.mock(UserDataStore.class);

        when(mockUserDataStore.getUserLiveData()).thenReturn(mMockUserLiveData);

        // ============ init test component ===================
        testComponent = DaggerTestComponent.builder()
                .dataModule(new DataModule(mockRealmDatabase, mockUserDataStore))
                .build();
        inject(testComponent);
    }

    protected void initUserSession() {
        when(mMockUserLiveData.getValue()).thenReturn(mUser);
        when(mockUserDataStore.getUser()).thenReturn(mUser);
    }

    /**
     * Inject target test class
     * Should be done in setup function {@link #setup()}
     * @param component component to inject
     * @throws Exception
     */
    protected abstract void inject(TestComponent component) throws Exception;
}
