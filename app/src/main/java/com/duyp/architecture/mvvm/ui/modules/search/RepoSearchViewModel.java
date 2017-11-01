package com.duyp.architecture.mvvm.ui.modules.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.duyp.architecture.mvvm.data.local.user.UserManager;
import com.duyp.architecture.mvvm.data.model.Repo;
import com.duyp.architecture.mvvm.data.repository.AllReposRepo;
import com.duyp.architecture.mvvm.helper.InputHelper;
import com.duyp.architecture.mvvm.ui.adapter.RepoAdapter;
import com.duyp.architecture.mvvm.ui.base.BaseListDataViewModel;

import javax.inject.Inject;

import lombok.Setter;

/**
 * Created by duypham on 11/1/17.
 *
 */

public class RepoSearchViewModel extends BaseListDataViewModel<Repo, RepoAdapter>{

    private final AllReposRepo allReposRepo;

    private String query; // two way binding

    @Inject
    RepoSearchViewModel(UserManager userManager, AllReposRepo repo) {
        super(userManager);
        this.allReposRepo = repo;
    }

    @Override
    protected void onFirsTimeUiCreate(@Nullable Bundle bundle) {}

    @Override
    protected void callApi(int page, OnCallApiDone<Repo> onCallApiDone) {
        disposeAllExecutions();
        execute(true, allReposRepo.searchRemote(query, page), repoPageable -> {
            onCallApiDone.onDone(repoPageable.getLast(), page == 1, repoPageable.getItems());
        });
    }

    void searchLocal(String query) {
        setData(allReposRepo.searchLocal(query), true);
    }

    void searchRemote(String query) {
        this.query = query;
        refresh();
    }

    @Override
    public void onItemClick(View v, Repo item) {
        navigatorHelper.navigateRepoDetail(item);
    }
}
