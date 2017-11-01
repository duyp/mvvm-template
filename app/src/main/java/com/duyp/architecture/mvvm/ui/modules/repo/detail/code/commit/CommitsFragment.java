package com.duyp.architecture.mvvm.ui.modules.repo.detail.code.commit;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.duyp.architecture.mvvm.R;
import com.duyp.architecture.mvvm.data.model.BranchesModel;
import com.duyp.architecture.mvvm.data.model.Commit;
import com.duyp.architecture.mvvm.databinding.CommitWithBranchLayoutBinding;
import com.duyp.architecture.mvvm.ui.base.fragment.BaseRecyclerViewFragment;
import com.duyp.architecture.mvvm.ui.modules.repo.detail.RepoDetailViewModel;

import java.util.List;

/**
 * Created by duypham on 11/1/17.
 *
 */

public class CommitsFragment extends BaseRecyclerViewFragment<CommitWithBranchLayoutBinding, Commit, CommitsAdapter, CommitsViewModel> {

    ArrayAdapter<String> branchesAdapter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.addKeyLineDivider();

        branchesAdapter = new ArrayAdapter<>(getContext(), R.layout.branch_item);
        binding.branches.setAdapter(branchesAdapter);
        binding.branches.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (view instanceof TextView) {
                    viewModel.switchBranch(((TextView) view).getText().toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        RepoDetailViewModel repoDetailViewModel = ViewModelProviders.of(getActivity()).get(RepoDetailViewModel.class);
        repoDetailViewModel.getOnDataReady().observe(this, ready -> {
            if (ready != null && ready) {
                // noinspection ConstantConditions
                viewModel.initRepoDetail(repoDetailViewModel.getRepoDetail());
                branchesAdapter.add(viewModel.getCurrentBranch());

                repoDetailViewModel.getBranchesData().observe(this, branches -> {
                    if (branches != null) {
                        List<String> list = Stream.of(branches).map(BranchesModel::getName).toList();
                        if (!list.isEmpty()) {
                            branchesAdapter.clear();
                            branchesAdapter.addAll(list);

                            int index = list.indexOf(viewModel.getCurrentBranch());

                            if (index >= 0) {
                                binding.branches.setSelection(index);
                            } else {
                                branchesAdapter.insert(viewModel.getCurrentBranch(), 0);
                                binding.branches.setSelection(0);
                            }
                        }
                    } else {
                        branchesAdapter.clear();
                        branchesAdapter.add(viewModel.getCurrentBranch());
                        binding.branches.setSelection(0);
                    }
                });
            }
        });

        viewModel.getCommitsCount().observe(this, count -> updateTabCount(2, count));
    }

    @Override
    protected Class<CommitsViewModel> getViewModelClass() {
        return CommitsViewModel.class;
    }

    @Override
    protected int getLayout() {
        return R.layout.commit_with_branch_layout;
    }
}