package com.duyp.architecture.mvvm.ui.modules.profile;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;

import com.duyp.architecture.mvvm.data.local.user.UserManager;
import com.duyp.architecture.mvvm.data.model.User;
import com.duyp.architecture.mvvm.data.remote.UserRestService;
import com.duyp.architecture.mvvm.helper.BundleConstant;
import com.duyp.architecture.mvvm.ui.adapter.UsersAdapter;
import com.duyp.architecture.mvvm.ui.base.BaseListDataViewModel;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by duypham on 10/28/17.
 *
 */

public abstract class ProfileFollowViewModel extends BaseListDataViewModel<User, UsersAdapter> {

    protected String targetUser;
    protected final UserRestService userRestService;

    public ProfileFollowViewModel(UserManager userManager, UserRestService service) {
        super(userManager);
        this.userRestService = service;
    }

    @Override
    protected void onFirsTimeUiCreate(@Nullable Bundle bundle) {
        targetUser = userManager.extractUser(bundle);
        refresh(100);
    }

    @Override
    public void onItemClick(View v, User item) {
        navigatorHelper.navigateUserProfile(item);
    }
}
