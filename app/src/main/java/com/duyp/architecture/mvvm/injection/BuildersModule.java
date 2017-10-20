package com.duyp.architecture.mvvm.injection;

import com.duyp.architecture.mvvm.MainActivity;
import com.duyp.architecture.mvvm.injection.activity_modules.MainActivityModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by duypham on 10/19/17.
 *
 */

@Module
abstract class BuildersModule {

    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity contributeMainActivity();
}
