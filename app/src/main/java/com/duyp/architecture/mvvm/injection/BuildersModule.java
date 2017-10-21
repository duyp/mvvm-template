package com.duyp.architecture.mvvm.injection;

import com.duyp.architecture.mvvm.ui.modules.login.LoginActivity;
import com.duyp.architecture.mvvm.ui.modules.login.LoginActivityModule;
import com.duyp.architecture.mvvm.ui.modules.splash.SplashActivity;
import com.duyp.architecture.mvvm.ui.modules.splash.SplashActivityModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by duypham on 10/19/17.
 *
 */

@Module
abstract class BuildersModule {

    @ContributesAndroidInjector(modules = LoginActivityModule.class)
    abstract LoginActivity loginActivity();

    @ContributesAndroidInjector(modules = SplashActivityModule.class)
    abstract SplashActivity splashActivity();
}
