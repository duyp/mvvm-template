package com.duyp.architecture.mvvm.ui.modules.repo.detail.code.contributors;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.duyp.architecture.mvvm.data.model.User;
import com.duyp.architecture.mvvm.databinding.RefreshRecyclerViewBinding;
import com.duyp.architecture.mvvm.ui.adapter.UsersAdapter;
import com.duyp.architecture.mvvm.ui.base.fragment.BaseRecyclerViewFragment;
import com.duyp.architecture.mvvm.ui.base.interfaces.TabBadgeListener;
import com.duyp.architecture.mvvm.ui.modules.repo.detail.RepoDetailViewModel;

/**
 * Created by duypham on 11/1/17.
 *
 */

public class ContributorsFragment extends BaseRecyclerViewFragment<RefreshRecyclerViewBinding, User, UsersAdapter, ContributorsViewModel> {

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter.setContributor(true);
        adapter.setFilter(false);
        recyclerView.addKeyLineDivider();
        RepoDetailViewModel repoDetailViewModel = ViewModelProviders.of(getActivity()).get(RepoDetailViewModel.class);
        repoDetailViewModel.getOnDataReady().observe(this, ready -> {
            if (ready != null && ready) {
                // noinspection ConstantConditions
                viewModel.initRepoDetail(repoDetailViewModel);
            }
        });
        setNoDataText("No Contributors");
        viewModel.getContributorsCount().observe(this, count -> {
            int n = count != null ? count : 0;
            Fragment fragment = getParentFragment();
            if (fragment != null && fragment instanceof TabBadgeListener) {
//                ((TabBadgeListener) fragment).setBadge(1, n);
            }
        });
    }

    @Override
    protected Class<ContributorsViewModel> getViewModelClass() {
        return ContributorsViewModel.class;
    }
}
