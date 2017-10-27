package com.duyp.architecture.mvvm.ui.modules.profile;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.duyp.androidutils.realm.LiveRealmObject;
import com.duyp.architecture.mvvm.data.local.user.UserManager;
import com.duyp.architecture.mvvm.data.model.User;
import com.duyp.architecture.mvvm.data.model.UserDetail;
import com.duyp.architecture.mvvm.data.repository.UserRepo;
import com.duyp.architecture.mvvm.helper.BundleConstant;
import com.duyp.architecture.mvvm.ui.base.BaseViewModel;
import com.duyp.architecture.mvvm.ui.navigator.NavigatorHelper;

import javax.inject.Inject;

import lombok.Getter;

/**
 * Created by duypham on 10/26/17.
 *
 */

@Getter
public class ProfileViewModel extends BaseViewModel{

    LiveRealmObject<UserDetail> user;

    private final UserRepo userRepo;

    private String userLogin;

    @Inject
    public ProfileViewModel(UserManager userManager, UserRepo userRepo) {
        super(userManager);
        this.userRepo = userRepo;
    }

    @Override
    protected void onFirsTimeUiCreate(@Nullable Bundle bundle) {
        User user = null;
        if (bundle != null) {
            user = bundle.getParcelable(BundleConstant.EXTRA);
        }
        User target = user != null ? user : userManager.getCurrentUser();
        if (target == null) {
            throw new IllegalStateException("Both target user and current user are NULL is not allowed!");
        }
        userLogin = target.getLogin();
        this.user = userRepo.initUser(target);
        refresh();
    }

    public void refresh() {
        execute(false, userRepo.getUser(userLogin), null);
    }

    public boolean isMe() {
        return isMe(userLogin);
    }

    public boolean isMyOrOrganization() {
        return isMe() && user.getData() != null && user.getData().isOrganizationType();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        userRepo.onDestroy();
    }
}
