//package com.duyp.architecture.mvvm.injection;
//
//import android.arch.lifecycle.LifecycleOwner;
//import android.content.Context;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentActivity;
//import android.support.v4.app.FragmentManager;
//
//import com.duyp.androidutils.glide.loader.SimpleGlideLoader;
//import com.duyp.androidutils.navigation.ActivityNavigator;
//import com.duyp.androidutils.navigation.ChildFragmentNavigator;
//import com.duyp.androidutils.navigation.FragmentNavigator;
//import com.duyp.androidutils.navigation.Navigator;
//import com.duyp.architecture.mvvm.utils.AvatarLoader;
//import com.duyp.architecture.mvvm.utils.NavigatorHelper;
//import com.duyp.architecture.mvvm.utils.qualifier.ActivityContext;
//import com.duyp.architecture.mvvm.utils.qualifier.ActivityFragmentManager;
//import com.duyp.architecture.mvvm.utils.qualifier.ChildFragmentManager;
//
//import dagger.Module;
//import dagger.Provides;
//
///**
// * Module for fragment component, modified by Duy Pham (Copyright 2016 Patrick Löwenstein)
// */
//@Module
//public class FragmentModule {
//
//    private final Fragment mFragment;
//
//    public FragmentModule(Fragment fragment) {
//        mFragment = fragment;
//    }
//
//    @Provides
//    Fragment provideFragment() {
//        return mFragment;
//    }
//
//    @Provides
//    @ChildFragmentManager
//    FragmentManager provideChildFragmentManager() { return mFragment.getChildFragmentManager(); }
//
//    @Provides
//    FragmentNavigator provideFragmentNavigator() { return new ChildFragmentNavigator(mFragment); }
//
//    @Provides
//    NavigatorHelper provideNavigatorHelper(FragmentNavigator navigator) {
//        return new NavigatorHelper(navigator);
//    }
//
//    @Provides
//    FragmentActivity provideActivity() {
//        return mFragment.getActivity();
//    }
//
//    @Provides
//    @ActivityContext
//    Context provideContext() {
//        return mFragment.getContext();
//    }
//
//    @Provides
//    @ActivityFragmentManager
//    FragmentManager provideFragmentManager() {
//        return mFragment.getActivity().getSupportFragmentManager();
//    }
//
//    @Provides
//    Navigator provideNavigator() {
//        return new ActivityNavigator(mFragment.getActivity());
//    }
//
//    @Provides
//    SimpleGlideLoader provideDefaultGlide() {
//        return new SimpleGlideLoader(mFragment);
//    }
//
//    @Provides
//    AvatarLoader provideAvatarLoader() {
//        return new AvatarLoader(mFragment);
//    }
//
//    @Provides
//    LifecycleOwner provideLifeCycleOwner() {
//        return mFragment;
//    }
//}
