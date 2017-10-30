package com.duyp.architecture.mvvm.ui.base.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;

import com.duyp.androidutils.AlertUtils;
import com.duyp.androidutils.CommonUtils;
import com.duyp.architecture.mvvm.R;
import com.duyp.architecture.mvvm.helper.PrefGetter;
import com.duyp.architecture.mvvm.helper.ViewHelper;
import com.duyp.architecture.mvvm.ui.modules.main.MainActivity;
import com.squareup.leakcanary.RefWatcher;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

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

    @Nullable protected AppBarLayout appBar;
    @Nullable protected Toolbar toolbar;
    private long backPressTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupLayoutStableFullscreen();

        if (!shouldUseDataBinding()) {
            // set contentView if child activity not use dataBinding
            setContentView(getLayout());
            initViews();
        }

        if (shouldPostponeTransition()) {
            ActivityCompat.postponeEnterTransition(this);
        }

        CommonUtils.hideSoftKeyboard(this);
    }

    protected void initViews() {
        appBar = findViewById(R.id.appbar);
        toolbar = findViewById(R.id.toolbar);
        setupToolbarAndStatusBar();
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
        refWatcher.watch(this);
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        if (canBack()) {
            if (item.getItemId() == android.R.id.home) {
                onBackPressed();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * @return true if should use transparent status bar
     */
    protected boolean isTransparent() {
        return true;
    }

    /**
     * @return true if should use back button on toolbar
     */
    protected abstract boolean canBack();

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
    protected boolean shouldUseDataBinding() {
        return false;
    }

    // ========================================================================================
    // UI setting
    // ========================================================================================

    private void setupToolbarAndStatusBar() {
        changeStatusBarColor(isTransparent());
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            if (canBack()) {
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    if (canBack()) {
                        View navIcon = getToolbarNavigationIcon(toolbar);
                        if (navIcon != null) {
                            navIcon.setOnLongClickListener(v -> {
                                Intent intent = new Intent(this, MainActivity.class);
                                startActivity(intent);
                                finish();
                                return true;
                            });
                        }
                    }
                }
            }
        }
    }

    protected void setToolbarIcon(@DrawableRes int res) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(res);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    protected void hideShowShadow(boolean show) {
        if (appBar != null) {
            appBar.setElevation(show ? getResources().getDimension(R.dimen.spacing_micro) : 0.0f);
        }
    }

    protected void changeStatusBarColor(boolean isTransparent) {
        if (!isTransparent) {
            getWindow().setStatusBarColor(ViewHelper.getPrimaryDarkColor(this));
        }
    }

    @Override
    public void onBackPressed() {
        boolean clickTwiceToExit = !PrefGetter.isTwiceBackButtonDisabled();
        superOnBackPressed(clickTwiceToExit);
    }

    private void superOnBackPressed(boolean didClickTwice) {
        if (this instanceof MainActivity) {
            if (didClickTwice) {
                if (canExit()) {
                    super.onBackPressed();
                }
            } else {
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }

    private boolean canExit() {
        if (backPressTimer + 2000 > System.currentTimeMillis()) {
            return true;
        } else {
            showToastLongMessage(getString(R.string.press_again_to_exit));
        }
        backPressTimer = System.currentTimeMillis();
        return false;
    }

    @Nullable private View getToolbarNavigationIcon(android.support.v7.widget.Toolbar toolbar) {
        boolean hadContentDescription = TextUtils.isEmpty(toolbar.getNavigationContentDescription());
        String contentDescription = !hadContentDescription ? String.valueOf(toolbar.getNavigationContentDescription()) : "navigationIcon";
        toolbar.setNavigationContentDescription(contentDescription);
        ArrayList<View> potentialViews = new ArrayList<>();
        toolbar.findViewsWithText(potentialViews, contentDescription, View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION);
        View navIcon = null;
        if (potentialViews.size() > 0) {
            navIcon = potentialViews.get(0);
        }
        if (hadContentDescription) toolbar.setNavigationContentDescription(null);
        return navIcon;
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
    // ========================================================================================
    // Progress showing
    // ========================================================================================

    private ProgressDialog progress_dialog;
    public void showProgressDialog() {
        showProgressDialog(null);
    }

    public void showProgressDialog(@Nullable String message) {
        if (progress_dialog == null) {
            progress_dialog = new ProgressDialog(this);
        }

        if (!progress_dialog.isShowing()) {
            progress_dialog.setMessage(message == null? "Loading" : message);
            progress_dialog.setCancelable(false);
            progress_dialog.show();
        }

    }

    public void hideProgressDialog() {
        if (progress_dialog != null && progress_dialog.isShowing()) {
            progress_dialog.dismiss();
        }
    }

    public void setLoading(boolean loading) {
        if (loading) {
            showProgressDialog();
        } else {
            hideProgressDialog();
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

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
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
