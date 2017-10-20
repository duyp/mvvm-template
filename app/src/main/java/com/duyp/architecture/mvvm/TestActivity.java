package com.duyp.architecture.mvvm;

import android.arch.lifecycle.LiveData;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.duyp.architecture.mvvm.base.activity.BaseActivity;
import com.duyp.architecture.mvvm.base.activity.BaseViewModelActivity;
import com.duyp.architecture.mvvm.data.remote.GithubService;
import com.duyp.architecture.mvvm.model.User;
import com.duyp.architecture.mvvm.utils.AvatarLoader;
import com.duyp.architecture.mvvm.utils.NavigatorHelper;
import com.duyp.architecture.mvvm.utils.qualifier.ActivityFragmentManager;

import javax.inject.Inject;

/**
 * Created by duypham on 10/20/17.
 * This activity is injected automatically, and its viewModel is provided as well
 * Not need any activityComponent().inject(this) or AndroidInjector.inject(this)
 *
 * SEE {@link com.duyp.architecture.mvvm.injection.AppInjector}
 */

public class TestActivity extends BaseViewModelActivity<TestViewModel> {

    @Inject
    NavigatorHelper navigatorHelper;

    @Inject
    AvatarLoader avatarLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.ensureInUserScope(this::populateUserInfo, this::showLoginView);
    }

    private void showLoginView() {
        showToastLongMessage("Login page hasn't been implemented yet! Data binding will be implemented soon :)");
    }

    private void populateUserInfo(LiveData<User> userLiveData) {
        userLiveData.observe(this, user -> {
            // populate user info here
        });
    }

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }
}

