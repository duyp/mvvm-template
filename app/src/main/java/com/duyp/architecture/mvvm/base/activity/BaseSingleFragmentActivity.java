package com.duyp.architecture.mvvm.base.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;

import com.duyp.architecture.mvvm.base.fragment.BaseFragment;

/**
 * Created by phamd on 7/3/2017.
 * Base activity with single fragment
 */

public abstract class BaseSingleFragmentActivity<T extends BaseFragment> extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            addFragment(getContainerId(), createFragment(), null);
        }
    }

    @Override
    public int getLayout() {
//        return R.layout.container;
        return 0;
    }

    protected abstract T createFragment();

    @Nullable
    public T getFragment() {
        // noinspection unchecked
        return (T) getSupportFragmentManager().findFragmentById(getContainerId());
    }

    @IdRes
    protected int getContainerId() {
//        return R.id.container;
        return 0;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        T fragment = getFragment();
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onBackPressed() {
        T fragment = getFragment();
        if (fragment != null) {
//            if (!fragment.onBackPressed()) {
//                finishWithAnimation();
//            }
        }
    }
}
