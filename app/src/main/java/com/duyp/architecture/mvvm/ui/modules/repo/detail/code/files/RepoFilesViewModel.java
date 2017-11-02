package com.duyp.architecture.mvvm.ui.modules.repo.detail.code.files;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.MenuInflater;
import android.view.View;
import android.widget.PopupMenu;

import com.annimon.stream.Stream;
import com.duyp.androidutils.AlertUtils;
import com.duyp.architecture.mvvm.App;
import com.duyp.architecture.mvvm.R;
import com.duyp.architecture.mvvm.data.local.user.UserManager;
import com.duyp.architecture.mvvm.data.model.RepoFile;
import com.duyp.architecture.mvvm.data.model.type.FilesType;
import com.duyp.architecture.mvvm.data.provider.markdown.MarkDownProvider;
import com.duyp.architecture.mvvm.data.remote.RepoService;
import com.duyp.architecture.mvvm.data.source.Status;
import com.duyp.architecture.mvvm.helper.BundleConstant;
import com.duyp.architecture.mvvm.helper.Bundler;
import com.duyp.architecture.mvvm.helper.FileHelper;
import com.duyp.architecture.mvvm.helper.InputHelper;
import com.duyp.architecture.mvvm.ui.base.BaseListDataViewModel;
import com.duyp.architecture.mvvm.ui.modules.repo.detail.code.files.paths.RepoFilePathsViewModel;
import com.duyp.architecture.mvvm.ui.widgets.dialog.MessageDialogView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by duypham on 11/2/17.
 *
 */

public class RepoFilesViewModel extends BaseListDataViewModel<RepoFile, RepoFilesAdapter> {

    RepoFilePathsViewModel parentViewModel;

    private final RepoService repoService;

    FragmentManager fragmentManager;

    @Inject
    public RepoFilesViewModel(UserManager userManager, RepoService repoService) {
        super(userManager);
        this.repoService = repoService;
    }

    @Override
    protected void onFirsTimeUiCreate(@Nullable Bundle bundle) {}

    public void initFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void initParentViewModel(RepoFilePathsViewModel parentViewModel) {
        if (this.parentViewModel == null) {
            this.parentViewModel = parentViewModel;
            refresh(300);
        }
    }

    @Override
    protected void callApi(int page, OnCallApiDone<RepoFile> onCallApiDone) {
        execute(true, true, repoService.getRepoFiles(parentViewModel.getLogin(), parentViewModel.getRepoId(),
                parentViewModel.getPath(), parentViewModel.getCurrentBranch()), repoFilePageable -> {

            List<RepoFile> filtered = new ArrayList<>();
            Observable.fromIterable(repoFilePageable.getItems())
                    .filter(file -> file.getType() != null)
                    .sorted((file1, file2) -> file2.getType().compareTo(file1.getType()))
                    .subscribe(filtered::add);

            onCallApiDone.onDone(repoFilePageable.getLast(), page == 1, filtered);
        }, errorEntity -> {
            if (page == 1 && adapter != null) {
                adapter.clear(); // clear adapter data if get error (except load more event - page > 1)
            }
            if (errorEntity.getHttpCode() == 404) { // if not found, reverse path
                parentViewModel.reversePath();
            }
        });
    }

    @Override
    public void onItemClick(View v, RepoFile item) {
        if (stateLiveData.getValue() != null && stateLiveData.getValue().getStatus() != Status.LOADING) {
            disposeAllExecutions();
            if (item.getType() == FilesType.dir) {
                parentViewModel.appendPath(item);
                refresh();
            } else {
                if (item.getSize() == 0 && InputHelper.isEmpty(item.getDownloadUrl()) && !InputHelper.isEmpty(item.getGitUrl())) {
//                    RepoFilesActivity.startActivity(getContext(), model.getGitUrl().replace("trees/", ""), isEnterprise());
                } else {
                    String url = InputHelper.isEmpty(item.getDownloadUrl()) ? item.getUrl() : item.getDownloadUrl();
                    if (InputHelper.isEmpty(url)) return;
                    if (item.getSize() > FileHelper.ONE_MB && !MarkDownProvider.isImage(url)) {
                        MessageDialogView.newInstance(getString(R.string.big_file), getString(R.string.big_file_description),
                                false, true, Bundler.start()
                                        .put(BundleConstant.EXTRA, item.getDownloadUrl())
                                        .put(BundleConstant.YES_NO_EXTRA, true)
                                        .end())
                                .show(fragmentManager, "MessageDialogView");
                    } else {
                        navigatorHelper.navigateCodeViewerActivity(url, item.getHtmlUrl());
                    }
                }
            }
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        fragmentManager = null;
    }
}
