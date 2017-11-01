package com.duyp.architecture.mvvm.ui.modules.repo.detail.code.commit;

import android.arch.lifecycle.MutableLiveData;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.duyp.androidutils.AlertUtils;
import com.duyp.architecture.mvvm.App;
import com.duyp.architecture.mvvm.data.local.user.UserManager;
import com.duyp.architecture.mvvm.data.model.Commit;
import com.duyp.architecture.mvvm.data.model.RepoDetail;
import com.duyp.architecture.mvvm.data.remote.RepoService;
import com.duyp.architecture.mvvm.ui.base.BaseListDataViewModel;

import javax.inject.Inject;

import lombok.Getter;

/**
 * Created by duypham on 11/1/17.
 *
 */

@Getter
public class CommitsViewModel extends BaseListDataViewModel<Commit, CommitsAdapter> {

    private RepoDetail repoDetail;
    private String login;
    private String repoName;
    private String currentBranch;

    private final RepoService repoService;

    private final MutableLiveData<Integer> commitsCount = new MutableLiveData<>();

    @Inject
    public CommitsViewModel(UserManager userManager, RepoService repoService) {
        super(userManager);
        this.repoService = repoService;
    }

    @Override
    protected void onFirsTimeUiCreate(@Nullable Bundle bundle) {}

    public void initRepoDetail(@NonNull RepoDetail repoDetail) {
        this.repoDetail = repoDetail;
        login = repoDetail.getOwner().getLogin();
        repoName = repoDetail.getName();
        currentBranch = repoDetail.getDefaultBranch();
        getCommitCount(currentBranch);
        refresh(100);
    }

    @Override
    protected void callApi(int page, OnCallApiDone<Commit> onCallApiDone) {
        execute(true, repoService.getCommits(login, repoName, currentBranch, page), commitPageable -> {
            onCallApiDone.onDone(commitPageable.getLast(), page == 1, commitPageable.getItems());
        });
    }

    public void switchBranch(String newBranch) {
        if (!currentBranch.equals(newBranch)) {
            this.currentBranch = newBranch;
            refresh(100);
            getCommitCount(currentBranch);
        }
    }

    public void getCommitCount(String branch) {
        new Handler(Looper.myLooper()).postDelayed(() -> {
            execute(false, false, repoService.getCommitCounts(login, repoName, branch), commitPageable -> {
                commitsCount.setValue(commitPageable.getLast());
            }, errorEntity -> {
                commitsCount.setValue(0);
            });
        }, 500);
    }

    @Override
    public void onItemClick(View v, Commit item) {
        AlertUtils.showToastShortMessage(App.getInstance(), "Coming soon...");
    }
}
