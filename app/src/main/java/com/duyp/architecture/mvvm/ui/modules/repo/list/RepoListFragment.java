package com.duyp.architecture.mvvm.ui.modules.repo.list;

import com.duyp.architecture.mvvm.data.model.Repo;
import com.duyp.architecture.mvvm.databinding.RefreshRecyclerViewBinding;
import com.duyp.architecture.mvvm.helper.BundleConstant;
import com.duyp.architecture.mvvm.ui.adapter.RepoAdapter;
import com.duyp.architecture.mvvm.ui.base.fragment.BaseRecyclerViewFragment;
import com.duyp.architecture.mvvm.utils.FragmentUtils;

/**
 * Created by duypham on 10/24/17.
 *
 */

public class RepoListFragment extends BaseRecyclerViewFragment<RefreshRecyclerViewBinding, Repo, RepoAdapter, UserReposViewModel> {

    public static RepoListFragment newInstance(String user, boolean hasImage) {
        return FragmentUtils.createFragmentInstance(new RepoListFragment(), bundle -> {
            bundle.putString(BundleConstant.EXTRA, user);
            bundle.putBoolean(BundleConstant.EXTRA_TWO, hasImage);
        });
    }

    @Override
    protected Class<UserReposViewModel> getViewModelClass() {
        return UserReposViewModel.class;
    }
}
