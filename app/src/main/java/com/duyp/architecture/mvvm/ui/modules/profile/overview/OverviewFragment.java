package com.duyp.architecture.mvvm.ui.modules.profile.overview;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.duyp.architecture.mvvm.R;
import com.duyp.architecture.mvvm.data.model.UserDetail;
import com.duyp.architecture.mvvm.databinding.ProfileOverviewBinding;
import com.duyp.architecture.mvvm.helper.InputHelper;
import com.duyp.architecture.mvvm.helper.ParseDateFormat;
import com.duyp.architecture.mvvm.ui.base.fragment.BaseViewModelFragment;
import com.duyp.architecture.mvvm.ui.modules.profile.ProfileViewModel;
import com.duyp.architecture.mvvm.ui.widgets.SpannableBuilder;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by duypham on 10/27/17.
 * 
 */

public class OverviewFragment extends BaseViewModelFragment<ProfileOverviewBinding, OverviewViewModel> {

    ProfileViewModel profileViewModel;

    @Override
    protected int getLayout() {
        return R.layout.profile_overview;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        profileViewModel = ViewModelProviders.of(getActivity()).get(ProfileViewModel.class);
        profileViewModel.getUser().observe(this, this::populateUserDetail);

        binding.follow.setVm(viewModel);
        viewModel.initUser(profileViewModel.getUser().getData().getLogin(), profileViewModel.isMyOrOrganization());
        viewModel.getFollowState().observe(this, this::invalidateFollowBtn);
    }

    private void populateUserDetail(UserDetail user) {
        Log.d(TAG, "populateUserDetail: " + user);
        binding.organization.setText(user.getCompany());
        binding.location.setText(user.getLocation());
        binding.email.setText(user.getEmail());
        binding.link.setText(user.getBlog());
        binding.joined.setText(ParseDateFormat.getTimeAgo(user.getCreatedAt()));

        binding.organization.setVisibility(InputHelper.isEmpty(user.getCompany()) ? GONE : VISIBLE);
        binding.location.setVisibility(InputHelper.isEmpty(user.getLocation()) ? GONE : VISIBLE);
        binding.email.setVisibility(InputHelper.isEmpty(user.getEmail()) ? GONE : VISIBLE);
        binding.link.setVisibility(InputHelper.isEmpty(user.getBlog()) ? GONE : VISIBLE);
        binding.joined.setVisibility(InputHelper.isEmpty(user.getCreatedAt()) ? GONE : VISIBLE);
        
        binding.follow.followers.setText(SpannableBuilder.builder()
                .append(getString(R.string.followers))
                .append(" (")
                .bold(String.valueOf(user.getFollowers()))
                .append(")"));
        binding.follow.following.setText(SpannableBuilder.builder()
                .append(getString(R.string.following))
                .append(" (")
                .bold(String.valueOf(user.getFollowing()))
                .append(")"));
    }

    public void invalidateFollowBtn(@Nullable OverviewViewModel.FollowingState state) {
        if (state == null) {
            binding.follow.followBtn.setVisibility(GONE);
            return;
        }
        binding.follow.followBtn.setVisibility(View.VISIBLE);

        binding.follow.followBtn.setEnabled(state != OverviewViewModel.FollowingState.LOADING);
        binding.follow.followBtn.setActivated(state == OverviewViewModel.FollowingState.FOLLOWED);

        binding.follow.followBtn.setText(state == OverviewViewModel.FollowingState.FOLLOWED
                ? getString(R.string.unfollow) : getString(R.string.follow));
    }
    
    @Override
    protected Class<OverviewViewModel> getViewModelClass() {
        return OverviewViewModel.class;
    }
}
