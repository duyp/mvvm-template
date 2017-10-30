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

public class IssuesFragment extends BaseRecyclerViewFragment<RefreshRecyclerViewBinding, Issue, IssuesAdapter, IssuesViewModel> {

    public static IssuesFragment newInstance(@NonNull String repoId, @NonNull String login, @IssueState String state) {
        return FragmentUtils.createFragmentInstance(new IssuesFragment(), bundle -> {
            bundle.putString(BundleConstant.ID, repoId);
            bundle.putString(BundleConstant.EXTRA, login);
            bundle.putString(BundleConstant.EXTRA_TWO, state);
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.addKeyLineDivider();
    }

    @Override
    protected Class<IssuesViewModel> getViewModelClass() {
        return IssuesViewModel.class;
    }
}
