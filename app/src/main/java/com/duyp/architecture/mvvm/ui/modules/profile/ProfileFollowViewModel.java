package com.duyp.architecture.mvvm.ui.modules.profile;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

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

    public ProfileFollowViewModel(UserManager userManager, UserRestService service, UsersAdapter usersAdapter) {
        super(userManager, usersAdapter);
        this.userRestService = service;
    }

    @Override
    protected void onFirsTimeUiCreate(@Nullable Bundle bundle) {
        String user = null;
        if (bundle != null) {
            user = bundle.getString(BundleConstant.EXTRA);
        }
        String target = user != null ? user : userManager.getCurrentUser().getLogin();
        if (target == null) {
            throw new IllegalStateException("Both target user and current user are NULL is not allowed!");
        }
        targetUser = target;
        User user1 = new User();
        ArrayList<User> users = new ArrayList<>();
//        users.add(user1);
        setData(users, true);
        new Handler().postDelayed(this::refresh, 100);
    }

    @Override
    protected void onItemClick(User item) {
        navigatorHelper.navigateUserProfile(item);
    }
}
