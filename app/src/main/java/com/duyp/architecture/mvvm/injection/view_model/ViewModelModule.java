package com.duyp.architecture.mvvm.injection.view_model;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.duyp.architecture.mvvm.ui.modules.login.LoginViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    abstract ViewModel bindUserViewModel(LoginViewModel viewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(GithubViewModelFactory factory);
}
