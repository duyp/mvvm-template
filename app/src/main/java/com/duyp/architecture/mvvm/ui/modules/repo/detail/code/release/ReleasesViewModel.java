package com.duyp.architecture.mvvm.ui.modules.repo.detail.code.release;

import android.arch.lifecycle.MutableLiveData;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.duyp.androidutils.AlertUtils;
import com.duyp.architecture.mvvm.App;
import com.duyp.architecture.mvvm.data.local.user.UserManager;
import com.duyp.architecture.mvvm.data.model.Release;
import com.duyp.architecture.mvvm.data.model.RepoDetail;
import com.duyp.architecture.mvvm.data.remote.RepoService;
import com.duyp.architecture.mvvm.ui.base.BaseListDataViewModel;
import com.duyp.architecture.mvvm.ui.modules.repo.detail.RepoDetailViewModel;

import javax.inject.Inject;

import lombok.Getter;

/**
 * Created by duypham on 11/1/17.
 *
 */

public class ReleasesViewModel extends BaseListDataViewModel<Release, ReleaseAdapter> {

    private final RepoService repoService;
    private RepoDetail repoDetail;
    private RepoDetailViewModel repoDetailViewModel;

    @Getter
    private final MutableLiveData<Integer> releasesCount = new MutableLiveData<>();

    @Inject
    public ReleasesViewModel(UserManager userManager, RepoService repoService) {
        super(userManager);
        this.repoService = repoService;
    }

    @Override
    protected void onFirsTimeUiCreate(@Nullable Bundle bundle) {}

    @Override
    public void initAdapter(@NonNull ReleaseAdapter adapter) {
        super.initAdapter(adapter);
        adapter.setOnDownloadClickListener(this::onDownloadClick);
    }

    public void initRepoDetail(@NonNull RepoDetailViewModel viewModel) {
        this.repoDetailViewModel = viewModel;
        this.repoDetail = viewModel.getRepoDetail();
        refresh(100);
    }

    @Override
    protected void callApi(int page, OnCallApiDone<Release> onCallApiDone) {
        execute(true, repoService.getReleases(repoDetail.getOwner().getLogin(), repoDetail.getName(), page), releasePageable -> {
            onCallApiDone.onDone(releasePageable.getLast(), page == 1, releasePageable.getItems());
        });
    }

    @Override
    public void onItemClick(View v, Release item) {

    }

    public void onDownloadClick(Release item) {
        AlertUtils.showToastShortMessage(App.getInstance(), "Coming soon...");
    }

}
