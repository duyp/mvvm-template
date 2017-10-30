package com.duyp.architecture.mvvm.ui.base.activity;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import com.duyp.androidutils.AlertUtils;
import com.duyp.architecture.mvvm.data.source.State;
import com.duyp.architecture.mvvm.data.source.Status;
import com.duyp.architecture.mvvm.ui.base.BaseViewModel;
import com.duyp.architecture.mvvm.ui.navigator.NavigatorHelper;

import java.lang.reflect.ParameterizedType;

import javax.inject.Inject;

/**
 * Created by duypham on 10/19/17.
 * Base activity class that has a ViewModel extending {@link BaseViewModel}. The viewModel will be provided automatically
 *
 * Progress showing and message showing will be handled automatically when viewModel's state changed
 * through {@link BaseViewModel#stateLiveData}
 */

public abstract class BaseViewModelActivity<B extends ViewDataBinding, VM extends BaseViewModel> extends BaseActivity {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    public String TAG;

    protected VM viewModel;

    @Inject NavigatorHelper navigatorHelper;

    @VisibleForTesting
    protected B binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TAG = getClass().getSimpleName();

        //init data binding
        binding = DataBindingUtil.setContentView(this, getLayout());
        initViews();

        // int view model
        // noinspection unchecked
        Class<VM> viewModelClass = (Class<VM>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[1]; // 1 is BaseViewModel

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(viewModelClass);

        viewModel.onCreate(getIntent().getExtras(), navigatorHelper);
        viewModel.getStateLiveData().observe(this, this::handleState);
    }

    @Override
    protected boolean shouldUseDataBinding() {
        return true;
    }

    /**
     * Default state handling, can be override
     * @param state viewModel's state
     */
    protected void handleState(@Nullable State state) {
        setLoading(state != null && state.getStatus() == Status.LOADING);
        handleMessageState(state);
    }

    protected void handleMessageState(@Nullable State state) {
        if (state != null && state.getMessage() != null) {
            if (state.isHardAlert()) {
                AlertUtils.showAlertDialog(this, state.getMessage());
                Log.d(TAG, "handleMessageState: " + state.getMessage());
            } else {
                showToastLongMessage(state.getMessage());
                Log.d(TAG, "handleMessageState: " + state.getMessage());
            }
        }
    }

    public void navigateLogin() {
        navigatorHelper.navigateLoginActivity(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewModel.onDestroyView(); // importance
    }
}