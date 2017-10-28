package com.duyp.architecture.mvvm.ui.modules.profile.following;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.duyp.architecture.mvvm.data.local.user.UserManager;
import com.duyp.architecture.mvvm.data.model.User;
import com.duyp.architecture.mvvm.data.remote.UserRestService;
import com.duyp.architecture.mvvm.helper.BundleConstant;
import com.duyp.architecture.mvvm.ui.adapter.UsersAdapter;
import com.duyp.architecture.mvvm.ui.base.BaseListDataViewModel;
import com.duyp.architecture.mvvm.ui.modules.profile.ProfileFollowViewModel;

import javax.inject.Inject;

/**
 * Created by duypham on 10/28/17.
 *
 */

public class ProfileFollowingViewModel extends ProfileFollowViewModel {

    @Inject
    public ProfileFollowingViewModel(UserManager userManager, UserRestService service, UsersAdapter adapter) {
        super(userManager, service, adapter);
    }

    @Override
    protected void callApi(int page, OnCallApiDone<User> onCallApiDone) {
        execute(true, userRestService.getFollowing(targetUser, page), userPageable -> {
            onCallApiDone.onDone(userPageable.getLast(), page == 1, userPageable.getItems());
        });
    }
}
