package com.duyp.architecture.mvvm.injection;

import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.duyp.androidutils.glide.loader.SimpleGlideLoader;
import com.duyp.androidutils.navigation.ActivityNavigator;
import com.duyp.androidutils.navigation.Navigator;
import com.duyp.architecture.mvvm.utils.AvatarLoader;
import com.duyp.architecture.mvvm.utils.NavigatorHelper;
import com.duyp.architecture.mvvm.utils.qualifier.ActivityContext;
import com.duyp.architecture.mvvm.utils.qualifier.ActivityFragmentManager;

import dagger.Module;
import dagger.Provides;

/**
 * Modified by Duy Pham
 */
@Module
public abstract class BaseActivityModule<T extends AppCompatActivity> {

    @Provides
    @ActivityContext
    public Context provideContext(T activity) { return activity; }

    @Provides
    @ActivityFragmentManager
    public FragmentManager provideFragmentManager(T activity) { return activity.getSupportFragmentManager(); }

    @Provides
    public Navigator provideNavigator(T activity) { return new ActivityNavigator(activity); }

    @Provides
    public NavigatorHelper provideNavigatorHelper(Navigator navigator) {
        return new NavigatorHelper(navigator);
    }

    @Provides
    public SimpleGlideLoader provideDefaultGlide(T activity) {
        return new SimpleGlideLoader(activity);
    }

    @Provides
    public AvatarLoader provideAvatarLoader(T activity) {
        return new AvatarLoader(activity);
    }

    @Provides
    public LifecycleOwner provideLifeCycleOwner(T activity) {
        return activity;
    }
}
