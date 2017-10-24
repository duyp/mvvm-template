package com.duyp.architecture.mvvm.injection.view_model;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.duyp.architecture.mvvm.ui.modules.feed.FeedViewModel;
import com.duyp.architecture.mvvm.ui.modules.login.LoginViewModel;
import com.duyp.architecture.mvvm.ui.modules.main.MainViewModel;

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
    abstract ViewModelProvider.Factory bindViewModelFactory(GithubViewModelFactory factory);
}
