package com.duyp.architecture.mvvm.ui.base.fragment;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.duyp.androidutils.AlertUtils;
import com.duyp.architecture.mvvm.data.source.State;
import com.duyp.architecture.mvvm.data.source.Status;
import com.duyp.architecture.mvvm.injection.Injectable;
import com.duyp.architecture.mvvm.ui.base.BaseViewModel;
import com.duyp.architecture.mvvm.ui.base.activity.BaseActivity;

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

//    @Inject
//    ViewModelProvider.Factory viewModelFactory;

    @Inject
    protected VM viewModel;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (! (getActivity() instanceof BaseActivity)) {
            throw new IllegalStateException("All fragment's container must extend BaseActivity");
        }

        // noinspection unchecked
//        Class<VM> viewModelClass = (Class<VM>) ((ParameterizedType) getClass()
//                .getGenericSuperclass()).getActualTypeArguments()[1]; // 1 is BaseViewModel

//        viewModel = ViewModelProviders.of(this, viewModelFactory).get(viewModelClass);

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
            } else {
                AlertUtils.showToastLongMessage(getContext(), state.getMessage());
            }
        }
    }

    public void setLoading(boolean loading) {
        ((BaseActivity)getActivity()).setLoading(loading);
    }
}
