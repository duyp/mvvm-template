package com.duyp.architecture.mvvm.ui.modules.repo.detail.issues.list;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.duyp.architecture.mvvm.data.model.Issue;
import com.duyp.architecture.mvvm.data.model.type.IssueState;
import com.duyp.architecture.mvvm.databinding.RefreshRecyclerViewBinding;
import com.duyp.architecture.mvvm.helper.BundleConstant;
import com.duyp.architecture.mvvm.ui.base.fragment.BaseRecyclerViewFragment;
import com.duyp.architecture.mvvm.utils.FragmentUtils;

/**
 * Created by duypham on 10/31/17.
 *
 */

public class RepoIssuesFragment extends BaseRecyclerViewFragment<RefreshRecyclerViewBinding, Issue, RepoIssuesAdapter, RepoIssuesViewModel> {

    public static RepoIssuesFragment newInstance(@NonNull String repoId, @NonNull String login, @IssueState String state) {
        return FragmentUtils.createFragmentInstance(new RepoIssuesFragment(), bundle -> {
            bundle.putString(BundleConstant.ID, repoId);
            bundle.putString(BundleConstant.EXTRA, login);
            bundle.putString(BundleConstant.EXTRA_TWO, state);
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.addKeyLineDivider();
        setNoDataText("No Issues");
    }

    @Override
    protected Class<RepoIssuesViewModel> getViewModelClass() {
        return RepoIssuesViewModel.class;
    }
}
