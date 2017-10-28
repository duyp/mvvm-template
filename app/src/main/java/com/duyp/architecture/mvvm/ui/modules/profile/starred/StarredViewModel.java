package com.duyp.architecture.mvvm.ui.modules.profile.starred;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.duyp.architecture.mvvm.data.local.user.UserManager;
import com.duyp.architecture.mvvm.data.model.Repo;
import com.duyp.architecture.mvvm.ui.base.BaseListDataViewModel;
import com.duyp.architecture.mvvm.ui.modules.repo.list.RepoAdapter;

import javax.inject.Inject;

/**
 * Created by duypham on 10/28/17.
 *
 */

public class StarredViewModel extends BaseListDataViewModel<Repo, RepoAdapter> {

    String user;

    @Inject
    public StarredViewModel(UserManager userManager, RepoAdapter adapter) {
        super(userManager, adapter);
    }

    @Override
    protected void onFirsTimeUiCreate(@Nullable Bundle bundle) {}

    public void initUser(@NonNull String user) {

    }

    @Override
    protected void callApi(int page, OnCallApiDone<Repo> onCallApiDone) {

    }

    @Override
    protected void onItemClick(Repo item) {

    }
}
