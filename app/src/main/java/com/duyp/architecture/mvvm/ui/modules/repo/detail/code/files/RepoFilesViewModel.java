package com.duyp.architecture.mvvm.ui.modules.repo.detail.code.files;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.annimon.stream.Stream;
import com.duyp.architecture.mvvm.data.local.user.UserManager;
import com.duyp.architecture.mvvm.data.model.RepoFile;
import com.duyp.architecture.mvvm.data.model.type.FilesType;
import com.duyp.architecture.mvvm.data.remote.RepoService;
import com.duyp.architecture.mvvm.data.source.Status;
import com.duyp.architecture.mvvm.ui.base.BaseListDataViewModel;
import com.duyp.architecture.mvvm.ui.modules.repo.detail.code.files.paths.RepoFilePathsViewModel;

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

    @Inject
    public RepoFilesViewModel(UserManager userManager, RepoService repoService) {
        super(userManager);
        this.repoService = repoService;
    }

    @Override
    protected void onFirsTimeUiCreate(@Nullable Bundle bundle) {}

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
            }
        }
    }
}
