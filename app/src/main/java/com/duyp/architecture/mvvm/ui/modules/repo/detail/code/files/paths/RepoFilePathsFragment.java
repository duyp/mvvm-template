package com.duyp.architecture.mvvm.ui.modules.repo.detail.code.files.paths;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.duyp.androidutils.navigation.FragmentNavigator;
import com.duyp.androidutils.navigation.Navigator;
import com.duyp.architecture.mvvm.R;
import com.duyp.architecture.mvvm.data.model.BranchesModel;
import com.duyp.architecture.mvvm.databinding.RepoFileLayoutBinding;
import com.duyp.architecture.mvvm.helper.BundleConstant;
import com.duyp.architecture.mvvm.helper.Bundler;
import com.duyp.architecture.mvvm.ui.base.fragment.BaseViewModelFragment;
import com.duyp.architecture.mvvm.ui.modules.repo.detail.RepoDetailViewModel;
import com.duyp.architecture.mvvm.ui.modules.repo.detail.code.files.RepoFilesFragment;
import com.duyp.architecture.mvvm.ui.widgets.SimpleAdapterDataObserver;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by duypham on 11/2/17.
 *
 */

public class RepoFilePathsFragment extends BaseViewModelFragment<RepoFileLayoutBinding, RepoFilePathsViewModel> {

    ArrayAdapter<String> branchesAdapter;

    @Inject RepoFilePathsAdapter adapter;

    @Inject
    FragmentNavigator navigator;

    @Override
    protected int getLayout() {
        return R.layout.repo_file_layout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        branchesAdapter = new ArrayAdapter<>(getContext(), R.layout.branch_item);
        binding.header.branches.setAdapter(branchesAdapter);
        binding.header.branches.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (view instanceof TextView) {
                    viewModel.setCurrentBranch(((TextView) view).getText().toString());
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        RepoDetailViewModel repoDetailViewModel = ViewModelProviders.of(getActivity()).get(RepoDetailViewModel.class);
        repoDetailViewModel.getOnDataReady().observe(this, ready -> {
            if (ready != null && ready) {
                // noinspection ConstantConditions
                branchesAdapter.add(viewModel.getCurrentBranch());

                repoDetailViewModel.getBranchesData().observe(this, branches -> {
                    if (branches != null) {
                        List<String> list = Stream.of(branches).map(BranchesModel::getName).toList();
                        if (!list.isEmpty()) {
                            branchesAdapter.clear();
                            branchesAdapter.addAll(list);

                            int index = list.indexOf(viewModel.getCurrentBranch());

                            if (index >= 0) {
                                binding.header.branches.setSelection(index);
                            } else {
                                branchesAdapter.insert(viewModel.getCurrentBranch(), 0);
                                binding.header.branches.setSelection(0);
                            }
                        }
                    } else {
                        branchesAdapter.clear();
                        branchesAdapter.add(viewModel.getCurrentBranch());
                        binding.header.branches.setSelection(0);
                    }
                });
            }
        });

        binding.header.toParentFolder.setOnClickListener(v -> viewModel.onHomeClick());

        viewModel.getOnDataReady().observe(this, ready -> {
            if (ready != null && ready) {

                viewModel.initAdapter(adapter);
                binding.header.recycler.setAdapter(adapter);
                adapter.registerAdapterDataObserver(new SimpleAdapterDataObserver() {
                    @Override
                    public void onChanged() {
                        if (adapter.getItemCount() > 0) {
                            binding.header.recycler.smoothScrollToPosition(adapter.getItemCount() - 1);
                        }
                    }
                });

                if (savedInstanceState == null) {
                    navigator.replaceChildFragment(R.id.container, new RepoFilesFragment());
                }
            }
        });
    }

    @Override
    protected Class<RepoFilePathsViewModel> getViewModelClass() {
        return RepoFilePathsViewModel.class;
    }

    public static RepoFilePathsFragment newInstance(@NonNull String login, @NonNull String repoId, @Nullable String path,
                                                   @NonNull String defaultBranch) {
        return newInstance(login, repoId, path, defaultBranch, false);
    }

    public static RepoFilePathsFragment newInstance(@NonNull String login, @NonNull String repoId,
                                                   @Nullable String path, @NonNull String defaultBranch,
                                                   boolean forceAppendPath) {
        RepoFilePathsFragment view = new RepoFilePathsFragment();
        view.setArguments(Bundler.start()
                .put(BundleConstant.ID, repoId)
                .put(BundleConstant.EXTRA, login)
                .put(BundleConstant.EXTRA_TWO, path)
                .put(BundleConstant.EXTRA_THREE, defaultBranch)
                .put(BundleConstant.EXTRA_FOUR, forceAppendPath)
                .end());
        return view;
    }
}
