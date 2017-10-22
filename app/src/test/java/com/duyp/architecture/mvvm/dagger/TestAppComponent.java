package com.duyp.architecture.mvvm.dagger;

import com.duyp.architecture.mvvm.injection.AppComponent;
import com.duyp.architecture.mvvm.injection.AppModule_ProvideMySharedPreferencesFactory;
import com.duyp.architecture.mvvm.injection.ui_modules.BuildersModule;
import com.duyp.architecture.mvvm.ui.modules.login.LoginViewModelTest;
import com.duyp.architecture.mvvm.ui.modules.splash.SplashActivityTest;
import com.duyp.architecture.mvvm.injection.AppModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by duypham on 10/21/17.
 *
 */

@Singleton
@Component(
        modules = {
                AndroidInjectionModule.class,
                AndroidSupportInjectionModule.class,
                BuildersModule.class,
                TestDataModule.class,
                TestNetworkModule.class,
                AppModule.class
        }
)
public interface TestAppComponent extends AppComponent{

    @Component.Builder
    interface Builder {
        TestAppComponent build();
    }

    void inject(LoginViewModelTest test);

    void inject(SplashActivityTest test);
}
