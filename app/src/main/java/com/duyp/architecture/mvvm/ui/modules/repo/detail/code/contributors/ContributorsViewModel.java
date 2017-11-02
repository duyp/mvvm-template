package com.duyp.architecture.mvvm.ui.modules.repo.detail.code.contributors;

import android.arch.lifecycle.MutableLiveData;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.duyp.architecture.mvvm.data.local.user.UserManager;
import com.duyp.architecture.mvvm.data.model.User;
import com.duyp.architecture.mvvm.data.remote.RepoService;
import com.duyp.architecture.mvvm.ui.adapter.UsersAdapter;
import com.duyp.architecture.mvvm.ui.base.BaseListDataViewModel;
import com.duyp.architecture.mvvm.ui.modules.repo.detail.RepoDetailViewModel;

import javax.inject.Inject;

import lombok.Getter;

/**
 * Created by duypham on 11/1/17.
 *
 */

public class ContributorsViewModel extends BaseListDataViewModel<User, UsersAdapter> {

    private final RepoService repoService;

    private RepoDetailViewModel repoDetailViewModel;
    private String repoName;
    private String login;

    @Getter
    private final MutableLiveData<Integer> contributorsCount = new MutableLiveData<>();

    @Inject
    public ContributorsViewModel(UserManager userManager, RepoService service) {
        super(userManager);
        this.repoService = service;
    }

    @Override
    protected void onFirsTimeUiCreate(@Nullable Bundle bundle) {}

    public void initRepoDetail(@NonNull RepoDetailViewModel viewModel) {
        if (this.repoDetailViewModel == null) {
            this.repoDetailViewModel = viewModel;
            repoName = viewModel.getRepoDetail().getName();
            login = viewModel.getRepoDetail().getOwner().getLogin();
            refresh(100);
        }
    }

    @Override
    public void onItemClick(View v, User item) {
        navigatorHelper.navigateUserProfile(item);
    }

    @Override
    protected void callApi(int page, OnCallApiDone<User> onCallApiDone) {
        execute(true, repoService.getContributors(login, repoName, page), userPageable -> {
            onCallApiDone.onDone(userPageable.getLast(), page == 1, userPageable.getItems());
        });
    }
}
