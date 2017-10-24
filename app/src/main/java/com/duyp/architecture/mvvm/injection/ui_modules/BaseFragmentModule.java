package com.duyp.architecture.mvvm.injection.ui_modules;

import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.duyp.androidutils.glide.loader.SimpleGlideLoader;
import com.duyp.androidutils.navigation.ChildFragmentNavigator;
import com.duyp.androidutils.navigation.FragmentNavigator;
import com.duyp.architecture.mvvm.ui.navigator.NavigatorHelper;
import com.duyp.architecture.mvvm.utils.AvatarLoader;
import com.duyp.architecture.mvvm.ui.navigator.FragmentNavigatorHelper;
import com.duyp.architecture.mvvm.injection.qualifier.ActivityContext;
import com.duyp.architecture.mvvm.injection.qualifier.ActivityFragmentManager;
import com.duyp.architecture.mvvm.injection.qualifier.ChildFragmentManager;

import dagger.Module;
import dagger.Provides;

/**
 * Module for fragment component, modified by Duy Pham (reference: Patrick Löwenstein)
 *
 * NOTE: all method must be public (since children module might not in same package,
 * thus dagger can't generate inherit method
 *
 */
@Module
public abstract class BaseFragmentModule<T extends Fragment> {

    @Provides
    @ActivityContext
    public Context provideContext(T fragment) {
        return fragment.getContext();
    }

    @Provides
    @ChildFragmentManager
    public FragmentManager provideChildFragmentManager(T fragment) { return fragment.getChildFragmentManager(); }

    @Provides
    @ActivityFragmentManager
    public FragmentManager provideActivityFragmentManager(FragmentActivity activity) {
        return activity.getSupportFragmentManager();
    }

    @Provides
    public FragmentNavigator provideFragmentNavigator(T fragment) { return new ChildFragmentNavigator(fragment); }

    @Provides
    public FragmentActivity provideActivity(T fragment) {
        return fragment.getActivity();
    }

    @Provides
    public LifecycleOwner provideLifeCycleOwner(T fragment) {
        return fragment;
    }

    @Provides
    public FragmentNavigatorHelper fragmentNavigatorHelper(FragmentNavigator navigator) {
        return new FragmentNavigatorHelper(navigator);
    }

    @Provides
    public NavigatorHelper navigatorHelper(FragmentNavigator navigator) {
        return new NavigatorHelper(navigator);
    }

    @Provides
    public SimpleGlideLoader provideDefaultGlide(T fragment) {
        return new SimpleGlideLoader(fragment);
    }

    @Provides
    public AvatarLoader provideAvatarLoader(T fragment) {
        return new AvatarLoader(fragment);
    }
}
