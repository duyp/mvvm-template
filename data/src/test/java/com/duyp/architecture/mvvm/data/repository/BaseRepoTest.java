package com.duyp.architecture.mvvm.data.repository;

import com.duyp.architecture.mvvm.data.dagger.DaggerTestComponent;
import com.duyp.architecture.mvvm.data.dagger.DataModule;
import com.duyp.architecture.mvvm.data.dagger.TestComponent;
import com.duyp.architecture.mvvm.local.RealmDatabase;
import com.duyp.architecture.mvvm.local.dao.IssueDao;
import com.duyp.architecture.mvvm.local.dao.RepositoryDao;
import com.duyp.architecture.mvvm.local.dao.UserDao;

import org.junit.Before;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.when;

import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import javax.inject.Inject;

/**
 * Created by duypham on 10/17/17.
 *
 */

@RunWith(MockitoJUnitRunner.class)
public abstract class BaseRepoTest {

    TestComponent testComponent;

    @Inject
    protected RealmDatabase mockRealmDatabase;

    @Before
    public void setup() throws Exception{
        testComponent = DaggerTestComponent.builder()
                .dataModule(new DataModule())
                .build();
        inject(testComponent);
    }

    protected abstract void inject(TestComponent component) throws Exception;
}
