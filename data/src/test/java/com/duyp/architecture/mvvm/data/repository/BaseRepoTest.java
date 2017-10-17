package com.duyp.architecture.mvvm.data.repository;

import com.duyp.architecture.mvvm.data.dagger.DaggerTestComponent;
import com.duyp.architecture.mvvm.data.dagger.DataModule;
import com.duyp.architecture.mvvm.data.dagger.TestComponent;
import com.duyp.architecture.mvvm.data.remote.GithubService;
import com.duyp.architecture.mvvm.local.RealmDatabase;
import com.duyp.architecture.mvvm.local.dao.IssueDao;
import com.duyp.architecture.mvvm.local.dao.RepositoryDao;
import com.duyp.architecture.mvvm.local.dao.UserDao;
import com.duyp.architecture.mvvm.test_utils.RxSchedulersOverrideRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.when;

import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.robolectric.RobolectricTestRunner;

import javax.inject.Inject;

import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by duypham on 10/17/17.
 *
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({RealmResults.class, RealmQuery.class})
public abstract class BaseRepoTest {

    @Rule
    public RxSchedulersOverrideRule rxSchedulersOverrideRule = new RxSchedulersOverrideRule();

    TestComponent testComponent;

    protected RealmDatabase mockRealmDatabase;

    @Inject
    protected GithubService githubService;

    @Before
    public void setup() throws Exception {
        mockRealmDatabase = PowerMockito.mock(RealmDatabase.class);
        testComponent = DaggerTestComponent.builder()
                .dataModule(new DataModule(mockRealmDatabase))
                .build();
        inject(testComponent);
    }

    protected abstract void inject(TestComponent component) throws Exception;
}
