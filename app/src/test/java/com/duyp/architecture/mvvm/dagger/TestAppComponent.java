package com.duyp.architecture.mvvm.dagger;

import com.duyp.architecture.mvvm.injection.AppComponent;
import com.duyp.architecture.mvvm.ui.modules.login.LoginViewModelTest;
import com.duyp.architecture.mvvm.ui.modules.splash.SplashActivityTest;

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
                TestBuilderModule.class,
                TestDataModule.class,
                TestNetworkModule.class
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
