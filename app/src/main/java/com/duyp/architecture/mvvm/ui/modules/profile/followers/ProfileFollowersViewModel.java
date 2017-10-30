package com.duyp.architecture.mvvm.ui.modules.profile.followers;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.duyp.architecture.mvvm.data.local.user.UserManager;
import com.duyp.architecture.mvvm.data.model.Event;
import com.duyp.architecture.mvvm.data.model.User;
import com.duyp.architecture.mvvm.data.remote.UserRestService;
import com.duyp.architecture.mvvm.data.repository.FeedRepo;
import com.duyp.architecture.mvvm.helper.BundleConstant;
import com.duyp.architecture.mvvm.ui.adapter.UsersAdapter;
import com.duyp.architecture.mvvm.ui.base.BaseListDataViewModel;
import com.duyp.architecture.mvvm.ui.modules.feed.FeedAdapter;
import com.duyp.architecture.mvvm.ui.modules.profile.ProfileFollowViewModel;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by duypham on 10/28/17.
 *
 */

public class ProfileFollowersViewModel extends ProfileFollowViewModel {

    @Inject
    public ProfileFollowersViewModel(UserManager userManager, UserRestService service) {
        super(userManager, service);
    }

    @Override
    protected void callApi(int page, OnCallApiDone<User> onCallApiDone) {
        execute(true, userRestService.getFollowers(targetUser, page), userPageable -> {
            onCallApiDone.onDone(userPageable.getLast(), page == 1, userPageable.getItems());
        });
    }
}
