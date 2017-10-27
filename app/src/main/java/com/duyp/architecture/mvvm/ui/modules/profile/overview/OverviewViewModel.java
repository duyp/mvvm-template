package com.duyp.architecture.mvvm.ui.modules.profile.overview;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.duyp.architecture.mvvm.data.local.user.UserManager;
import com.duyp.architecture.mvvm.data.remote.UserRestService;
import com.duyp.architecture.mvvm.ui.base.BaseViewModel;

import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by duypham on 10/27/17.
 *
 */

public class OverviewViewModel extends BaseViewModel {

    private String user;

    @Getter
    private MutableLiveData<FollowingState> followState = new MutableLiveData<>();

    private final UserRestService userRestService;
    @Inject
    public OverviewViewModel(UserManager userManager, UserRestService service) {
        super(userManager);
        this.userRestService = service;
    }

    public void initUser(String userLogin, boolean isMeOrOrganization) {
        this.user = userLogin;
        followState.setValue(null);
        if (!isMeOrOrganization) {
            checkFollowState();
        }
    }

    public void checkFollowState() {
        execute(false, userRestService.getFollowStatus(user), booleanResponse -> {
            followState.setValue(booleanResponse.code() == 204 ? FollowingState.FOLLOWED : FollowingState.UNFOLLOWED);
        }, errorEntity -> {
            followState.setValue(null);
        });
    }

    public void onFollowClick() {
        final FollowingState oldState = followState.getValue();
        final boolean followed = oldState == FollowingState.FOLLOWED;
        followState.setValue(FollowingState.LOADING);
        execute(false, followed ? userRestService.unfollowUser(user) : userRestService.followUser(user), booleanResponse -> {
            boolean success = booleanResponse.code() == 204;
            if (followed) {
                followState.setValue(success ? FollowingState.UNFOLLOWED : FollowingState.FOLLOWED);
            } else {
                followState.setValue(success ? FollowingState.FOLLOWED : FollowingState.UNFOLLOWED);
            }
        }, errorEntity -> {
            followState.setValue(oldState);
        });
    }

    @Override
    protected void onFirsTimeUiCreate(@Nullable Bundle bundle) {}

    public enum FollowingState {
        LOADING,
        FOLLOWED,
        UNFOLLOWED
    }
}
