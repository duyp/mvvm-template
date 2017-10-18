package com.duyp.architecture.mvvm.base.activity;

import android.os.Bundle;
import android.support.annotation.CallSuper;

import com.duyp.architecture.mvp.base.BaseView;
import com.duyp.architecture.mvp.base.presenter.BasePresenter;

import javax.inject.Inject;

import lombok.Getter;

/**
 * Created by duypham on 9/12/17.
 */

public abstract class BasePresenterActivity<V extends BaseView, P extends BasePresenter<V>> extends BaseActivity {

    @Inject
    @Getter
    protected P presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @CallSuper
    protected void initialize() {
        if (presenter != null) {
            presenter.bindView(getViewLayer());
            presenter.onCreate();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (presenter != null) {
            presenter.onStart();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (presenter != null) {
            presenter.onStop();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (presenter != null) {
            presenter.onSaveInstanceState(outState);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (presenter != null){
            presenter.onRestoreInstanceState(savedInstanceState);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (refWatcher != null && presenter != null) {
            refWatcher.watch(presenter);
        }
        if (presenter != null) {
            presenter.onDestroy();
        }
    }

    private V getViewLayer() {
        // noinspection unchecked
        return ((V)this);
    }
}
