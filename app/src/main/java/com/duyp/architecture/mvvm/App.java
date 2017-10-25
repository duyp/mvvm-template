package com.duyp.architecture.mvvm;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.v7.preference.PreferenceManager;

import com.duyp.architecture.mvvm.data.local.user.UserManager;
import com.duyp.architecture.mvvm.data.provider.color.ColorsProvider;
import com.duyp.architecture.mvvm.data.provider.emoji.EmojiManager;
import com.duyp.architecture.mvvm.helper.DeviceNameGetter;
import com.duyp.architecture.mvvm.helper.TypeFaceHelper;
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
public class App extends Application implements HasActivityInjector {

    protected static App sInstance;

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
        setupPreference();
        TypeFaceHelper.generateTypeface(this);
        EmojiManager.load();
        ColorsProvider.load();
        DeviceNameGetter.getInstance().loadDevice();
        refWatcher = LeakCanary.install(this);
    }

    protected void initAppComponent() {
        sAppComponent = AppInjector.init(this);
    }

    public static AppComponent getAppComponent() {
        return sAppComponent;
    }

    public static App getInstance() {
        return sInstance;
    }

    private void setupPreference() {
        PreferenceManager.setDefaultValues(this, R.xml.fasthub_settings, false);
        PreferenceManager.setDefaultValues(this, R.xml.about_settings, false);
        PreferenceManager.setDefaultValues(this, R.xml.behaviour_settings, false);
        PreferenceManager.setDefaultValues(this, R.xml.customization_settings, false);
        PreferenceManager.setDefaultValues(this, R.xml.language_settings, false);
        PreferenceManager.setDefaultValues(this, R.xml.notification_settings, false);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }
}
