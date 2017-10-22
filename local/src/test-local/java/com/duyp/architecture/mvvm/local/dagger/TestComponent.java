package com.duyp.architecture.mvvm.dagger;

import com.duyp.architecture.mvvm.local.IssueDaoTest;
import com.duyp.architecture.mvvm.local.base_test.BaseRealmDaoTest;

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
    void inject(IssueDaoTest test);
    void inject(BaseRealmDaoTest test);
}
