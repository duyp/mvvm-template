package com.duyp.architecture.mvvm;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.duyp.architecture.mvvm.data.user.UserManager;
import com.duyp.architecture.mvvm.injection.AppComponent;
import com.duyp.architecture.mvvm.injection.AppInjector;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import lombok.Getter;

/**
 * Created by Duy Pham on 09/2017.
 *
 */
public class MyApplication extends Application implements HasActivityInjector {

    private static MyApplication sInstance;

    protected static AppComponent sAppComponent;

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Getter
    @Inject
    UserManager userManager;

    @Getter
    RefWatcher refWatcher;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        initAppComponent();
        refWatcher = LeakCanary.install(this);
    }

    protected void initAppComponent() {
        sAppComponent = AppInjector.init(this);
    }

    public static AppComponent getAppComponent() {
        return sAppComponent;
    }

    public static MyApplication getInstance() {
        return sInstance;
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }
}
