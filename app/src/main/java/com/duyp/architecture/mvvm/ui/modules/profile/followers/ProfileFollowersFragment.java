package com.duyp.architecture.mvvm.ui.modules.profile.followers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.duyp.architecture.mvvm.data.model.Event;
import com.duyp.architecture.mvvm.data.model.User;
import com.duyp.architecture.mvvm.databinding.RefreshRecyclerViewBinding;
import com.duyp.architecture.mvvm.helper.BundleConstant;
import com.duyp.architecture.mvvm.ui.adapter.UsersAdapter;
import com.duyp.architecture.mvvm.ui.base.fragment.BaseRecyclerViewFragment;
import com.duyp.architecture.mvvm.ui.modules.feed.FeedAdapter;
import com.duyp.architecture.mvvm.ui.modules.profile.following.ProfileFollowingViewModel;
import com.duyp.architecture.mvvm.utils.FragmentUtils;

/**
 * Created by duypham on 10/28/17.
 *
 */

public class ProfileFollowersFragment extends BaseRecyclerViewFragment<RefreshRecyclerViewBinding, User, UsersAdapter, ProfileFollowersViewModel> {

    public static ProfileFollowersFragment newInstance(@Nullable String user) {
        return FragmentUtils.createFragmentInstance(new ProfileFollowersFragment(), user);
    }

    @Override
    protected Class<ProfileFollowersViewModel> getViewModelClass() {
        return ProfileFollowersViewModel.class;
    }
}
