package com.duyp.architecture.mvvm.data.dagger;

import com.duyp.architecture.mvvm.data.UserManagerTest;
import com.duyp.architecture.mvvm.data.repository.IssuesRepoTest;
import com.duyp.architecture.mvvm.data.repository.RepositoriesRepoTest;
import com.duyp.architecture.mvvm.data.repository.RepositoryDetailRepoTest;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Module;

/**
 * Created by duypham on 10/17/17.
 *
 */

@Component (
        modules = DataModule.class
)
@Singleton
public interface TestComponent {
    void inject(IssuesRepoTest test);
    void inject(RepositoriesRepoTest test);
    void inject(RepositoryDetailRepoTest test);
    void inject(UserManagerTest test);
}
