package com.duyp.architecture.mvvm.ui.modules.profile.starred;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.duyp.architecture.mvvm.data.model.Repo;
import com.duyp.architecture.mvvm.databinding.RefreshRecyclerViewBinding;
import com.duyp.architecture.mvvm.ui.base.fragment.BaseRecyclerViewFragment;
import com.duyp.architecture.mvvm.ui.modules.profile.ProfileViewModel;
import com.duyp.architecture.mvvm.ui.modules.repo.list.RepoAdapter;
import com.duyp.architecture.mvvm.utils.FragmentUtils;

/**
 * Created by duypham on 10/28/17.
 *
 */

public class StarredFragment extends BaseRecyclerViewFragment<RefreshRecyclerViewBinding, Repo, RepoAdapter, StarredViewModel> {

    public static StarredFragment newInstance(String user) {
        return FragmentUtils.createFragmentInstance(new StarredFragment(), user);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.initProfileViewModel(ViewModelProviders.of(getActivity()).get(ProfileViewModel.class));
    }

    @Override
    protected Class<StarredViewModel> getViewModelClass() {
        return StarredViewModel.class;
    }
}
