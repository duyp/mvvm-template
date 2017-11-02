package com.duyp.architecture.mvvm.ui.modules.repo.detail.code.files;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.duyp.architecture.mvvm.R;
import com.duyp.architecture.mvvm.data.model.RepoFile;
import com.duyp.architecture.mvvm.databinding.RefreshRecyclerViewBinding;
import com.duyp.architecture.mvvm.databinding.ViewComingSoonBinding;
import com.duyp.architecture.mvvm.ui.base.fragment.BaseFragment;
import com.duyp.architecture.mvvm.ui.base.fragment.BaseRecyclerViewFragment;
import com.duyp.architecture.mvvm.ui.modules.repo.detail.code.files.paths.RepoFilePathsViewModel;

/**
 * Created by duypham on 11/1/17.
 *
 */

public class RepoFilesFragment extends BaseRecyclerViewFragment<RefreshRecyclerViewBinding,RepoFile, RepoFilesAdapter, RepoFilesViewModel>{

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RepoFilePathsViewModel parentViewModel = ViewModelProviders.of(getParentFragment()).get(RepoFilePathsViewModel.class);
        viewModel.initParentViewModel(parentViewModel);

        parentViewModel.getOnRefresh().observe(this, o -> {
            if (o != null) {
                viewModel.refresh();
            }
        });

    }

    @Override
    protected Class<RepoFilesViewModel> getViewModelClass() {
        return RepoFilesViewModel.class;
    }
}
