package com.duyp.architecture.mvvm.ui.base.fragment;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.duyp.androidutils.AlertUtils;
import com.duyp.architecture.mvvm.data.source.State;
import com.duyp.architecture.mvvm.data.source.Status;
import com.duyp.architecture.mvvm.injection.Injectable;
import com.duyp.architecture.mvvm.ui.base.BaseViewModel;
import com.duyp.architecture.mvvm.ui.base.activity.BaseActivity;
import com.duyp.architecture.mvvm.ui.navigator.NavigatorHelper;

import java.lang.reflect.ParameterizedType;

import javax.inject.Inject;

/**
 * Created by duypham on 10/20/17.
 * Base fragment class that has a ViewModel extending {@link BaseViewModel}.
 * All fragments extend this fragment must be added into {@link BaseActivity}
 *
 * Progress showing and message showing will be handled automatically when viewModel's state changed
 * through {@link BaseViewModel#stateLiveData}
 */

public abstract class BaseViewModelFragment<B extends ViewDataBinding, VM extends BaseViewModel> extends BaseFragment<B>
    implements Injectable {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

//    @Inject
    protected VM viewModel;

    @Inject
    NavigatorHelper navigatorHelper;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (! (getActivity() instanceof BaseActivity)) {
            throw new IllegalStateException("All fragment's container must extend BaseActivity");
        }
    }

    @Override
    @CallSuper
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(getViewModelClass());
        viewModel.onCreate(getFragmentArguments(), navigatorHelper);

        viewModel.getStateLiveData().observe(this, this::handleState);
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
                AlertUtils.showAlertDialog(getContext(), state.getMessage());
                Log.d(TAG, "handleMessageState: " + state.getMessage());
            } else {
                AlertUtils.showToastShortMessage(getContext(), state.getMessage());
                Log.d(TAG, "handleMessageState: " + state.getMessage());
            }
        }
    }

    public void setLoading(boolean loading) {
        ((BaseActivity)getActivity()).setLoading(loading);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewModel.onDestroyView();
    }

    protected abstract Class<VM> getViewModelClass();
}
