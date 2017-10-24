package com.duyp.architecture.mvvm.ui.modules.login;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.duyp.architecture.mvvm.R;
import com.duyp.architecture.mvvm.data.source.State;
import com.duyp.architecture.mvvm.databinding.ActivityLoginBinding;
import com.duyp.architecture.mvvm.ui.base.activity.BaseViewModelActivity;

/**
 * Created by duypham on 10/21/17.
 *
 */

public class LoginActivity extends BaseViewModelActivity<ActivityLoginBinding, LoginViewModel> {

    @Override
    public int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected boolean canBack() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding.setVm(viewModel);
    }

    @Override
    protected void handleState(@Nullable State state) {
        if (state != null) {
            binding.setState(state);
            if (state.getMessage() != null) {
                showToastLongMessage(state.getMessage());
            }
        }
    }
}
