package com.duyp.architecture.mvvm.injection;

import com.duyp.architecture.mvvm.ui.MainActivity;
import com.duyp.architecture.mvvm.ui.MainActivityModule;
import com.duyp.architecture.mvvm.ui.TestFragment;
import com.duyp.architecture.mvvm.ui.TestFragmentModule;

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

    @ContributesAndroidInjector(modules = TestFragmentModule.class)
    abstract TestFragment testFragment();
}
