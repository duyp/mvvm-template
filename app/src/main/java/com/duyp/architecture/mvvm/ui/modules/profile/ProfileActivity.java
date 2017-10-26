package com.duyp.architecture.mvvm.ui.modules.profile;

import com.duyp.architecture.mvvm.R;
import com.duyp.architecture.mvvm.databinding.ActivityProfileUserBinding;
import com.duyp.architecture.mvvm.ui.base.activity.BaseViewModelActivity;

/**
 * Created by duypham on 10/26/17.
 *
 */

public class ProfileActivity extends BaseViewModelActivity<ActivityProfileUserBinding, ProfileViewModel>{

    @Override
    public int getLayout() {
        return R.layout.activity_profile_user;
    }

    @Override
    protected boolean canBack() {
        return false;
    }
}
