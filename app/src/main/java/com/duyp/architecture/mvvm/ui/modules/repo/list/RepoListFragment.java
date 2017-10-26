package com.duyp.architecture.mvvm.ui.modules.repo.list;

import com.duyp.architecture.mvvm.data.model.Repo;
import com.duyp.architecture.mvvm.databinding.RefreshRecyclerViewBinding;
import com.duyp.architecture.mvvm.ui.base.fragment.BaseRecyclerViewFragment;
import com.duyp.architecture.mvvm.utils.FragmentUtils;

/**
 * Created by duypham on 10/24/17.
 *
 */

public class RepoListFragment extends BaseRecyclerViewFragment<RefreshRecyclerViewBinding, Repo, RepoAdapter, UserReposViewModel> {

    public static RepoListFragment newInstance(String user) {
        return FragmentUtils.createFragmentInstance(new RepoListFragment(), user);
    }

    @Override
    protected Class<UserReposViewModel> getViewModelClass() {
        return UserReposViewModel.class;
    }
}
