package com.duyp.architecture.mvvm.ui.modules.profile.following;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.duyp.architecture.mvvm.data.model.User;
import com.duyp.architecture.mvvm.databinding.RefreshRecyclerViewBinding;
import com.duyp.architecture.mvvm.helper.BundleConstant;
import com.duyp.architecture.mvvm.ui.adapter.UsersAdapter;
import com.duyp.architecture.mvvm.ui.base.fragment.BaseRecyclerViewFragment;
import com.duyp.architecture.mvvm.utils.FragmentUtils;

/**
 * Created by duypham on 10/28/17.
 *
 */

public class ProfileFollowingFragment extends BaseRecyclerViewFragment<RefreshRecyclerViewBinding, User, UsersAdapter, ProfileFollowingViewModel> {

    public static ProfileFollowingFragment newInstance(@Nullable String user) {
        return FragmentUtils.createFragmentInstance(new ProfileFollowingFragment(), user);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.addKeyLineDivider();
    }

    @Override
    protected Class<ProfileFollowingViewModel> getViewModelClass() {
        return ProfileFollowingViewModel.class;
    }
}
