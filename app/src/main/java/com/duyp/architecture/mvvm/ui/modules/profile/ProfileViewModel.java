package com.duyp.architecture.mvvm.ui.modules.profile;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;

import com.duyp.androidutils.realm.LiveRealmObject;
import com.duyp.architecture.mvvm.data.local.user.UserManager;
import com.duyp.architecture.mvvm.data.model.User;
import com.duyp.architecture.mvvm.data.model.UserDetail;
import com.duyp.architecture.mvvm.data.repository.UserRepo;
import com.duyp.architecture.mvvm.helper.BundleConstant;
import com.duyp.architecture.mvvm.ui.base.BaseViewModel;
import com.duyp.architecture.mvvm.ui.navigator.NavigatorHelper;
import com.duyp.architecture.mvvm.utils.SafeMutableLiveData;

import javax.inject.Inject;

import lombok.Getter;

/**
 * Created by duypham on 10/26/17.
 *
 */

@Getter
public class ProfileViewModel extends BaseViewModel implements ViewPager.OnPageChangeListener{

    LiveRealmObject<UserDetail> user;

    private final UserRepo userRepo;

    private String userLogin;

    private SafeMutableLiveData<Integer> staredCount = new SafeMutableLiveData<>();
    private SafeMutableLiveData<Integer> currentTab = new SafeMutableLiveData<>();

    @Inject
    public ProfileViewModel(UserManager userManager, UserRepo userRepo) {
        super(userManager);
        this.userRepo = userRepo;
        currentTab.setValue(0);
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

    public void selectTab(@ProfilePagerAdapter.Tab int position) {
        currentTab.setValue(position);
    }

    public boolean isMe() {
        return isMe(userLogin);
    }

    public boolean isMyOrOrganization() {
        return isMe() || (user.getData() != null && user.getData().isOrganizationType());
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        userRepo.onDestroy();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {
        currentTab.setValue(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {}
}
