package com.duyp.architecture.mvvm.base.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewTreeObserver;

import com.duyp.androidutils.AlertUtils;
import com.duyp.androidutils.CommonUtils;
import com.squareup.leakcanary.RefWatcher;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * Created by Duy Pham on 12/29/2015.
 * Base activity that will be injected automatically by implementing {@link HasSupportFragmentInjector}
 * SEE {@link com.duyp.architecture.mvvm.injection.AppInjector}
 * All fragment inside this activity is injected as well
 */
public abstract class BaseActivity extends BasePermissionActivity
        implements HasSupportFragmentInjector {

    @Inject
    protected RefWatcher refWatcher;

    // dispatch android injector to all fragments
    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupLayoutStableFullscreen();

        if (!shouldUserDataBinding()) {
            // set contentView if child activity not use dataBinding
            setContentView(getLayout());
        }

        if (shouldPostponeTransition()) {
            ActivityCompat.postponeEnterTransition(this);
        }

        CommonUtils.hideSoftKeyboard(this);
    }

    public abstract int getLayout();

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

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
        refWatcher.watch(this);
    }

    /**
     * @return true if this activity should use layout stable fullscreen (status bar overlap activity's content)
     */
    protected boolean shouldUseLayoutStableFullscreen() {
        return false;
    }

    /**
     * @return true if this activity should postpone transition (in case of destination view is in viewpager)
     */
    protected boolean shouldPostponeTransition() {
        return false;
    }

    /**
     * @return true if should register event bus (onStart - onStop)
     */
    protected boolean shouldRegisterEventBus() {
        return false;
    }

    /**
     * @return true if child activity should use data binding instead of {@link #setContentView(View)}
     */
    protected boolean shouldUserDataBinding() {
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

    /**
     * Return activity result to source activity which called {@link #startActivityForResult(Intent, int)}
     * @param data data to be returned
     */
    protected void returnResult(Intent data) {
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

    public void showToastLongMessage(String message) {
        AlertUtils.showToastLongMessage(this, message);
    }

    public void showToastShortMessage(String message){
        AlertUtils.showToastShortMessage(this, message);
    }

    private void setupLayoutStableFullscreen() {
        if (shouldUseLayoutStableFullscreen()) {
            View decorView = getWindow().getDecorView();
            // Hide the status bar.
            int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(uiOptions);
        }
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
