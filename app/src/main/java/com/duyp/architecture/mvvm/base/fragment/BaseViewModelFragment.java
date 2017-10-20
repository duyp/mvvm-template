package com.duyp.architecture.mvvm.base.fragment;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.duyp.androidutils.AlertUtils;
import com.duyp.architecture.mvvm.base.BaseViewModel;
import com.duyp.architecture.mvvm.base.activity.BaseActivity;
import com.duyp.architecture.mvvm.data.source.State;
import com.duyp.architecture.mvvm.data.source.Status;
import com.duyp.architecture.mvvm.injection.Injectable;

import java.lang.reflect.ParameterizedType;

import javax.inject.Inject;

/**
 * Created by duypham on 10/20/17.
 * Base fragment class that has a ViewModel extending {@link BaseViewModel}. The viewModel will be provided automatically
 * All fragments extend this fragment must be added into {@link BaseActivity}
 *
 * Progress showing and message showing will be handled automatically when viewModel's state changed
 * through {@link BaseViewModel#stateLiveData}
 */

public abstract class BaseViewModelFragment<B extends ViewDataBinding, VM extends BaseViewModel> extends BaseFragment<B>
    implements Injectable {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    protected VM viewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // int view model
        // noinspection unchecked
        Class<VM> viewModelClass = (Class<VM>) ((ParameterizedType) getClass()
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

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (! (getActivity() instanceof BaseActivity)) {
            throw new IllegalStateException("All fragments must extend BaseActivity");
        }
    }

    protected void handleMessageState(@NonNull State state) {
        if (state.getMessage() != null) {
            if (state.isHardAlert()) {
                AlertUtils.showAlertDialog(getContext(), state.getMessage());
            } else {
                AlertUtils.showToastLongMessage(getContext(), state.getMessage());
            }
        }
    }

    private void hideProgressDialog() {
        ((BaseActivity)getActivity()).hideProgressDialog();
    }

    private void showProgressDialog() {
        ((BaseActivity)getActivity()).showProgressDialog();
    }
}
