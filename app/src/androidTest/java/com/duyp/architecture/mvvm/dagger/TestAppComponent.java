package com.duyp.architecture.mvvm.dagger;

import com.duyp.architecture.mvvm.local.dao.IssueDaoTest;
import com.duyp.architecture.mvvm.local.dao.RepoDaoTest;
import com.duyp.architecture.mvvm.local.dao.UserDaoTest;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by duypham on 9/21/17.
 *
 */

@Component(
        modules = {TestAppModule.class, TestDataModule.class}
)
@Singleton
public interface TestAppComponent{

    void inject(UserDaoTest test);
    void inject(IssueDaoTest test);
    void inject(RepoDaoTest test);
}
