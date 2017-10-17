package com.duyp.architecture.mvvm.data.dagger;

import com.duyp.architecture.mvvm.data.repository.IssuesRepoTest;

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
}
