package com.duyp.architecture.mvvm.ui.modules.profile.overview;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.duyp.architecture.mvvm.R;
import com.duyp.architecture.mvvm.data.model.UserDetail;
import com.duyp.architecture.mvvm.data.source.Resource;
import com.duyp.architecture.mvvm.data.source.State;
import com.duyp.architecture.mvvm.data.source.Status;
import com.duyp.architecture.mvvm.databinding.ProfileOverviewBinding;
import com.duyp.architecture.mvvm.helper.InputHelper;
import com.duyp.architecture.mvvm.helper.ParseDateFormat;
import com.duyp.architecture.mvvm.ui.base.fragment.BaseViewModelFragment;
import com.duyp.architecture.mvvm.ui.modules.profile.ProfileViewModel;
import com.duyp.architecture.mvvm.ui.widgets.SpannableBuilder;
import com.duyp.architecture.mvvm.ui.widgets.contributions.ContributionsDay;
import com.duyp.architecture.mvvm.ui.widgets.recyclerview.layout_manager.GridManager;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.disposables.Disposable;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by duypham on 10/27/17.
 * 
 */

public class OverviewFragment extends BaseViewModelFragment<ProfileOverviewBinding, OverviewViewModel> {

    ProfileViewModel profileViewModel;

    private Disposable disposable;

    @Override
    protected int getLayout() {
        return R.layout.profile_overview;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        profileViewModel = ViewModelProviders.of(getActivity()).get(ProfileViewModel.class);
        profileViewModel.getUser().observe(this, this::populateUserDetail);

        if (profileViewModel.isMyOrOrganization()) {
            binding.follow.followBtn.setVisibility(GONE);
        } else {
            binding.follow.followBtn.setVisibility(VISIBLE);
            binding.follow.setVm(viewModel);
        }
        viewModel.initUser(profileViewModel.getUser().getData().getLogin(), profileViewModel);
        viewModel.getFollowState().observe(this, this::invalidateFollowBtn);
        viewModel.getOrgansState().observe(this, this::invalidateOrgans);
        viewModel.getPinnedState().observe(this, this::invalidatePinned);
        viewModel.getContributionsData().observe(this, this::invalidateContributions);

        binding.organizationList.setAdapter(viewModel.getOrganizationAdapter());
        ((GridManager) binding.organizationList.getLayoutManager()).setIconSize(getResources().getDimensionPixelSize(R.dimen.header_icon_zie) + getResources()
                .getDimensionPixelSize(R.dimen.spacing_xs_large));

        binding.pinnedList.setAdapter(viewModel.getPinnedAdapter());
        binding.pinnedList.addDivider();
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
        if (binding.follow.followBtn.getVisibility() == VISIBLE) {
            binding.follow.followBtn.setEnabled(state != OverviewViewModel.FollowingState.LOADING);
            binding.follow.followBtn.setActivated(state == OverviewViewModel.FollowingState.FOLLOWED);

            binding.follow.followBtn.setText(state == OverviewViewModel.FollowingState.FOLLOWED
                    ? getString(R.string.unfollow) : getString(R.string.follow));
        }
    }

    public void invalidateOrgans(State state) {
        binding.organizationPb.setVisibility(state.getStatus() == Status.LOADING ? VISIBLE : GONE);
        binding.organizationsCaption.setVisibility(state.getStatus() == Status.SUCCESS ? VISIBLE : GONE);
        binding.organsLayout.setVisibility(state.getStatus() == Status.ERROR ? GONE : VISIBLE);
    }

    public void invalidatePinned(State state) {
        binding.pinnedReposTextView.setVisibility(state.getStatus() == Status.SUCCESS ? VISIBLE : GONE);
        binding.pinnedLayout.setVisibility(state.getStatus() == Status.SUCCESS ? VISIBLE : GONE);
    }

    public void invalidateContributions(Resource<List<ContributionsDay>> data) {
        binding.contributionsCaption.setVisibility(data.getState().getStatus() == Status.SUCCESS ? VISIBLE : GONE);
        if (data.getData() != null) {
            List<ContributionsDay> filter = binding.contributionView.getLastContributions(data.data);
            if (filter != null) {
                disposable = Completable.create( e -> {
                    binding.contributionView.drawOnCanvas(filter, data.getData());
                     e.onComplete();
                }).subscribe(() -> {
                    binding.contributionLayout.setVisibility(VISIBLE);
                });
            }
        } else {
            binding.contributionLayout.setVisibility(GONE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (disposable != null) {
            disposable.dispose();
        }
    }

    @Override
    protected Class<OverviewViewModel> getViewModelClass() {
        return OverviewViewModel.class;
    }
}
