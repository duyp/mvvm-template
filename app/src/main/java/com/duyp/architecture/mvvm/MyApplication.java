//package com.duyp.architecture.mvvm;
//
//import android.app.Application;
//import android.content.Context;
//
//import com.duyp.architecture.mvvm.data.UserManager;
//import com.squareup.leakcanary.LeakCanary;
//import com.squareup.leakcanary.RefWatcher;
//
//import javax.inject.Inject;
//
//import lombok.Getter;
//import lombok.Setter;
//
///**
// * Created by Duy Pham on 09/2017.
// *
// */
//public class MyApplication extends Application {
//
//    private static MyApplication sInstance;
//
//    @Setter
//    private static AppComponent sAppComponent;
//
//    @Getter
//    @Inject
//    UserManager userManager;
//
//    @Getter
//    RefWatcher refWatcher;
//
//    @Override
//    protected void attachBaseContext(Context base) {
//        super.attachBaseContext(base);
//    }
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        sInstance = this;
//        sAppComponent = setupAppComponent();
//        refWatcher = LeakCanary.install(this);
//    }
//
//    // component
//    protected AppComponent setupAppComponent() {
//         return DaggerAppComponent.builder()
//                .appModule(new AppModule(this))
//                .networkModule(new NetworkModule(this))
//                .dataModule(new DataModule(this))
//                .build();
//    }
//
//    public AppComponent getAppComponent() {
//        return sAppComponent;
//    }
//}
