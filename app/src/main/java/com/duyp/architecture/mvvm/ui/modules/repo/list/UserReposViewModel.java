package com.duyp.architecture.mvvm.ui.modules.repo.list;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.duyp.architecture.mvvm.data.local.user.UserManager;
import com.duyp.architecture.mvvm.data.model.FilterOptionsModel;
import com.duyp.architecture.mvvm.data.model.Repo;
import com.duyp.architecture.mvvm.data.repository.UserReposRepo;
import com.duyp.architecture.mvvm.helper.BundleConstant;
import com.duyp.architecture.mvvm.ui.adapter.RepoAdapter;
import com.duyp.architecture.mvvm.ui.base.BaseListDataViewModel;

import javax.inject.Inject;

/**
 * Created by duypham on 10/25/17.
 *
 */

public class UserReposViewModel extends BaseListDataViewModel<Repo, RepoAdapter> {

    private final UserReposRepo repo;

    private boolean hasAvatar = true;

    private FilterOptionsModel filterOptions = new FilterOptionsModel();

    @Inject
    public UserReposViewModel(UserManager userManager, UserReposRepo repo) {
        super(userManager);
        this.repo = repo;
    }

    @Override
    public void initAdapter(@NonNull RepoAdapter adapter) {
        super.initAdapter(adapter);
        adapter.setHasAvatar(hasAvatar);
        setData(repo.getData().getData(), true);
    }

    @Override
    protected void onFirsTimeUiCreate(@Nullable Bundle bundle) {
        String targetUser = null;
        if (bundle != null) {
            targetUser = bundle.getString(BundleConstant.EXTRA);
            hasAvatar = bundle.getBoolean(BundleConstant.EXTRA_TWO, true);
        }
        repo.initUser(targetUser);
        new Handler().postDelayed(this::refresh, 300);
    }

    @Override
    protected void callApi(int page, OnCallApiDone<Repo> onCallApiDone) {
        execute(true, repo.getUserRepositories(filterOptions, page), repoPageable -> {
            onCallApiDone.onDone(repoPageable.getLast(), page == 1, repoPageable.getItems());
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        repo.onDestroy();
    }

    @Override
    public void onItemClick(View v, Repo item) {
        navigatorHelper.navigateRepoDetail(item);
    }
}
