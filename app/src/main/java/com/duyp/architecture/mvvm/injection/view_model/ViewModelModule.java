package com.duyp.architecture.mvvm.injection.view_model;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.duyp.architecture.mvvm.ui.modules.feed.FeedViewModel;
import com.duyp.architecture.mvvm.ui.modules.login.LoginViewModel;
import com.duyp.architecture.mvvm.ui.modules.main.MainViewModel;
import com.duyp.architecture.mvvm.ui.modules.profile.ProfileViewModel;
import com.duyp.architecture.mvvm.ui.modules.profile.overview.OverviewViewModel;
import com.duyp.architecture.mvvm.ui.modules.repo.list.UserReposViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    abstract ViewModel bindLoginViewModel(LoginViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    abstract ViewModel mainViewModel(MainViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(FeedViewModel.class)
    abstract ViewModel feedViewModel(FeedViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(UserReposViewModel.class)
    abstract ViewModel repoListViewModel(UserReposViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel.class)
    abstract ViewModel profileViewModel(ProfileViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(OverviewViewModel.class)
    abstract ViewModel provideOverviewViewModel(OverviewViewModel viewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(GithubViewModelFactory factory);
}
