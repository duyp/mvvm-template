package com.duyp.architecture.mvvm.ui.modules.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.duyp.architecture.mvvm.data.local.user.UserManager;
import com.duyp.architecture.mvvm.data.model.User;
import com.duyp.architecture.mvvm.helper.BundleConstant;
import com.duyp.architecture.mvvm.ui.base.BaseViewModel;

import org.parceler.Parcels;

import javax.inject.Inject;

import lombok.Getter;

/**
 * Created by duypham on 10/26/17.
 *
 */

public class ProfileViewModel extends BaseViewModel{

    @Getter
    User targetUser;

    @Getter
    boolean isFollowed = false;

    @Inject
    public ProfileViewModel(UserManager userManager) {
        super(userManager);
    }

    @Override
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        User user = null;
        if (bundle != null) {
            user = Parcels.unwrap(bundle.getParcelable(BundleConstant.EXTRA));
        }
        targetUser = user != null ? user : userManager.getCurrentUser();
    }
}
