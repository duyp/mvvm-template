package com.duyp.architecture.mvvm.injection.fragment_modules;

import android.arch.lifecycle.LifecycleOwner;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.duyp.androidutils.navigation.ChildFragmentNavigator;
import com.duyp.androidutils.navigation.FragmentNavigator;
import com.duyp.architecture.mvvm.utils.qualifier.ChildFragmentManager;

import dagger.Module;
import dagger.Provides;

/**
 * Module for fragment component, modified by Duy Pham (Copyright 2016 Patrick Löwenstein)
 */
@Module
public abstract class BaseFragmentModule<T extends Fragment> {

    public BaseFragmentModule() {}

    @Provides
    @ChildFragmentManager
    FragmentManager provideChildFragmentManager(T fragment) { return fragment.getChildFragmentManager(); }

    @Provides
    FragmentNavigator provideFragmentNavigator(T fragment) { return new ChildFragmentNavigator(fragment); }

    @Provides
    FragmentActivity provideActivity(T fragment) {
        return fragment.getActivity();
    }

    @Provides
    LifecycleOwner provideLifeCycleOwner(T fragment) {
        return fragment;
    }
}
