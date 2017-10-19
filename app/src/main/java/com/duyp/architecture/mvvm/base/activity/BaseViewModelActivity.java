package com.duyp.architecture.mvvm.base.activity;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.duyp.architecture.mvvm.base.BaseViewModel;
import com.duyp.architecture.mvvm.data.source.State;
import com.duyp.architecture.mvvm.data.source.Status;

import javax.inject.Inject;

/**
 * Created by duypham on 10/19/17.
 * Base activity class that has a {@link BaseViewModel}. The viewModel will be provide automatically
 * All children classes and all inside fragment will be inject automatically
 * by implementing {@link dagger.android.support.HasSupportFragmentInjector}
 * SEE {@link com.duyp.architecture.mvvm.injection.AppInjector}
 *
 * Progress showing and message showing will be handled automatically when viewModel 's state changed
 * through {@link BaseViewModel#stateLiveData}
 */

public abstract class BaseViewModelActivity<T extends BaseViewModel> extends BaseActivity {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    T viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(getViewModelClass());

        viewModel.getStateLiveData().observe(this, state -> {
            if (state != null) {
                if (state.getStatus() == Status.LOADING) {
                    showProgressDialog();
                } else {
                    hideProgressDialog();
                    handleMessageState(state);
                }
            } else {
                hideProgressDialog();
            }
        });
    }

    protected void handleMessageState(@NonNull State state) {
        if (state.getMessage() != null) {
            showToastLongMessage(state.getMessage());
        }
    }

    protected abstract Class<T> getViewModelClass();
}
