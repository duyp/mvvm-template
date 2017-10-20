package com.duyp.architecture.mvvm.injection.view_model;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.duyp.architecture.mvvm.ui.TestViewModel;
import com.duyp.architecture.mvvm.base.BaseViewModel;

import java.util.Map;

import javax.inject.Provider;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import dagger.multibindings.Multibinds;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(TestViewModel.class)
    abstract ViewModel bindUserViewModel(TestViewModel userViewModel);
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(SearchViewModel.class)
//    abstract ViewModel bindSearchViewModel(SearchViewModel searchViewModel);
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(RepoViewModel.class)
//    abstract ViewModel bindRepoViewModel(RepoViewModel repoViewModel);
//
    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(GithubViewModelFactory factory);
}
