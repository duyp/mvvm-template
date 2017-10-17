package com.duyp.architecture.mvvm.data.dagger;


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

}
