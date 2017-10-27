package com.duyp.architecture.mvvm.injection.ui_modules;

import com.duyp.architecture.mvvm.ui.modules.feed.FeedFragment;
import com.duyp.architecture.mvvm.ui.modules.feed.FeedModule;
import com.duyp.architecture.mvvm.ui.modules.login.LoginActivity;
import com.duyp.architecture.mvvm.ui.modules.login.LoginActivityModule;
import com.duyp.architecture.mvvm.ui.modules.main.MainActivity;
import com.duyp.architecture.mvvm.ui.modules.main.MainActivityModule;
import com.duyp.architecture.mvvm.ui.modules.main.ProfileActivityModule;
import com.duyp.architecture.mvvm.ui.modules.profile.ProfileActivity;
import com.duyp.architecture.mvvm.ui.modules.profile.overview.OverviewFragment;
import com.duyp.architecture.mvvm.ui.modules.profile.overview.OverviewFragmentModule;
import com.duyp.architecture.mvvm.ui.modules.repo.list.RepoListFragment;
import com.duyp.architecture.mvvm.ui.modules.repo.list.RepoListFragmentModule;
import com.duyp.architecture.mvvm.ui.modules.splash.SplashActivity;
import com.duyp.architecture.mvvm.ui.modules.splash.SplashActivityModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by duypham on 10/19/17.
 *
 */

@Module
public abstract class BuildersModule {

    @ContributesAndroidInjector(modules = LoginActivityModule.class)
    abstract LoginActivity loginActivity();

    @ContributesAndroidInjector(modules = SplashActivityModule.class)
    abstract SplashActivity splashActivity();

    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity mainActivity();

    @ContributesAndroidInjector(modules = ProfileActivityModule.class)
    abstract ProfileActivity profileActivity();

    @ContributesAndroidInjector(modules = FeedModule.class)
    abstract FeedFragment feedFragment();

    @ContributesAndroidInjector(modules = OverviewFragmentModule.class)
    abstract OverviewFragment overviewFragment();


    @ContributesAndroidInjector(modules = RepoListFragmentModule.class)
    abstract RepoListFragment repoListFragment();
}
