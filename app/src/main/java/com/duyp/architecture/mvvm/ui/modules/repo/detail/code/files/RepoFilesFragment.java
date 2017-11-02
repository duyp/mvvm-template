package com.duyp.architecture.mvvm.ui.modules.repo.detail.code.files;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.MenuInflater;
import android.view.View;
import android.widget.PopupMenu;

import com.duyp.androidutils.AlertUtils;
import com.duyp.architecture.mvvm.R;
import com.duyp.architecture.mvvm.data.model.RepoFile;
import com.duyp.architecture.mvvm.data.model.User;
import com.duyp.architecture.mvvm.data.model.type.FilesType;
import com.duyp.architecture.mvvm.data.provider.markdown.MarkDownProvider;
import com.duyp.architecture.mvvm.databinding.RefreshRecyclerViewBinding;
import com.duyp.architecture.mvvm.databinding.ViewComingSoonBinding;
import com.duyp.architecture.mvvm.helper.ActivityHelper;
import com.duyp.architecture.mvvm.helper.AppHelper;
import com.duyp.architecture.mvvm.helper.DownloadHelper;
import com.duyp.architecture.mvvm.helper.InputHelper;
import com.duyp.architecture.mvvm.helper.PrefGetter;
import com.duyp.architecture.mvvm.ui.base.fragment.BaseFragment;
import com.duyp.architecture.mvvm.ui.base.fragment.BaseRecyclerViewFragment;
import com.duyp.architecture.mvvm.ui.modules.repo.detail.RepoDetailViewModel;
import com.duyp.architecture.mvvm.ui.modules.repo.detail.code.files.paths.RepoFilePathsViewModel;

/**
 * Created by duypham on 11/1/17.
 *
 */

public class RepoFilesFragment extends BaseRecyclerViewFragment<RefreshRecyclerViewBinding,RepoFile, RepoFilesAdapter, RepoFilesViewModel>{

    private RepoDetailViewModel repoDetailViewModel;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        repoDetailViewModel = ViewModelProviders.of(getActivity()).get(RepoDetailViewModel.class);
        RepoFilePathsViewModel parentViewModel = ViewModelProviders.of(getParentFragment()).get(RepoFilePathsViewModel.class);
        viewModel.initParentViewModel(parentViewModel);
        viewModel.initFragmentManager(getChildFragmentManager());

        parentViewModel.getOnRefresh().observe(this, o -> {
            if (o != null) {
                viewModel.refresh();
            }
        });
        adapter.setOnMenuClick(this::onMenuClicked);
    }

    public void onMenuClicked(View v, @NonNull RepoFile item) {
        if (isRefreshing) return;

        boolean isOwner = repoDetailViewModel.isOwnerOrCollaborator();

        PopupMenu popup = new PopupMenu(getContext(), v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.download_share_menu, popup.getMenu());
        popup.getMenu().findItem(R.id.download).setVisible(item.getType() == FilesType.file);
        boolean canOpen = canOpen(item);
        popup.getMenu().findItem(R.id.editFile).setVisible(isOwner && item.getType() == FilesType.file && canOpen);
        popup.getMenu().findItem(R.id.deleteFile).setVisible(isOwner && item.getType() == FilesType.file);
        popup.setOnMenuItemClickListener(item1 -> {
            switch (item1.getItemId()) {
                case R.id.share:
                    ActivityHelper.shareUrl(getContext(), item.getHtmlUrl());
                    break;
                case R.id.download:
                    if (ActivityHelper.checkAndRequestReadWritePermission(getActivity())) {
                        DownloadHelper.downloadFile(getContext().getApplicationContext(), item.getDownloadUrl());
                    }
                    break;
                case R.id.copy:
                    AppHelper.copyToClipboard(getContext(), !InputHelper.isEmpty(item.getHtmlUrl()) ? item.getHtmlUrl() : item.getUrl());
                    break;
                case R.id.editFile:
                    AlertUtils.showToastShortMessage(getContext(), "Coming soon...");
//                    if (PrefGetter.isProEnabled() || PrefGetter.isAllFeaturesUnlocked()) {
//                        if (canOpen) {
//                            EditRepoFileModel fileModel = new EditRepoFileModel(getPresenter().login, getPresenter().repoId,
//                                    item.getPath(), getPresenter().ref, item.getSha(), item.getDownloadUrl(), item.getName(), true);
//                            EditRepoFileActivity.Companion.startForResult(this, fileModel, isEnterprise());
//                        }
//                    } else {
//                        PremiumActivity.Companion.startActivity(getContext());
//                    }
                    break;
                case R.id.deleteFile:
                    AlertUtils.showToastShortMessage(getContext(), "Coming soon...");
//                    if (PrefGetter.isProEnabled() || PrefGetter.isAllFeaturesUnlocked()) {
//                        DeleteFileBottomSheetFragment.Companion.newInstance(position, item.getName())
//                                .show(getChildFragmentManager(), DeleteFileBottomSheetFragment.class.getSimpleName());
//                    } else {
//                        PremiumActivity.Companion.startActivity(getContext());
//                    }
                    break;
            }
            return true;
        });
        popup.show();
    }

    private boolean canOpen(@NonNull RepoFile item) {
        return item.getDownloadUrl() != null && !MarkDownProvider.isImage(item.getDownloadUrl())
                && !MarkDownProvider.isArchive(item.getDownloadUrl());
    }

    @Override
    protected Class<RepoFilesViewModel> getViewModelClass() {
        return RepoFilesViewModel.class;
    }
}
