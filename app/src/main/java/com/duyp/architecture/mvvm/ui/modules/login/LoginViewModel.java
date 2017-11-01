package com.duyp.architecture.mvvm.ui.modules.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.duyp.androidutils.AlertUtils;
import com.duyp.androidutils.StringUtils;
import com.duyp.architecture.mvvm.App;
import com.duyp.architecture.mvvm.data.local.user.UserManager;
import com.duyp.architecture.mvvm.data.remote.GithubService;
import com.duyp.architecture.mvvm.data.source.State;
import com.duyp.architecture.mvvm.helper.InputHelper;
import com.duyp.architecture.mvvm.helper.RestHelper;
import com.duyp.architecture.mvvm.ui.base.BaseViewModel;

import javax.inject.Inject;

/**
 * Created by duypham on 10/21/17.
 *
 */

public class LoginViewModel extends BaseViewModel {

    private final GithubService githubService;

    public String userName = "";
    public String password = "";

    @Inject
    LoginViewModel(GithubService githubService, UserManager userManager) {
        super(userManager);
        Log.d(TAG, "LoginViewModel: init");
        this.githubService = githubService;
    }

    public void login() {
        if (InputHelper.isEmpty(userName) || InputHelper.isEmpty(password)) {
            AlertUtils.showToastShortMessage(App.getInstance(), "Please input username and password!");
            return;
        }
        String auth = StringUtils.getBasicAuth(userName, password);
        execute(true, RestHelper.createRemoteSourceMapper(githubService.login(auth), null), user -> {
            userManager.startUserSession(user, auth);
            navigatorHelper.navigateMainActivity(true);
        });
    }

    @Override
    protected void onFirsTimeUiCreate(@Nullable Bundle bundle) {}
}
