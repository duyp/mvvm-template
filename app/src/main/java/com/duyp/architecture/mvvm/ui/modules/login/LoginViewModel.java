package com.duyp.architecture.mvvm.ui.modules.login;

import com.duyp.androidutils.StringUtils;
import com.duyp.architecture.mvvm.base.BaseViewModel;
import com.duyp.architecture.mvvm.data.UserManager;
import com.duyp.architecture.mvvm.data.remote.GithubService;
import com.duyp.architecture.mvvm.utils.ApiUtils;

import javax.inject.Inject;

import io.reactivex.Flowable;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by duypham on 10/21/17.
 *
 */

@Getter
@Setter
public class LoginViewModel extends BaseViewModel {

    private final GithubService githubService;
    private final UserManager userManager;

    private String userName = "duyp1";
    private String password = "Duy1234";

    @Inject
    public LoginViewModel(GithubService githubService, UserManager userManager) {
        this.githubService = githubService;
        this.userManager = userManager;
    }

    public void login() {
        String auth = StringUtils.getBasicAuth(userName, password);
        execute(ApiUtils.createRemoteSourceMapper(githubService.login(auth), null), user -> {
            userManager.startUserSession(user, auth);
        });
    }
}
