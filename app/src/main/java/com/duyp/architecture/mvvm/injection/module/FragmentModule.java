package com.duyp.architecture.mvvm.injection.module;

import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.duyp.androidutils.image.glide.loader.SimpleGlideLoader;
import com.duyp.androidutils.image.glide.loader.TransitionGlideLoader;
import com.duyp.androidutils.navigator.ActivityNavigator;
import com.duyp.androidutils.navigator.ChildFragmentNavigator;
import com.duyp.androidutils.navigator.FragmentNavigator;
import com.duyp.androidutils.navigator.Navigator;
import com.duyp.architecture.mvp.base.fragment.BaseFragment;
import com.duyp.architecture.mvp.dagger.qualifier.ActivityContext;
import com.duyp.architecture.mvp.dagger.qualifier.ActivityFragmentManager;
import com.duyp.architecture.mvp.dagger.qualifier.ChildFragmentManager;
import com.duyp.architecture.mvp.dagger.scopes.PerFragment;
import com.duyp.architecture.mvp.utils.AvatarLoader;
import com.duyp.architecture.mvp.utils.NavigatorHelper;

import dagger.Module;
import dagger.Provides;

/**
 * Module for fragment component, modified by Duy Pham (Copyright 2016 Patrick Löwenstein)
 */
@Module
public class FragmentModule {

    private final BaseFragment mFragment;

    public FragmentModule(BaseFragment fragment) {
        mFragment = fragment;
    }

    @PerFragment
    @Provides
    Fragment provideFragment() {
        return mFragment;
    }

    @Provides
    @PerFragment
    @ChildFragmentManager
    FragmentManager provideChildFragmentManager() { return mFragment.getChildFragmentManager(); }

    @Provides
    @PerFragment
    FragmentNavigator provideFragmentNavigator() { return new ChildFragmentNavigator(mFragment); }

    @Provides
    @PerFragment
    NavigatorHelper provideNavigatorHelper(FragmentNavigator navigator) {
        return new NavigatorHelper(navigator);
    }

    @Provides
    FragmentActivity provideActivity() {
        return mFragment.getActivity();
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mFragment.getContext();
    }

    @Provides
    @ActivityFragmentManager
    FragmentManager provideFragmentManager() {
        return mFragment.getActivity().getSupportFragmentManager();
    }

    @Provides
    Navigator provideNavigator() {
        return new ActivityNavigator(mFragment.getActivity());
    }

    @Provides
    SimpleGlideLoader provideDefaultGlide() {
        return new SimpleGlideLoader(mFragment);
    }

    @Provides
    TransitionGlideLoader provideTransitionGlide() {
        return new TransitionGlideLoader(mFragment);
    }

    @Provides
    AvatarLoader provideAvatarLoader() {
        return new AvatarLoader(mFragment);
    }

    @Provides
    LifecycleOwner provideLifeCycleOwner() {
        return mFragment;
    }
}
