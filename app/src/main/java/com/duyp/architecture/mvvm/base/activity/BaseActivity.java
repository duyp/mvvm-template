package com.duyp.architecture.mvvm.base.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewTreeObserver;

import com.duyp.androidutils.AlertUtils;
import com.duyp.androidutils.CommonUtils;
import com.duyp.androidutils.functions.PlainAction;
import com.duyp.androidutils.functions.PlainConsumer;
import com.duyp.architecture.mvp.app.MyApplication;
import com.duyp.architecture.mvp.base.BaseView;
import com.duyp.architecture.mvp.dagger.InjectionHelper;
import com.duyp.architecture.mvp.dagger.component.ActivityComponent;
import com.duyp.architecture.mvp.dagger.component.DaggerActivityComponent;
import com.duyp.architecture.mvp.dagger.component.UserActivityComponent;
import com.duyp.architecture.mvp.dagger.module.ActivityModule;
import com.duyp.architecture.mvp.data.local.user.UserManager;
import com.duyp.architecture.mvp.data.model.base.ErrorEntity;
import com.squareup.leakcanary.RefWatcher;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Duy Pham on 12/29/2015.
 * Base activity
 */
public abstract class BaseActivity extends BasePermissionActivity implements BaseView, LifecycleRegistryOwner {

    private final LifecycleRegistry mRegistry = new LifecycleRegistry(this);

    @Override
    public LifecycleRegistry getLifecycle() {
        return mRegistry;
    }

    private ActivityComponent mActivityComponent;

    private UserActivityComponent mUserActivityComponent;

    protected RefWatcher refWatcher;

    @Override
    protected void attachBaseContext(Context newBase) {
        // for custom font
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        refWatcher = MyApplication.get(this).getRefWatcher();

        if (shouldUseLayoutStableFullscreen()) {
            View decorView = getWindow().getDecorView();
            // Hide the status bar.
            int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(uiOptions);
        }

        setContentView(getLayout());

        if (shouldPostponeTransition()) {
            ActivityCompat.postponeEnterTransition(this);
        }

        ButterKnife.bind(this);
        CommonUtils.hideSoftKeyboard(this);
    }

    protected void ensureInUserScope(@NonNull PlainConsumer<UserActivityComponent> componentConsumer,
                                     @NonNull PlainAction onError) {
        UserManager userManager = InjectionHelper.getAppComponent(this).userManager();
        if (userManager.checkForSavedUserAndStartSessionIfHas()) {
            // noinspection ConstantConditions
            mUserActivityComponent = userManager.getUserComponent().getUserActivityComponent(new ActivityModule(this));
            componentConsumer.accept(mUserActivityComponent);
        } else {
            onError.run();
        }
    }

    public abstract int getLayout();

    @Override
    protected void onStart() {
        super.onStart();
        if (shouldRegisterEventBus()) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (shouldRegisterEventBus()) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (refWatcher != null) {
            if(mActivityComponent != null) {
                refWatcher.watch(mActivityComponent);
            }
            refWatcher.watch(this);
            if (mUserActivityComponent != null) {
                refWatcher.watch(mUserActivityComponent);
            }
        }
        mUserActivityComponent = null;
        mActivityComponent = null;
    }

    // activity component, activity may or may not need this
    public ActivityComponent activityComponent() {
        if (mActivityComponent == null) {
            mActivityComponent = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule(this))
                    .appComponent(InjectionHelper.getAppComponent(this))
                    .build();
        }
        return mActivityComponent;
    }

    public void addFragment(@IdRes int res, Fragment fragment, @Nullable String tag) {
        getSupportFragmentManager().beginTransaction()
                .add(res, fragment, tag)
                .commit();
    }

    public void replaceFragmentWithBackStack(@IdRes int frameId, Fragment fragment, @Nullable String tag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(frameId, fragment, tag);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    protected boolean shouldUseLayoutStableFullscreen() {
        return false;
    }

    protected boolean shouldPostponeTransition() {
        return false;
    }

    protected boolean shouldRegisterEventBus() {
        return false;
    }

    private ProgressDialog progress_dialog;

    public void showProgressDialog(String... message) {
        if (progress_dialog == null) {
            progress_dialog = new ProgressDialog(this);
        }

        if (!progress_dialog.isShowing()) {
            progress_dialog.setMessage(message == null || message.length == 0 ? "Loading" : message[0]);
            progress_dialog.setCancelable(false);
            progress_dialog.show();
        }

    }

    public void hideProgressDialog() {
        if (progress_dialog != null && progress_dialog.isShowing()) {
            progress_dialog.dismiss();
        }
    }

    @Override
    public void showProgress() {
        showProgressDialog();
    }

    @Override
    public void showProgress(String message) {
        showProgressDialog(message);
    }

    @Override
    public void hideProgress() {
        hideProgressDialog();
    }

    @Override
    public void setProgress(boolean show) {
        if (show) {
            showProgress();
        } else {
            hideProgress();
        }
    }

    @Override
    public void showMessage(String message) {
        showToastLongMessage(message);
    }

    @Override
    public void onError(ErrorEntity errorEntity) {
        AlertUtils.showToastLongMessage(this, errorEntity.getMessage());
    }

    public void returnResult(Intent data) {
        if (getParent() == null) {
            setResult(Activity.RESULT_OK, data);
        } else {
            getParent().setResult(Activity.RESULT_OK, data);
        }
        finish();
    }

    protected void finishWithAnimation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else {
            finish();
        }
    }

    public void showDialog(DialogFragment dialog) {

        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // show the dialog.
        dialog.show(ft, "dialog");
    }

    public void showToastLongMessage(String message) {
        AlertUtils.showToastLongMessage(this, message);
    }

    public void showToastShortMessage(String message){
        AlertUtils.showToastShortMessage(this, message);
    }

    /**
     * Schedules the shared element transition to be started immediately
     * after the shared element has been measured and laid out within the
     * activity's view hierarchy. Some common places where it might make
     * sense to call this method are:
     *
     * (1) Inside a Fragment's onCreateView() method (if the shared element
     *     lives inside a Fragment hosted by the called Timeline).
     *
     * (2) Inside a Picasso Callback object (if you need to wait for Picasso to
     *     asynchronously load/scale a bitmap before the transition can begin).
     *
     * (3) Inside a LoaderCallback's onLoadFinished() method (if the shared
     *     element depends on data queried by a Loader).
     */
    public void scheduleStartPostponedTransition(@NonNull final View sharedElement) {
        sharedElement.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        sharedElement.getViewTreeObserver().removeOnPreDrawListener(this);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            startPostponedEnterTransition();
                        }
                        return true;
                    }
                });
    }
}
