package com.duyp.architecture.mvvm.data.dagger;

import com.duyp.architecture.mvvm.data.local.dao.IssueDaoTest;
import com.duyp.architecture.mvvm.data.repository.IssuesRepoTest;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by duypham on 10/16/17.
 * Component for data module testing
 */

@Component (
        modules = TestDataModule.class
)
@Singleton
public interface TestComponent {
    void inject(IssuesRepoTest test);
    void inject(IssueDaoTest test);
}
