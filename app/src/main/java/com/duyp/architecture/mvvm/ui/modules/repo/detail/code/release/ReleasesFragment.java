package com.duyp.architecture.mvvm.ui.modules.repo.detail.code.release;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.duyp.architecture.mvvm.data.model.Release;
import com.duyp.architecture.mvvm.databinding.RefreshRecyclerViewBinding;
import com.duyp.architecture.mvvm.ui.base.fragment.BaseRecyclerViewFragment;
import com.duyp.architecture.mvvm.ui.modules.repo.detail.RepoDetailViewModel;

/**
 * Created by duypham on 11/1/17.
 *
 */

public class ReleasesFragment extends BaseRecyclerViewFragment<RefreshRecyclerViewBinding, Release, ReleaseAdapter, ReleasesViewModel> {

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.addKeyLineDivider();

        RepoDetailViewModel repoDetailViewModel = ViewModelProviders.of(getActivity()).get(RepoDetailViewModel.class);
        repoDetailViewModel.getOnDataReady().observe(this, ready -> {
            if (ready != null && ready) {
                // noinspection ConstantConditions
                viewModel.initRepoDetail(repoDetailViewModel);
            }
        });
        viewModel.getReleasesCount().observe(this, count -> {
//            updateTabCount(3, count);
        });
    }

    @Override
    protected Class<ReleasesViewModel> getViewModelClass() {
        return ReleasesViewModel.class;
    }
}
