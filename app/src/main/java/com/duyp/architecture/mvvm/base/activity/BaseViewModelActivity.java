package com.duyp.architecture.mvvm.base.activity;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.duyp.androidutils.AlertUtils;
import com.duyp.architecture.mvvm.base.BaseViewModel;
import com.duyp.architecture.mvvm.data.source.State;
import com.duyp.architecture.mvvm.data.source.Status;

import java.lang.reflect.ParameterizedType;

import javax.inject.Inject;

/**
 * Created by duypham on 10/19/17.
 * Base activity class that has a ViewModel extending {@link BaseViewModel}. The viewModel will be provided automatically
 *
 * Progress showing and message showing will be handled automatically when viewModel's state changed
 * through {@link BaseViewModel#stateLiveData}
 */

public abstract class BaseViewModelActivity<B extends ViewDataBinding, T extends BaseViewModel> extends BaseActivity {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    protected T viewModel;

    protected B binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //init data binding
        binding = DataBindingUtil.setContentView(this, getLayout());

        // int view model
        // noinspection unchecked
        Class<T> viewModelClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[1]; // 1 is BaseViewModel

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(viewModelClass);

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
            if (state.isHardAlert()) {
                AlertUtils.showAlertDialog(this, state.getMessage());
            } else {
                showToastLongMessage(state.getMessage());
            }
        }
    }
}